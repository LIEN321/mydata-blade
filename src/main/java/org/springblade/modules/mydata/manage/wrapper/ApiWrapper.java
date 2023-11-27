package org.springblade.modules.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.common.util.MdUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.modules.mydata.manage.cache.ManageCache;
import org.springblade.modules.mydata.manage.entity.Api;
import org.springblade.modules.mydata.manage.entity.App;
import org.springblade.modules.mydata.manage.vo.ApiVO;

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

        App app = ManageCache.getApp(api.getAppId());
        if (app != null) {
            apiVO.setAppName(app.getAppName());
            apiVO.setAppCode(app.getAppCode());
        }

        return apiVO;
    }

}
