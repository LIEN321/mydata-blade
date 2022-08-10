package org.springblade.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 集成任务视图实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode
public class TaskVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 所属环境
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long envId;
    /**
     * 所属应用接口
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apiId;
    /**
     * 接口完整地址
     */
    private String apiUrl;
    /**
     * 操作类型，1-提供数据、2-消费数据
     */
    private Integer opType;
    /**
     * 接口请求类型
     */
    private String apiMethod;
    /**
     * 所属数据
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataId;
    /**
     * 任务周期
     */
    private String taskPeriod;
    /**
     * 字段层级前缀
     */
    private String apiFieldPrefix;
    /**
     * 字段映射
     */
    private Map<String, String> fieldMapping;
    /**
     * 运行状态：0-停止，1-运行
     */
    private Integer taskStatus;
    /**
     * 接口数据类型：JSON
     */
    private String dataType;

    /**
     * 数据项编号
     */
    private String dataCode;

    /**
     * 数据项名称
     */
    private String dataName;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 环境名称
     */
    private String envName;

    /**
     * 是否为订阅任务：0-不订阅，1-订阅
     */
    private Integer isSubscribed;
}
