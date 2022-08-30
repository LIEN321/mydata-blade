package org.springblade.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.common.util.MdUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.vo.ApiVO;

/**
 * 应用接口包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-08
 */
public class ApiWrapper extends BaseEntityWrapper<Api, ApiVO> {

    public static ApiWrapper build() {
        return new ApiWrapper();
    }

    @Override
    public ApiVO entityVO(Api api) {
        ApiVO apiVO = BeanUtil.copyProperties(api, ApiVO.class, "reqHeaders", "reqParams");

        apiVO.setReqHeaders(MdUtil.switchMapToList(api.getReqHeaders()));
        apiVO.setReqParams(MdUtil.switchMapToList(api.getReqParams()));

        return apiVO;
    }

}
