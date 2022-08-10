package org.springblade.mydata.manage.cache;

import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.service.IApiService;
import org.springblade.mydata.manage.service.IDataService;
import org.springblade.mydata.manage.service.IEnvService;
import org.springblade.mydata.manage.service.ITaskService;

/**
 * mydata-manage 缓存
 *
 * @author LIEN
 * @date 2022/7/12
 */
public class MdCache {

    private static final String CACHE_MANAGE = "mydata:manage:";
    private static final String DATA_ID = "data:id:";
    private static final String ENV_ID = "env:id:";
    private static final String API_ID = "api:id:";
    private static final String TASK_ID = "task:id:";

    private static final IDataService dataService;
    private static final IEnvService envService;
    private static final IApiService apiService;
    private static final ITaskService taskService;

    static {
        dataService = SpringUtil.getBean(IDataService.class);
        envService = SpringUtil.getBean(IEnvService.class);
        apiService = SpringUtil.getBean(IApiService.class);
        taskService = SpringUtil.getBean(ITaskService.class);
    }

    private static String getCacheName() {
        return CACHE_MANAGE.concat(SecureUtil.getTenantId());
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
     * 获取环境
     *
     * @param id 环境id
     * @return 环境
     */
    public static Env getEnv(Long id) {
        return CacheUtil.get(getCacheName(), ENV_ID, id, () -> envService.getById(id));
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
     * 获取任务
     *
     * @param id 任务id
     * @return 任务
     */
    public static Task getTask(Long id) {
        return CacheUtil.get(getCacheName(), TASK_ID, id, () -> taskService.getById(id));
    }

    /**
     * 清除缓存
     */
    public static void clear() {
        CacheUtil.clear(getCacheName());
    }
}
