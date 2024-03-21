package org.springblade.modules.mydata.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import org.springblade.common.constant.MdConstant;
import org.springblade.modules.mydata.job.bean.TaskBatchParam;
import org.springblade.modules.mydata.job.bean.TaskInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务分批参数处理类
 *
 * @author LIEN
 * @since 2024/3/20
 */
@Component
public class JobBatchService {
    private static final String OP_INC = "inc";

    /**
     * 将数据库中的分批参数 转为封装类结构
     */
    public List<TaskBatchParam> parseTaskBatchParam(List<Map<String, String>> taskBatchParams) {
        if (CollUtil.isEmpty(taskBatchParams)) {
            return null;
        }

        List<TaskBatchParam> batchParamList = CollUtil.newArrayList();
        for (Map<String, String> map : taskBatchParams) {
            TaskBatchParam taskBatchParam = new TaskBatchParam();
            taskBatchParam.setKey(map.get(MdConstant.DATA_KEY));
            taskBatchParam.setOp(map.get(MdConstant.DATA_OP));
            taskBatchParam.setValue(Integer.valueOf(map.get(MdConstant.DATA_VALUE)));
            taskBatchParam.setStep(Integer.valueOf(map.get(MdConstant.BATCH_INC_STEP)));
            batchParamList.add(taskBatchParam);
        }

        return batchParamList;
    }

    /**
     * 将分批参数 转为请求参数
     *
     * @param taskInfo 任务
     * @return 请求参数
     */
    public Map<String, Object> parseToMap(TaskInfo taskInfo) {
        if (!taskInfo.isBatch()) {
            return null;
        }

        List<TaskBatchParam> batchParams = taskInfo.getBatchParams();
        if (CollUtil.isEmpty(batchParams)) {
            return null;
        }

        Map<String, Object> reqParams = new HashMap<>();
        for (TaskBatchParam batchParam : batchParams) {
            reqParams.put(batchParam.getKey(), batchParam.getValue());
        }
        return reqParams;
    }

    /**
     * 递增分批参数
     *
     * @param taskInfo 任务对象
     */
    public void incBatchParam(TaskInfo taskInfo) {
        Assert.notNull(taskInfo);
        // 未开启递增，则不处理
        if (!taskInfo.isBatch()) {
            return;
        }

        List<TaskBatchParam> taskBatchParams = taskInfo.getBatchParams();
        if (CollUtil.isEmpty(taskBatchParams)) {
            return;
        }

        for (TaskBatchParam param : taskBatchParams) {
            if (OP_INC.equals(param.getOp())) {
                param.setValue(param.getValue() + param.getStep());
            }
        }
    }
}
