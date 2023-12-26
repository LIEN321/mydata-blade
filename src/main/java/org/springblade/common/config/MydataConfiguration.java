package org.springblade.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Mydata 配置
 *
 * @author LIEN
 * @since 2023/12/26
 */
@Configuration
@Data
public class MydataConfiguration {
    /**
     * 是否启用 加密业务数据库名
     */
    @Value("${mydata.secure.encrypt-db:false}")
    private boolean isEncryptDb;
}
