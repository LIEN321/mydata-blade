package org.springblade.mydata.manage.wrapper;

import cn.hutool.core.util.ObjectUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.vo.TaskVO;

/**
 * 集成任务包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-11
 */
public class TaskWrapper extends BaseEntityWrapper<Task, TaskVO> {

    public static TaskWrapper build() {
        return new TaskWrapper();
    }

    @Override
    public TaskVO entityVO(Task task) {
        TaskVO taskVO = BeanUtil.copy(task, TaskVO.class);

        // 查询数据项所属数据集
        Data data = MdCache.getData(task.getDataId());
        if (ObjectUtil.isNotNull(data)) {
            taskVO.setDataCode(data.getDataCode());
            taskVO.setDataName(data.getDataName());
        }

        // 查询所属环境
        Env env = MdCache.getEnv(task.getEnvId());
        if (ObjectUtil.isNotNull(env)) {
            taskVO.setEnvName(env.getEnvName());
        }

        // 查询应用接口
        Api appApi = MdCache.getApi(task.getApiId());
        if (ObjectUtil.isNotNull(appApi)) {
            taskVO.setApiName(appApi.getApiName());
        }

        return taskVO;
    }

}
