package org.springblade.mydata.data;

import cn.hutool.core.lang.Assert;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务数据 操作类
 *
 * @author LIEN
 * @date 2022/7/13
 */
@Component
public class BizDataDAO {

    @Resource
    private MultiMongoFactory mongoFactory;

    /**
     * 保存单个数据
     *
     * @param tenantId 租户id
     * @param dataCode 数据编号，即集合名称
     * @param data     业务数据
     */
    public void insert(String tenantId, String dataCode, Map<String, Object> data) {
        mongoFactory.getTemplate(tenantId).insert(data, dataCode);
    }

    /**
     * 批量保存数据
     *
     * @param tenantId 租户id
     * @param dataCode 数据编号，即集合名称
     * @param dataList 业务数据列表
     */
    public void insertBatch(String tenantId, String dataCode, List<Map<String, Object>> dataList) {
        mongoFactory.getTemplate(tenantId).insert(dataList, dataCode);
    }

    public void update(String tenantId, String dataCode, String idField, String idValue, Map<String, Object> data) {
        Query query = new Query(Criteria.where(idField).is(idValue));
        Document document = new Document(data);
        Update update = Update.fromDocument(document);
        mongoFactory.getTemplate(tenantId).updateFirst(query, update, dataCode);
    }

    public List<Map> listAll(String tenantId, String dataCode) {
        return mongoFactory.getTemplate(tenantId).findAll(Map.class, dataCode);
    }

    public List<Map> list(String tenantId, String dataCode, int size) {
        Assert.isTrue(size >= 0);
        Query query = new Query();
        query.limit(size);
        return mongoFactory.getTemplate(tenantId).find(query, Map.class, dataCode);
    }

    public List<Map> page(String tenantId, String dataCode, int pageNo, int pageSize) {
        Query query = new Query();
        query.skip((pageNo - 1) * pageSize);
        query.limit(pageSize);
        return mongoFactory.getTemplate(tenantId).find(query, Map.class, dataCode);
    }

    public long total(String tenantId, String dataCode) {
        Query query = new Query();
        return mongoFactory.getTemplate(tenantId).count(query, dataCode);
    }

    public Map<String, Object> findById(String tenantId, String dataCode, String idCode, Object idValue) {
        Query query = new Query(Criteria.where(idCode).is(idValue));
        return mongoFactory.getTemplate(tenantId).findOne(query, BasicDBObject.class, dataCode);
    }

    public void drop(String tenantId, String dataCode) {
        mongoFactory.getTemplate(tenantId).dropCollection(dataCode);
    }

}
