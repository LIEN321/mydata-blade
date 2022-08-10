package org.springblade.mydata.job.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springblade.mydata.data.BizDataDAO;
import org.springblade.mydata.job.bean.TaskJob;
import org.springblade.mydata.manage.feign.IDataClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 任务的数据处理类
 *
 * @author LIEN
 * @date 2022/7/16
 */
@Component
public class JobDataService {

    @Resource
    private BizDataDAO bizDataDAO;
    @Resource
    private IDataClient dataClient;

    /**
     * 根据任务配置，从json中解析出业务数据列表
     *
     * @param taskJob    任务
     * @param jsonString json字符串
     */
    public void parseData(TaskJob taskJob, String jsonString) {
        // 字段层级前缀
        String apiFieldPrefix = taskJob.getApiFieldPrefix();

        JSON json = JSONUtil.parse(jsonString);
        if (StrUtil.isNotEmpty(apiFieldPrefix)) {
            Object prefixJson = json.getByPath(apiFieldPrefix);
            if (!(prefixJson instanceof JSON)) {
                throw new RuntimeException("接口前缀 无法解析为JSON");
            }
            json = (JSON) prefixJson;
        }
        JSONArray jsonArray;
        if (json instanceof JSONArray) {
            jsonArray = (JSONArray) json;
        } else {
            jsonArray = new JSONArray();
            jsonArray.add(json);
        }

        // 声明方法返回结果
        List<Map> apiResponseDataList = CollUtil.newArrayList();
        // 获取任务中的字段映射配置
        Map<String, String> filedMappings = taskJob.getFieldMapping();
        // 根据映射 解析出json中的数据 并存入数据
        jsonArray.forEach(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> datacenterData = MapUtil.newHashMap();
            filedMappings.forEach((standardCode, apiCode) -> {
                datacenterData.put(standardCode, jsonObject.get(apiCode));
            });

            apiResponseDataList.add(datacenterData);
        });

        taskJob.setProduceDataList(apiResponseDataList);
    }

    /**
     * 根据任务中字段映射，将consumeDataList转换为api参数结构
     *
     * @param taskJob 任务
     */
    public void convertData(TaskJob taskJob) {
        List<Map> consumeDataList = taskJob.getConsumeDataList();
        // 获取任务的数据映射
        // 映射中，key为数据中心字段名，value为api字段名
        Map<String, String> mFieldMapping = taskJob.getFieldMapping();
        Assert.notEmpty(mFieldMapping, "任务未设置数据映射");

        // 遍历数据中心数据，根据映射 转换为接口结构的数据
        List<Map> apiRequestDataList = CollUtil.newArrayList();

        consumeDataList.forEach(data -> {
            Map<String, Object> apiData = MapUtil.newHashMap();
            // 根据映射关系 将数据转换为api的数据结构
            mFieldMapping.forEach((key, value) -> {
                apiData.put(value, data.get(key));
            });

            apiRequestDataList.add(apiData);
        });

        taskJob.setConsumeDataList(apiRequestDataList);
    }

    public void saveTaskData(TaskJob task) {
        Assert.notNull(task);
        Assert.notEmpty(task.getProduceDataList(), "error: 保存数据到仓库失败，task.datas是空的");

        // 标准数据编号
        String dataCode = task.getDataCode();
        // 数据的标识字段编号
        String dataIdCode = task.getIdFieldCode();

        // 保存数据到数据中心
        List<Map<String, Object>> dataInsertList = CollUtil.newArrayList();
        List<Map<String, Object>> dataUpdateList = CollUtil.newArrayList();
        task.getProduceDataList().forEach(standardDataValue -> {

            // 若数据的 标识字段值 无效，则不存储
            Object idFieldValue = standardDataValue.get(task.getIdFieldCode());
            if (ObjectUtil.isNull(idFieldValue)) {
                return;
            }

            Map<String, Object> value = bizDataDAO.findById(task.getTenantId(), task.getDataCode(), task.getIdFieldCode(), idFieldValue);

            if (value == null) {
                value = standardDataValue;
                dataInsertList.add(value);
            } else {
                value.putAll(standardDataValue);
                dataUpdateList.add(value);
            }
        });

        // 新增数据 到 数据仓库
        if (!dataInsertList.isEmpty()) {
            bizDataDAO.insertBatch(task.getTenantId(), dataCode, dataInsertList);
            // 更新业务数据量
            dataClient.updateDataCount(task.getDataId());
        }

        // 更新数据仓库的数据
        if (!dataUpdateList.isEmpty()) {
            dataUpdateList.forEach(data -> {
                String dataIdValue = (String) data.get(dataIdCode);
                bizDataDAO.update(task.getTenantId(), dataCode, dataIdCode, dataIdValue, data);
            });
        }
    }
}
