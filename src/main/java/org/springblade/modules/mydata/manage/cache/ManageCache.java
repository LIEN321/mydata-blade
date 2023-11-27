package org.springblade.modules.mydata.manage.cache;

import cn.hutool.core.util.ArrayUtil;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.mydata.manage.entity.Api;
import org.springblade.modules.mydata.manage.entity.App;
import org.springblade.modules.mydata.manage.entity.Data;
import org.springblade.modules.mydata.manage.entity.Env;
import org.springblade.modules.mydata.manage.entity.Task;
import org.springblade.modules.mydata.manage.service.IApiService;
import org.springblade.modules.mydata.manage.service.IAppService;
import org.springblade.modules.mydata.manage.service.IDataService;
import org.springblade.modules.mydata.manage.service.IEnvService;
import org.springblade.modules.mydata.manage.service.ITaskService;

/**
 * mydata-manage 缓存
 *
 * @author LIEN
 * @since 2022/7/12
 */
public class ManageCache {

    private static final String CACHE_MANAGE = "mydata:manage:";

    private static final String DATA_ID = "data:id:";

    private static final String ENV_ID = "env:id:";

    private static final String API_ID = "api:id:";

    private static final String TASK_ID = "task:id:";

    private static final String APP_ID = "app:id:";

    private static final IDataService dataService;

    private static final IEnvService envService;

    private static final IApiService apiService;

    private static final ITaskService taskService;

    private static final IAppService appService;


    static {
        dataService = SpringUtil.getBean(IDataService.class);
        envService = SpringUtil.getBean(IEnvService.class);
        apiService = SpringUtil.getBean(IApiService.class);
        taskService = SpringUtil.getBean(ITaskService.class);
        appService = SpringUtil.getBean(IAppService.class);
    }

    private static String getCacheName() {
        return CACHE_MANAGE.concat(SecureUtil.getTenantId());
    }

    private static String getCacheName(String tenantId) {
        return CACHE_MANAGE.concat(tenantId);
    }

    /**
     * 获取数据项
     *
     * @param id 数据项id
     * @return 数据项
     */
    public static Data getData(Long id) {
        return CacheUtil.get(getCacheName(), DATA_ID, id, () -> dataService.getById(id));
    }

    /**
     * 后台服务 获取数据项
     *
     * @param tenantId 租户id
     * @param id       数据项id
     * @return 数据项
     */
    public static Data getData(String tenantId, Long id) {
        return CacheUtil.get(getCacheName(tenantId), DATA_ID, id, () -> dataService.getById(id));
    }

    /**
     * 删除指定数据项缓存
     *
     * @param ids 数据项id
     */
    public static void clearData(Long... ids) {
        clear(DATA_ID, ids);
    }

    /**
     * 获取环境
     *
     * @param id 环境id
     * @return 环境
     */
    public static Env getEnv(Long id) {
        return CacheUtil.get(getCacheName(), ENV_ID, id, () -> envService.getById(id));
    }

    /**
     * 删除指定环境缓存
     *
     * @param ids 环境id
     */
    public static void clearEnv(Long... ids) {
        clear(ENV_ID, ids);
    }

    /**
     * 获取API
     *
     * @param id API id
     * @return API
     */
    public static Api getApi(Long id) {
        return CacheUtil.get(getCacheName(), API_ID, id, () -> apiService.getById(id));
    }

    /**
     * 删除指定API缓存
     *
     * @param ids API id
     */
    public static void clearApi(Long... ids) {
        clear(API_ID, ids);
    }

    /**
     * 获取任务
     *
     * @param id 任务id
     * @return 任务
     */
    public static Task getTask(Long id) {
        return CacheUtil.get(getCacheName(), TASK_ID, id, () -> taskService.getById(id));
    }

    /**
     * 删除指定任务缓存
     *
     * @param ids API id
     */
    public static void clearTask(Long... ids) {
        clear(TASK_ID, ids);
    }

    /**
     * 获取应用
     *
     * @param id 应用id
     * @return 应用
     */
    public static App getApp(Long id) {
        return CacheUtil.get(getCacheName(), APP_ID, id, () -> appService.getById(id));
    }

    /**
     * 删除指定任务缓存
     *
     * @param ids API id
     */
    public static void clearApp(Long... ids) {
        clear(APP_ID, ids);
    }

//    /**
//     * 清除缓存
//     */
//    public static void clear() {
//        CacheUtil.clear(getCacheName());
//    }

    /**
     * 删除指定API缓存
     *
     * @param ids API id
     */
    private static void clear(String prefix, Long... ids) {
        if (ArrayUtil.isNotEmpty(ids)) {
            for (Long id : ids) {
                CacheUtil.evict(getCacheName(), prefix, id);
            }
        }
    }
}
