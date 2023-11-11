package org.springblade.mydata.job;

import org.springblade.common.constant.AppConstant;
import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * Job启动类
 *
 * @author LIEN
 * @since 2022/7/14
 */
@BladeCloudApplication
public class JobApplication {
    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_MYDATA_JOB, JobApplication.class, args);
    }
}
