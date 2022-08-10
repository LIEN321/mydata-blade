package org.springblade.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LIEN
 */
@Data
@TableName("demo_oa_user")
@EqualsAndHashCode(callSuper = false)
public class OaUser extends BaseEntity {
    private static final long serialVersionUID = -2136974355532381981L;
    private Long id;
    private String userCode;
    private String userName;
    private Date birthday;
    private Integer age;
    private BigDecimal salary;
}
