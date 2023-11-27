package org.springblade.modules.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.mydata.manage.entity.TaskLog;

/**
 * 集成任务日志视图实体类
 *
 * @author LIEN
 * @since 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskLogVO extends TaskLog {
    private static final long serialVersionUID = 1L;

}
