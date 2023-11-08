package org.springblade.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作台统计 视图实体类
 *
 * @author LIEN
 * @since 2023/2/21
 */
@Data
@EqualsAndHashCode
public class WorkplaceStatVO {
    /**
     * 数据项数量
     */
    private Long dataCount;

    /**
     * 业务数据总条数
     */
    private Long bizDataCount;

    // ----------------------------------------

    /**
     * 应用数量
     */
    private Long appCount;

    // ----------------------------------------

    /**
     * API数量
     */
    private Long apiCount;
    /**
     * 提供数据的API数量
     */
    private Long producerCount;
    /**
     * 消费数据的API数量
     */
    private Long consumerCount;

    // ----------------------------------------

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
