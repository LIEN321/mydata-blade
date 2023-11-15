package org.springblade.mydata.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import org.springblade.common.constant.MdConstant;
import org.springblade.mydata.data.BizDataFilter;
import org.springblade.mydata.job.bean.TaskInfo;

import java.util.List;
import java.util.Map;

/**
 * 任务数据过滤器
 *
 * @author LIEN
 * @since 2021/2/13
 */
public class JobDataFilterService {
    /**
     * 通过 task里的dataFitler 对datas进行过滤
     *
     * @param task
     */
    public void doFilter(TaskInfo task) {
        Assert.notNull(task);

        if (CollUtil.isEmpty(task.getDataFilters()) || CollUtil.isEmpty(task.getProduceDataList())) {
            return;
        }

        // 定义新的数据集合，用于存储 过滤后的数据
        List<Map> filterDatas = ListUtil.toList();
        // 遍历数据，并进行过滤
        List<Map> dataList = task.getProduceDataList();
        List<BizDataFilter> dataFilters = task.getDataFilters();

        dataList.forEach(data -> {

            boolean isCorrect = false;

            for (BizDataFilter filter : dataFilters) {
                String key = filter.getKey();
                Object filterValue = filter.getValue();
                String op = filter.getOp();

                // 当数据中 不包含 过滤的字段名，则执行下一项过滤
                if (!data.containsKey(key)) {
                    continue;
                }

                // 当数据中 指定字段的值 无效，则过滤该数据
                Object dataValue = data.get(key);
                if (ObjectUtil.isNull(dataValue)) {
                    isCorrect = true;
                    break;
                }

                // 判断业务数据值 和 过滤数据值 都可对比，否则过滤条件无效
                if (!(dataValue instanceof Comparable && filterValue instanceof Comparable)) {
                    break;
                }

                String cDataValue = dataValue.toString();
                String cFilterValue = filterValue.toString();
                // 根据op类型，过滤数据
                switch (op) {

                    case MdConstant.DATA_OP_EQ:
                        // 等于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) == 0);
                        break;
                    case MdConstant.DATA_OP_NE:
                        // 不等于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) != 0);
                        break;
                    case MdConstant.DATA_OP_GT:
                        // 大于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) > 0);
                        break;
                    case MdConstant.DATA_OP_GTE:
                        // 大于等于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) >= 0);
                        break;
                    case MdConstant.DATA_OP_LT:
                        // 小于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) < 0);
                        break;
                    case MdConstant.DATA_OP_LTE:
                        // 小于等于
                        isCorrect = (ObjectUtil.compare(cDataValue, cFilterValue) <= 0);
                        break;

                    default:
                        throw new RuntimeException("JobDataFilter: 不支持的过滤操作");
                }
            }

            // 当 未被过滤，则添加到过滤结果
            if (isCorrect) {
                filterDatas.add(data);
            }
        });

        task.setProduceDataList(filterDatas);
    }
}
