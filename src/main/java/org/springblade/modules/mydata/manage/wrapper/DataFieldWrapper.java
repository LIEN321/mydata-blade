package org.springblade.modules.mydata.manage.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.mydata.manage.entity.DataField;
import org.springblade.modules.mydata.manage.vo.DataFieldVO;

/**
 * 标准数据项包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-08
 */
public class DataFieldWrapper extends BaseEntityWrapper<DataField, DataFieldVO> {

    public static DataFieldWrapper build() {
        return new DataFieldWrapper();
    }

    @Override
    public DataFieldVO entityVO(DataField dataField) {
        DataFieldVO dataFieldVO = BeanUtil.copy(dataField, DataFieldVO.class);

        return dataFieldVO;
    }

}
