package org.springblade.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.vo.ApiVO;

import java.util.List;
import java.util.Map;

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
        ApiVO apiVO = BeanUtil.copyProperties(api, ApiVO.class, "reqHeaders");

        Map<String, String> reqHeaders = api.getReqHeaders();
        if (CollUtil.isNotEmpty(reqHeaders)) {
            List<Map<String, String>> reqHeaderMaps = CollUtil.newArrayList();
            reqHeaders.forEach((k, v) -> {
                Map<String, String> map = MapUtil.newHashMap();
                map.put("k", k);
                map.put("v", v);
                reqHeaderMaps.add(map);
            });
            apiVO.setReqHeaders(reqHeaderMaps);
        }

        Map<String, String> reqParams = api.getReqParams();
        if (CollUtil.isNotEmpty(reqParams)) {
            List<Map<String, String>> reqParamMaps = CollUtil.newArrayList();
            reqParams.forEach((k, v) -> {
                Map<String, String> map = MapUtil.newHashMap();
                map.put("k", k);
                map.put("v", v);
                reqParamMaps.add(map);
            });
            apiVO.setReqParams(reqParamMaps);
        }

        return apiVO;
    }

}
