package org.springblade.modules.mydata.data;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源工厂类
 *
 * @author LIEN
 * @since 2020/9/22
 */
@Component
public class MultiMongoFactory {

    private final Map<String, MongoDatabaseFactory> mongoDbFactoryMap = new HashMap<>();

    /**
     * 获取配置文件的副本集连接
     */
    @Value("${mydata.mongodb.url}")
    private String mongodbUrl;

    private static final String DB_PREFIX = "tenant_";

    public MongoTemplate getTemplate(String tenantId) {

        String dbName = DB_PREFIX + MD5.create().digestHex(tenantId);

        //查找项目对应的MongFactory
        MongoDatabaseFactory mongoDatabaseFactory = mongoDbFactoryMap.get(dbName);
        //实例化
        if (mongoDatabaseFactory == null) {

            //替换数据源
            String connectionString = StrUtil.format(mongodbUrl, dbName);

            mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(connectionString);
            mongoDbFactoryMap.put(dbName, mongoDatabaseFactory);
        }

        return new MultiMongoTemplate(mongoDatabaseFactory);
    }
}
