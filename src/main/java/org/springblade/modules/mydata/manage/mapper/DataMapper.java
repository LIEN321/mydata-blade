package org.springblade.modules.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.mydata.manage.dto.DataStatDTO;
import org.springblade.modules.mydata.manage.entity.Data;
import org.springblade.modules.mydata.manage.vo.DataVO;

import java.util.List;

/**
 * 标准数据项 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-08
 */
public interface DataMapper extends BaseMapper<Data> {

    /**
     * 自定义分页
     *
     * @param page
     * @param data
     * @return
     */
    List<DataVO> selectDataPage(IPage page, DataVO data);

    /**
     * 查询数据项的概况统计
     *
     * @return
     */
    DataStatDTO selectDataStat();
}
