package org.springblade.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.mydata.manage.dto.DataDTO;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.vo.DataVO;

import java.util.List;

/**
 * 标准数据项 服务类
 *
 * @author LIEN
 * @since 2022-07-08
 */
public interface IDataService extends BaseService<Data> {

    /**
     * 自定义分页
     *
     * @param page
     * @param data
     * @return
     */
    IPage<DataVO> selectDataPage(IPage<DataVO> page, DataVO data);

    /**
     * 新增或更新
     *
     * @param dataDTO 数据项
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(DataDTO dataDTO);

    /**
     * 删除 数据项
     *
     * @param ids 主键值
     * @return
     */
    boolean remove(List<Long> ids);

    /**
     * 更新业务数据量
     *
     * @param dataId 数据项id
     * @return 操作结果，true-成功，false-失败
     */
    boolean updateDataCount(Long dataId);
}
