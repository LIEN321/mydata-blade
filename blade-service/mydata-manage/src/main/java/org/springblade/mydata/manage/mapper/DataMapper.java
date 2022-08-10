package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.vo.DataVO;

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

}
