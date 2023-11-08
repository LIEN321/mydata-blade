package org.springblade.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务 相关统计DTO
 *
 * @author LIEN
 * @since 2023/2/23
 */
@Data
@EqualsAndHashCode
public class TaskStatDTO {
    /**
     * 任务数量
     */
    private Long taskCount;
    /**
     * 运行的任务数量
     */
    private Long runningCount;
    /**
     * 异常的任务数量
     */
    private Long failedCount;
    /**
     * 停止的任务数量
     */
    private Long stoppedCount;
}
