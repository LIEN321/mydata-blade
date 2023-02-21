package org.springblade.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作台统计 视图实体类
 *
 * @author LIEN
 * @date 2023/2/21
 */
@Data
@EqualsAndHashCode
public class WorkplaceStatVO {
    /**
     * 数据项数量
     */
    public Long dataCount;

    /**
     * 业务数据总条数
     */
    public Long bizDataCount;

    // ----------------------------------------

    /**
     * 应用数量
     */
    public Long appCount;

    // ----------------------------------------

    /**
     * API数量
     */
    public Long apiCount;
    /**
     * 提供数据的API数量
     */
    public Long producerCount;
    /**
     * 消费数据的API数量
     */
    public Long consumerCount;

    // ----------------------------------------

    /**
     * 任务数量
     */
    public Long taskCount;
    /**
     * 运行的任务数量
     */
    public Long runningCount;
    /**
     * 异常的任务数量
     */
    public Long failedCount;
    /**
     * 停止的任务数量
     */
    public Long stoppedCount;
}
