package org.springblade.modules.mydata.manage.cache;

import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;

/**
 * 系统缓存
 *
 * @author LIEN
 * @since 2023/12/7
 */
public class SystemCache {
    private static final String CACHE_MANAGE = "mydata:system:";

    private static final String USER_ID = "user:id:";

    private static final IUserService userService;

    static {
        userService = SpringUtil.getBean(IUserService.class);
    }

    private static String getCacheName() {
        return CACHE_MANAGE.concat(SecureUtil.getTenantId());
    }

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return 用户
     */
    public static User getUser(Long id) {
        return CacheUtil.get(getCacheName(), USER_ID, id, () -> userService.getById(id));
    }
}
