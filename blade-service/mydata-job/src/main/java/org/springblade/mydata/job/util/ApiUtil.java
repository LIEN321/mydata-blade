package org.springblade.mydata.job.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springblade.common.constant.MdConstant;
import org.springblade.common.util.HttpUtils;
import org.springblade.mydata.job.bean.TaskJob;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Api接口调用器
 *
 * @author LIEN
 * @date 2021/1/31
 */
@Component
public class ApiUtil {

    /**
     * 调用接口，获取返回结果
     *
     * @param task 任务
     * @return 接口结果
     */
    public static String read(TaskJob task) {
        return HttpUtils.send(Method.valueOf(task.getApiMethod()), task.getApiUrl(), task.getReqHeaders(), task.getReqQueries());
    }

    /**
     * 调用接口，发送标准数据
     *
     * @param task 任务
     */
    public static void write(TaskJob task) {
        // 数据分多批发送
        int round = 0;
        while (true) {
            List<Map> subList = CollUtil.sub(task.getConsumeDataList()
                    , round * MdConstant.ROUND_DATA_COUNT
                    , (round + 1) * MdConstant.ROUND_DATA_COUNT);
            if (CollUtil.isEmpty(subList)) {
                break;
            }

            String apiFieldPrefix = task.getApiFieldPrefix();
            JSON json;
            if (StrUtil.isNotBlank(apiFieldPrefix)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putByPath(apiFieldPrefix, subList);
                json = jsonObject;
            } else {
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(subList);
                json = jsonArray;
            }

            HttpUtils.send(Method.valueOf(task.getApiMethod())
                    , task.getApiUrl()
                    , task.getReqHeaders()
                    , task.getReqQueries()
                    , json.toString());

            round++;
        }
    }

}
