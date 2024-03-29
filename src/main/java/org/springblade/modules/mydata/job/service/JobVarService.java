package org.springblade.modules.mydata.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.springblade.common.util.MdUtil;
import org.springblade.modules.mydata.job.bean.TaskInfo;
import org.springblade.modules.mydata.manage.cache.EnvVarCache;
import org.springblade.modules.mydata.manage.entity.Env;
import org.springblade.modules.mydata.manage.entity.EnvVar;
import org.springblade.modules.mydata.manage.service.IEnvService;
import org.springblade.modules.mydata.manage.service.IEnvVarService;
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
    private IEnvVarService envVarService;

    @Resource
    private IEnvService envService;

    /**
     * 将json中提取指定数据 保存到任务的指定环境变量
     *
     * @param task       任务
     * @param jsonString json数据
     */
    public void saveVarValue(TaskInfo task, String jsonString) {
        if (task == null) {
            return;
        }

        // 接口字段 与 变量的映射
        Map<String, String> fieldVarMapping = task.getFieldVarMapping();
        if (CollUtil.isEmpty(fieldVarMapping)) {
            return;
        }

        JSON json = JSONUtil.parse(jsonString);
        fieldVarMapping.forEach((apiField, varName) -> {
            String varValue = json.getByPath(apiField, String.class);
            Long envId = task.getEnvId();

            EnvVar envVar = new EnvVar();
            envVar.setEnvId(envId);
            envVar.setVarName(varName);
            envVar.setVarValue(varValue);
            envVar.setTenantId(task.getTenantId());

            envVarService.saveByNameInEnv(envVar);
            task.appendLog("保存环境变量，tenantId：{}，varName：{}，varValue：{}"
                    , envVar.getTenantId()
                    , envVar.getVarName()
                    , envVar.getVarValue());
        });

    }

    /**
     * 解析任务API header和param中的变量表达式，从任务对应环境中获取变量值 并替换变量；
     *
     * @param taskInfo 任务
     */
    public void parseVar(TaskInfo taskInfo) {
        Set<String> varNames = CollUtil.newHashSet();

        // 从API的header和param中 解析变量表达式
        Map<String, String> reqHeaders = taskInfo.getReqHeaders();
        Map<String, Object> reqParams = taskInfo.getReqParams();

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

        taskInfo.appendLog("任务接口中 解析出环境变量名：{}", varNames);

        // 根据变量名 获取环境变量值
        Long envId = taskInfo.getEnvId();
        List<EnvVar> envVars = CollUtil.newArrayList();
        Env env = envService.getById(envId);

        // redis中没有缓存 需要查数据库的变量名
        if (env != null && CollUtil.isNotEmpty(varNames)) {
            varNames.forEach(varName -> {
                // 尝试从redis获取变量
                EnvVar envVar = EnvVarCache.getEnvVar(env.getTenantId(), envId, varName);
                if (envVar != null) {
                    // 缓存对象有效 存入返回结果列表
                    envVars.add(envVar);
                }
            });
        }

        Map<String, String> varMap = envVars.stream().collect(Collectors.toMap(EnvVar::getVarName, EnvVar::getVarValue));

        taskInfo.appendLog("环境变量值：{}", varMap);

        // 替换 header和param 中的变量
        if (CollUtil.isNotEmpty(reqHeaders)) {
            taskInfo.setReqHeaders(MdUtil.replaceVarValues(reqHeaders, varMap));
        }
        if (CollUtil.isNotEmpty(reqParams)) {
            taskInfo.setReqParams(MdUtil.replaceVarValues(reqParams, varMap));
        }
    }
}
