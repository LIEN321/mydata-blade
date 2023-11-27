package org.springblade.modules.mydata.manage.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.mydata.manage.entity.TaskLog;
import org.springblade.modules.mydata.manage.vo.TaskLogVO;

/**
 * 集成任务日志包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-18
 */
public class TaskLogWrapper extends BaseEntityWrapper<TaskLog, TaskLogVO> {

    public static TaskLogWrapper build() {
        return new TaskLogWrapper();
    }

    @Override
    public TaskLogVO entityVO(TaskLog taskLog) {
        TaskLogVO taskLogVO = BeanUtil.copy(taskLog, TaskLogVO.class);

        return taskLogVO;
    }

}
