package org.springblade.modules.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 工作台 任务列表
 *
 * @author LIEN
 * @since 2024-02-19
 */
@Data
@EqualsAndHashCode
public class WorkplaceTaskVO {
    /**
     * 最近成功的任务
     */
    private List<TaskVO> successTasks;

    /**
     * 最近失败的任务
     */
    private List<TaskVO> failedTasks;
}
