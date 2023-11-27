package org.springblade.modules.mydata.manage.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 系统邮箱配置
 *
 * @author LIEN
 * @since 2023/3/18
 */
@Configuration
@Data
public class MailConfig {
    /**
     * 邮件服务器的SMTP地址
     */
    @Value("${mail.host}")
    private String host;

    /**
     * 邮件服务器的SMTP端口，可选，默认25
     */
    @Value("${mail.port}")
    private String port;

    /**
     * 发件人
     */
    @Value("${mail.from}")
    private String from;

    /**
     * 用户名
     */
    @Value("${mail.user}")
    private String user;

    /**
     * 密码
     */
    @Value("${mail.pass}")
    private String pass;
}
