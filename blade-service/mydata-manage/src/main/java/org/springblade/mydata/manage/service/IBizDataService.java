package org.springblade.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.dto.BizDataDTO;

import java.util.List;
import java.util.Map;

/**
 * 标准数据项 服务类
 *
 * @author LIEN
 * @since 2022-07-08
 */
public interface IBizDataService {

    /**
     * 自定义分页
     *
     * @param page       分页
     * @param bizDataDTO 参数
     * @return 数据列表
     */
    IPage<Map> bizDataPage(IPage<List<Map>> page, BizDataDTO bizDataDTO);

    /**
     * 获取数据项的总数
     *
     * @param dataId 数据项id
     * @return 总数
     */
    long getTotalCount(Long dataId);

    /**
     * 删除指定数据项的业务数据
     *
     * @param dataId 数据项id
     * @return 操作结果，true-成功，false-失败
     */
    boolean dropBizData(Long dataId);
}
