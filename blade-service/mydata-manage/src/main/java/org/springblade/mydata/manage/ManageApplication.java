package org.springblade.mydata.manage;

import org.springblade.common.constant.AppConstant;
import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * mydata管理模块启动类
 *
 * @author LIEN
 * @date 2022/7/7
 */
@BladeCloudApplication
public class ManageApplication {
    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_MYDATA_MANAGE, ManageApplication.class, args);
    }
}
