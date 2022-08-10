package org.springblade.test;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * @author LIEN
 */
@BladeCloudApplication
public class TestApplication {
    public static void main(String[] args) {
        BladeApplication.run("mydata-test", TestApplication.class, args);
    }
}
