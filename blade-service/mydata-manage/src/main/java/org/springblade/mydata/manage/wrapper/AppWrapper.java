package org.springblade.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.mydata.manage.entity.App;
import org.springblade.mydata.manage.vo.AppVO;

/**
 * 应用包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2023-01-31
 */
public class AppWrapper extends BaseEntityWrapper<App, AppVO> {

    public static AppWrapper build() {
        return new AppWrapper();
    }

    @Override
    public AppVO entityVO(App app) {
        AppVO appVO = BeanUtil.copyProperties(app, AppVO.class);

        return appVO;
    }

}
