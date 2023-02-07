package org.springblade.mydata.job.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import org.springblade.common.constant.MdConstant;
import org.springblade.mydata.data.BizDataFilter;
import org.springblade.mydata.job.bean.TaskJob;

import java.util.List;
import java.util.Map;

/**
 * 任务数据过滤器
 *
 * @author LIEN
 * @date 2021/2/13
 */
public class JobDataFilter {
    /**
     * 通过 task里的dataFitler 对datas进行过滤
     *
     * @param task
     */
    public void doFilter(TaskJob task) {
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

            boolean isFiltered = false;

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
                    isFiltered = true;
                    break;
                }

                // TODO 根据op类型，过滤数据
                switch (op) {
                    case MdConstant.DATA_OP_EQ:
                        if (!ObjectUtil.equal(dataValue, filterValue)) {
                            isFiltered = true;
                            break;
                        }

                    default:
                        throw new RuntimeException("JobDataFilter: 不支持的过滤操作");
                }
            }

            // 当 未被过滤，则添加到过滤结果
            if (!isFiltered) {
                filterDatas.add(data);
            }
        });

        task.setProduceDataList(filterDatas);
    }
}
