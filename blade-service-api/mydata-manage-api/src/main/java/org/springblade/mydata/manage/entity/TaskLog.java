package org.springblade.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.base.BaseEntity;

import java.util.Date;

/**
 * 集成任务日志实体类
 *
 * @author LIEN
 * @since 2022-07-18
 */
@Data
@TableName("md_task_log")
@EqualsAndHashCode(callSuper = true)
public class TaskLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 所属任务
     */
    private Long taskId;
    /**
     * 执行开始时间
     */
    private Date taskStartTime;
    /**
     * 执行结束时间
     */
    private Date taskEndTime;
    /**
     * 执行结果（0-失败，1-成功）
     */
    private Integer taskResult;
    /**
     * 执行内容
     */
    private String taskDetail;


}
