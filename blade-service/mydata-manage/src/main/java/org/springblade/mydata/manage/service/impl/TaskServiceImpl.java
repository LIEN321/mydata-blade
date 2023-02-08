package org.springblade.mydata.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.common.util.MapUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.mydata.job.feign.IJobClient;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.dto.TaskDTO;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.entity.DataField;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.mapper.TaskMapper;
import org.springblade.mydata.manage.service.IDataFieldService;
import org.springblade.mydata.manage.service.ITaskLogService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springblade.mydata.manage.vo.TaskVO;
import org.springblade.mydata.manage.wrapper.TaskWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 集成任务 服务实现类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Service
@AllArgsConstructor
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, Task> implements ITaskService {

    private final IDataFieldService dataFieldService;
    private final IJobClient jobClient;
    private final ITaskLogService taskLogService;

    @Override
    public IPage<TaskVO> selectTaskPage(IPage<TaskVO> page, TaskVO task) {
        return page.setRecords(baseMapper.selectTaskPage(page, task));
    }

    @Override
    public IPage<TaskVO> taskPage(IPage<Task> page, Wrapper<Task> queryWrapper) {
        IPage<Task> pages = this.page(page, queryWrapper);
        List<Task> tasks = pages.getRecords();

        IPage<TaskVO> taskPage = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        List<TaskVO> taskVOList = TaskWrapper.build().listVO(tasks);
        taskPage.setRecords(taskVOList);
        return taskPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(TaskDTO taskDTO) {
        // 校验参数
        check(taskDTO);

        // 查询data
        Data data = MdCache.getData(taskDTO.getDataId());
        Assert.notNull(data, "提交失败：所选数据项 不存在！");

        // 查询data的主键字段
        DataField idField = dataFieldService.findIdField(taskDTO.getDataId());
        Assert.notNull(idField, "提交失败：所选数据项 缺少唯一标识字段！");

        // 查询api
        Api api = MdCache.getApi(taskDTO.getApiId());
        Assert.notNull(api, "提交失败：所选API 不存在！");

        // 查询环境
        Env env = MdCache.getEnv(taskDTO.getEnvId());
        Assert.notNull(env, "提交失败：所选环境 不存在！");

        Task task = BeanUtil.copyProperties(taskDTO, Task.class);
        // 复制data的编号
        task.setDataCode(data.getDataCode());
        // 复制标识字段的编号
        task.setIdFieldCode(idField.getFieldCode());
        // 复制api的操作类型
        task.setOpType(api.getOpType());
        // 复制api的请求方法
        task.setApiMethod(api.getApiMethod());
        // 复制api的数据类型
        task.setDataType(api.getDataType());

        // 从env和api中 汇总header、param，优先级api > env
        mergeHeaderAndParam(task, api, env);

        // 保存或更新task
        return saveOrUpdate(task);
    }

    @Override
    public TaskVO detail(Long id) {
        Assert.notNull(id);

        Task task = getById(id);

        Assert.notNull(task);

        return TaskWrapper.build().entityVO(task);
    }

    //    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean startTask(Long id) {
        // 校验参数
        Assert.notNull(id);

        Task task = new Task();
        task.setId(id);
        task.setTaskStatus(MdConstant.TASK_STATUS_RUNNING);

        boolean result = updateById(task);

        // 若任务不是订阅模式 才能启动（fix #707419847）
        if (result && !MdConstant.TASK_IS_SUBSCRIBED.equals(task.getIsSubscribed())) {
            // 通知任务服务
            jobClient.startTask(id);
        }
        return result;
    }

    //    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean stopTask(Long id) {
        // 校验参数
        Assert.notNull(id);

        Task task = new Task();
        task.setId(id);
        task.setTaskStatus(MdConstant.TASK_STATUS_STOPPED);

        boolean result = updateById(task);

        if (result) {
            // 通知任务服务
            jobClient.stopTask(id);
        }
        return result;
    }

    @Override
    public List<Task> listRunningTasks() {
        LambdaQueryWrapper<Task> queryWrapper = Wrappers.<Task>lambdaQuery()
                .eq(Task::getTaskStatus, MdConstant.TASK_STATUS_RUNNING)
                .ne(Task::getIsSubscribed, MdConstant.TASK_IS_SUBSCRIBED);
        return list(queryWrapper);
    }

    @Override
    public List<Task> listRunningSubTasks(Long dataId) {
        Assert.notNull(dataId, "参数dataId无效，dataId = {}", dataId);

        LambdaQueryWrapper<Task> queryWrapper = Wrappers.<Task>lambdaQuery()
                .eq(Task::getTaskStatus, MdConstant.TASK_STATUS_RUNNING)
                .eq(Task::getIsSubscribed, MdConstant.TASK_IS_SUBSCRIBED)
                .eq(Task::getDataId, dataId);

        return list(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean failTask(Long id) {
        Assert.notNull(id);

        Task task = new Task();
        task.setId(id);
        task.setTaskStatus(MdConstant.TASK_STATUS_FAILED);

        return updateById(task);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Long id) {
        Task task = MdCache.getTask(id);
        if (task == null) {
            return true;
        }
        if (task.getTaskStatus() == MdConstant.TASK_STATUS_RUNNING) {
            throw new ServiceException("删除失败，任务正在运行！");
        }

        taskLogService.deleteByTask(id);
        return removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<Long> ids) {
        ids.forEach(this::delete);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByApi(Long apiId) {
        List<Task> tasks = list(null, apiId, null);
        if (CollUtil.isNotEmpty(tasks)) {
            tasks.forEach(task -> this.delete(task.getId()));
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByEnv(Long envId) {
        List<Task> tasks = list(null, null, envId);
        if (CollUtil.isNotEmpty(tasks)) {
            tasks.forEach(task -> this.delete(task.getId()));
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByData(Long dataId) {
        List<Task> tasks = list(dataId, null, null);
        if (CollUtil.isNotEmpty(tasks)) {
            tasks.forEach(task -> this.delete(task.getId()));
        }
        return true;
    }

    //    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateApiUrlByEnv(Env env) {
        // 根据环境查询任务
        List<Task> tasks = list(null, null, env.getId());
        if (CollUtil.isNotEmpty(tasks)) {
            // 批量更新任务的接口地址和参数
            tasks.forEach(task -> {
                Api api = MdCache.getApi(task.getApiId());
                if (api != null) {
                    mergeHeaderAndParam(task, api, env);
                }
            });
            updateBatchById(tasks);

            // 筛选运行中的任务
            List<Task> runningTasks = tasks.stream()
                    .filter(task -> task.getTaskStatus() == MdConstant.TASK_STATUS_RUNNING)
                    .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(runningTasks)) {
                // 重启任务的调度
                runningTasks.forEach(task -> jobClient.restartTask(task.getId()));
            }
        }
        return true;
    }

    //    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateApiUrlByApi(Api api) {
        // 根据环境查询任务
        List<Task> tasks = list(null, api.getId(), null);
        if (CollUtil.isNotEmpty(tasks)) {
            // 批量更新任务的api地址
            tasks.forEach(task -> {
                Env env = MdCache.getEnv(task.getEnvId());
                if (env != null) {
                    mergeHeaderAndParam(task, api, env);
                }
            });
            updateBatchById(tasks);

            // 筛选运行中的任务
            List<Task> runningTasks = tasks.stream()
                    .filter(task -> task.getTaskStatus() == MdConstant.TASK_STATUS_RUNNING)
                    .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(runningTasks)) {
                // 重启任务的调度
                runningTasks.forEach(task -> jobClient.restartTask(task.getId()));
            }
        }
        return true;
    }

    private void check(TaskDTO taskDTO) {
        // 校验参数
        Assert.notNull(taskDTO);

        String taskName = taskDTO.getTaskName();
        Assert.notBlank(taskName, "名称 不能为空！");
        taskName = StrUtil.trim(taskName);
        Assert.isTrue(StrUtil.length(taskName) <= MdConstant.MAX_NAME_LENGTH, "名称 长度不能超过{}！", MdConstant.MAX_NAME_LENGTH);
        taskDTO.setTaskName(taskName);

        Assert.notNull(taskDTO.getEnvId(), "提交失败：环境无效！");
        Assert.notNull(taskDTO.getApiId(), "提交失败：API无效！");
        Assert.notNull(taskDTO.getDataId(), "提交失败：数据项无效！");
        // 不是订阅任务，则任务周期必填
        if (!MdConstant.TASK_IS_SUBSCRIBED.equals(taskDTO.getIsSubscribed())) {
            Assert.notBlank(taskDTO.getTaskPeriod(), "提交失败：任务周期 不能为空！");
        }

        Map<String, String> fieldMapping = taskDTO.getFieldMapping();
        Assert.notEmpty(fieldMapping, "提交失败：字段映射无效！");
        Collection<String> values = fieldMapping.values();
        boolean hasValidValue = false;
        for (String value : values) {
            if (StrUtil.isNotBlank(value)) {
                hasValidValue = true;
                break;
            }
        }
        Assert.isTrue(hasValidValue, "提交失败：字段映射无效！");
    }

    private List<Task> list(Long dataId, Long apiId, Long envId) {
        LambdaQueryWrapper<Task> queryTaskWrapper = Wrappers.<Task>lambdaQuery()
                .eq(ObjectUtil.isNotNull(dataId), Task::getDataId, dataId)
                .eq(ObjectUtil.isNotNull(apiId), Task::getApiId, apiId)
                .eq(ObjectUtil.isNotNull(envId), Task::getEnvId, envId);

        return list(queryTaskWrapper);
    }

    private void mergeHeaderAndParam(Task task, Api api, Env env) {
        // 拼接完整的url
        String apiUrl = env.getEnvPrefix() + api.getApiUri();
        task.setApiUrl(apiUrl);

        // 从env和api中 汇总header、param，优先级api > env
        LinkedHashMap<String, String> headers = (LinkedHashMap<String, String>) MapUtil.union(env.getGlobalHeaders(), api.getReqHeaders());
        LinkedHashMap<String, String> params = (LinkedHashMap<String, String>) MapUtil.union(env.getGlobalParams(), api.getReqParams());
        task.setReqHeaders(headers);
        task.setReqParams(params);
    }
}
