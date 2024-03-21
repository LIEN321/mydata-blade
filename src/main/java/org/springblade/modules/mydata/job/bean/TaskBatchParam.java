package org.springblade.modules.mydata.job.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务分批参数
 *
 * @author LIEN
 * @since 2024/3/20
 */
@Data
public class TaskBatchParam implements Serializable {
    private static final long serialVersionUID = -2198448128993256536L;

    /**
     * 条件key
     */
    private String key;

    /**
     * 条件操作符
     */
    private String op;

    /**
     * 条件值
     */
    private Integer value;

    /**
     * 递增区间
     */
    private Integer step;
}
