package org.springblade.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.constant.MdConstant;
import org.springblade.mydata.manage.base.TenantEntity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 集成任务实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@TableName(value = "md_task", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class Task extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 所属环境
     */
    private Long envId;

    /**
     * 所属应用接口
     */
    private Long apiId;

    /**
     * 接口完整地址
     */
    private String apiUrl;

    /**
     * 操作类型
     *
     * @see MdConstant#DATA_PRODUCER
     * @see MdConstant#DATA_CONSUMER
     */
    private Integer opType;

    /**
     * 接口请求类型
     */
    private String apiMethod;

    /**
     * 所属数据
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
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
    @TableField(typeHandler = FastjsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private Map<String, String> fieldMapping;

    /**
     * 运行状态：0-停止，1-运行，2-异常
     */
    private Integer taskStatus;

    /**
     * 接口数据类型：JSON
     */
    private String dataType;

    /**
     * 数据编号
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String dataCode;

    /**
     * 数据主键字段编号
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String idFieldCode;

    /**
     * 是否为订阅任务：0-不订阅，1-订阅
     */
    private Integer isSubscribed;

    /**
     * 接口请求Header
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String, String> reqHeaders;

    /**
     * 接口请求参数
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String, String> reqParams;

    /**
     * 数据的过滤条件
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Map<String, String>> dataFilter;

    /**
     * 最后执行时间
     */
    private Date lastRunTime;

    /**
     * 最后成功时间
     */
    private Date lastSuccessTime;

    /**
     * 接口字段与变量名的映射
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String, String> fieldVarMapping;
}
