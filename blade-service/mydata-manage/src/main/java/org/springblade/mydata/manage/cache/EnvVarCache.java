package org.springblade.mydata.manage.cache;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.service.IEnvVarService;

/**
 * 环境变量缓存
 *
 * @author LIEN
 * @since 2023/11/8
 */
public class EnvVarCache {
    private static final String CACHE_MANAGE = "mydata:env:";

    private static final String ENV_VAR = ":var:";

    private static final IEnvVarService envVarService;

    static {
        envVarService = SpringUtil.getBean(IEnvVarService.class);
    }

    private static String getCacheName() {
        return getCacheName(SecureUtil.getTenantId());
    }

    /**
     * 后台服务 获取缓存时 需要传租户id
     *
     * @param tenantId 租户id
     * @return cacheName
     */
    private static String getCacheName(String tenantId) {
        return CACHE_MANAGE.concat(tenantId);
    }

    /**
     * 后台服务 获取环境变量
     *
     * @param tenantId 租户id
     * @param envId    环境id
     * @param varName  变量名
     * @return 环境变量
     */
    public static EnvVar getEnvVar(String tenantId, Long envId, String varName) {
        return CacheUtil.get(getCacheName(tenantId), envId + ENV_VAR, varName, () -> envVarService.findByNameInEnv(envId, varName));
    }

    public static void clearEnvVar(Long envId, String... varNames) {
        if (envId == null || ArrayUtil.isEmpty(varNames)) {
            return;
        }

        for (String varName : varNames) {
            CacheUtil.evict(getCacheName(), envId + ENV_VAR, varName);
        }
    }

    public static void clearEnvVar(String tenantId, Long envId, String... varNames) {
        if (StrUtil.isEmpty(tenantId) || envId == null || ArrayUtil.isEmpty(varNames)) {
            return;
        }

        for (String varName : varNames) {
            CacheUtil.evict(getCacheName(tenantId), envId + ENV_VAR, varName);
        }
    }
}
