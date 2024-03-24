package org.springblade.modules.mydata.job.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springblade.common.util.HttpUtils;
import org.springblade.modules.mydata.job.bean.TaskInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Api接口调用器
 *
 * @author LIEN
 * @since 2021/1/31
 */
@Component
public class ApiUtil {

    /**
     * 调用接口，获取返回结果
     *
     * @param task 任务
     * @return 接口结果
     */
    public static String read(TaskInfo task) {
        task.appendLog("调用API 获取数据，method={}，url={}，headers={}，params={}", task.getApiMethod(), task.getApiUrl(), task.getReqHeaders(), task.getReqParams());
        return HttpUtils.send(Method.valueOf(task.getApiMethod()), task.getApiUrl(), task.getReqHeaders(), task.getReqParams());
    }

    /**
     * 调用接口，发送标准数据
     *
     * @param task 任务
     */
    public static void write(TaskInfo task) {
        List<Map> consumeDataList = task.getConsumeDataList();
        if (CollUtil.isEmpty(consumeDataList)) {
            return;
        }

        String apiFieldPrefix = task.getApiFieldPrefix();
        JSON json;
        if (StrUtil.isNotBlank(apiFieldPrefix)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putByPath(apiFieldPrefix, consumeDataList);
            json = jsonObject;
        } else if (task.getBatchSize() == 1) {
            json = new JSONObject(consumeDataList.get(0));
        } else {
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(consumeDataList);
            json = jsonArray;
        }

        task.appendLog("调用API 发送数据，method={}，url={}，headers={}，params={}，body={}", task.getApiMethod(), task.getApiUrl(), task.getReqHeaders(), task.getReqParams(), json.toString());

        HttpUtils.send(Method.valueOf(task.getApiMethod()), task.getApiUrl(), task.getReqHeaders(), task.getReqParams(), json.toString());
    }
}