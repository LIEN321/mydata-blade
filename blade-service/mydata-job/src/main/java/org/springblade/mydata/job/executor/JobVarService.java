package org.springblade.mydata.job.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.springblade.common.util.MdUtil;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.job.bean.TaskJob;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.feign.IEnvClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 任务的变量处理类
 *
 * @author LIEN
 * @since 2023/11/5
 */
@Component
public class JobVarService {

    @Resource
    private IEnvClient envClient;

    /**
     * 将json中提取指定数据 保存到任务的指定环境变量
     *
     * @param taskJob    任务
     * @param jsonString json数据
     */
    public void saveVarValue(TaskJob taskJob, String jsonString) {
        if (taskJob == null) {
            return;
        }

        // 接口字段 与 变量的映射
        Map<String, String> fieldVarMapping = taskJob.getFieldVarMapping();
        if (CollUtil.isEmpty(fieldVarMapping)) {
            return;
        }

        JSON json = JSONUtil.parse(jsonString);
        fieldVarMapping.forEach((apiField, varName) -> {
            String varValue = json.getByPath(apiField, String.class);
            Long envId = taskJob.getEnvId();

            EnvVar envVar = new EnvVar();
            envVar.setEnvId(envId);
            envVar.setVarName(varName);
            envVar.setVarValue(varValue);
            envVar.setTenantId(taskJob.getTenantId());

            envClient.saveVar(envVar);
        });
    }

    /**
     * 解析任务API header和param中的变量表达式，从任务对应环境中获取变量值 并替换变量；
     *
     * @param taskJob 任务
     */
    public void parseVar(TaskJob taskJob) {
        Set<String> varNames = CollUtil.newHashSet();

        // 从API的header和param中 解析变量表达式
        Map<String, String> reqHeaders = taskJob.getReqHeaders();
        Map<String, Object> reqParams = taskJob.getReqParams();

        if (CollUtil.isNotEmpty(reqHeaders)) {
            //varNames.addAll(MdUtil.parseVarNames(reqHeaders.keySet()));
            varNames.addAll(MdUtil.parseVarNames(reqHeaders.values()));
        }
        if (CollUtil.isNotEmpty(reqParams)) {
            //varNames.addAll(MdUtil.parseVarNames(reqParams.keySet()));
            varNames.addAll(MdUtil.parseVarNames(reqParams.values()));
        }
        // 若没有变量名，则结束解析
        if (CollUtil.isEmpty(varNames)) {
            return;
        }

        // 根据变量名 获取环境变量值
        Long envId = taskJob.getEnvId();
        R<List<EnvVar>> listR = envClient.getVars(envId, varNames);
        Assert.isTrue(listR.isSuccess(), "解析环境变量出错：获取环境变量失败，envId = {}，varNames = {}， listR = {}", envId, varNames, listR);

        List<EnvVar> envVars = listR.getData();
        Map<String, String> varMap = envVars.stream().collect(Collectors.toMap(EnvVar::getVarName, EnvVar::getVarValue));

        // 替换 header和param 中的变量
        if (CollUtil.isNotEmpty(reqHeaders)) {
            taskJob.setReqHeaders(MdUtil.replaceVarValues(reqHeaders, varMap));
        }
        if (CollUtil.isNotEmpty(reqParams)) {
            taskJob.setReqParams(MdUtil.replaceVarValues(reqParams, varMap));
        }
    }
}
