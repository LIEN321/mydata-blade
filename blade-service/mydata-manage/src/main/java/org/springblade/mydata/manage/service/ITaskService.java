package org.springblade.mydata.manage.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.mydata.manage.dto.TaskDTO;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.vo.TaskVO;

import java.util.List;

/**
 * 集成任务 服务类
 *
 * @author LIEN
 * @since 2022-07-11
 */
public interface ITaskService extends BaseService<Task> {

    /**
     * 自定义分页
     *
     * @param page
     * @param task
     * @return
     */
    IPage<TaskVO> selectTaskPage(IPage<TaskVO> page, TaskVO task);

    /**
     * 任务分页列表
     *
     * @param page         分页
     * @param queryWrapper 过滤条件
     * @return 分页列表
     */
    IPage<TaskVO> taskPage(IPage<Task> page, Wrapper<Task> queryWrapper);

    /**
     * 新增或修改 集成任务
     *
     * @param taskDTO 集成任务
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(TaskDTO taskDTO);

    /**
     * 根据id查询 集成任务
     *
     * @param id 主键值
     * @return 集成任务
     */
    TaskVO detail(Long id);

    /**
     * 设置任务为 运行 状态
     *
     * @param id 主键值
     * @return 操作结果，true-成功，false-失败
     */
    boolean startTask(Long id);

    /**
     * 设置任务为 停止 状态
     *
     * @param id 主键值
     * @return 操作结果，true-成功，false-失败
     */
    boolean stopTask(Long id);

    /**
     * 查询所有运行中的任务
     *
     * @return 任务列表
     */
    List<Task> listRunningTasks();

    /**
     * 查询订阅指定数据项的、运行中的任务
     *
     * @param dataId 数据项id
     * @return 任务列表
     */
    List<Task> listRunningSubTasks(Long dataId);

    /**
     * 设置任务为 异常 状态
     *
     * @param id 主键值
     * @return 操作结果，true-成功，false-失败
     */
    boolean failTask(Long id);

    /**
     * 删除单个任务
     *
     * @param id 任务id
     * @return 操作结果，true-成功，false-失败
     */
    boolean delete(Long id);

    /**
     * 删除多个任务
     *
     * @param ids 任务id
     * @return 操作结果，true-成功，false-失败
     */
    boolean delete(List<Long> ids);

    /**
     * 根据api删除关联的任务
     *
     * @param apiId API id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteByApi(Long apiId);

    /**
     * 根据环境删除关联的任务
     *
     * @param envId 环境id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteByEnv(Long envId);

    /**
     * 根据数据项删除关联的任务
     *
     * @param dataId 数据项id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteByData(Long dataId);

    /**
     * 根据环境及其前缀地址 更新相关任务的完整接口地址，并重启运行中的任务
     *
     * @param env 环境
     * @return 操作结果，true-成功，false-失败
     */
    boolean updateApiUrlByEnv(Env env);

    /**
     * 根据api及其地址 更新相关任务的完整接口地址，并重启运行中的任务
     *
     * @param api API
     * @return 操作结果，true-成功，false-失败
     */
    boolean updateApiUrlByApi(Api api);
}