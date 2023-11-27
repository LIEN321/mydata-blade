package org.springblade.modules.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.mydata.manage.entity.DataField;
import org.springblade.modules.mydata.manage.vo.DataFieldVO;

import java.util.List;

/**
 * 标准数据项字段 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-09
 */
public interface DataFieldMapper extends BaseMapper<DataField> {

    /**
     * 自定义分页
     *
     * @param page
     * @param dataField
     * @return
     */
    List<DataFieldVO> selectDataFieldPage(IPage page, DataFieldVO dataField);

}
