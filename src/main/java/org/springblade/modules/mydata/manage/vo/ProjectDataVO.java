package org.springblade.modules.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目数据项VO
 *
 * @author LIEN
 * @since 2023/12/12
 */
@Data
public class ProjectDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 数据编号
     */
    private String dataCode;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 数据量
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataCount;

    /**
     * 提供数据的应用数
     */
    @JsonSerialize(using = ToStringSerializer.class, nullsUsing = NullSerializer.class)
    private Long provideAppCount;

    /**
     * 消费数据的应用数
     */
    @JsonSerialize(using = ToStringSerializer.class, nullsUsing = NullSerializer.class)
    private Long consumeAppCount;

    /**
     * 运行中的任务数
     */
    @JsonSerialize(using = ToStringSerializer.class, nullsUsing = NullSerializer.class)
    private Long runningTaskCount;

    /**
     * 已停止的任务数
     */
    @JsonSerialize(using = ToStringSerializer.class, nullsUsing = NullSerializer.class)
    private Long stoppedTaskCount;

    /**
     * 异常的任务数
     */
    @JsonSerialize(using = ToStringSerializer.class, nullsUsing = NullSerializer.class)
    private Long failedTaskCount;
}
