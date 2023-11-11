package org.springblade.mydata.data;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springblade.common.constant.MdConstant;
import org.springframework.data.mongodb.core.MongoTemplate;
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
 * @since 2022/7/13
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

    /**
     * 根据 多个唯一标识的组合 更新业务数据
     *
     * @param tenantId 租户id
     * @param dataCode 业务数据编号
     * @param idMap    唯一标识的组合
     * @param data     业务数据
     */
    public void update(String tenantId, String dataCode, Map<String, Object> idMap, Map<String, Object> data) {
        Query query = new Query();
        idMap.forEach((k, v) -> {
            query.addCriteria(Criteria.where(k).is(v));
        });

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

    public List<Map> list(String tenantId, String dataCode, List<BizDataFilter> bizDataFilters) {
        MongoTemplate mongoTemplate = mongoFactory.getTemplate(tenantId);
        Query query = new Query();
        // 遍历数据过滤条件
        if (CollUtil.isNotEmpty(bizDataFilters)) {
            // mongodb的查询条件集合
            List<Criteria> criteriaList = CollUtil.newArrayList();
            for (BizDataFilter bizDataFilter : bizDataFilters) {
                // 条件key
                String key = bizDataFilter.getKey();
                // 条件操作
                String op = bizDataFilter.getOp();
                // 条件值
                Object value = bizDataFilter.getValue();

                // 根据条件操作类型 调用mongodb对应的查询方法
                Criteria criteria = Criteria.where(key);
                switch (op) {
                    case MdConstant.DATA_OP_EQ:
                        criteria.is(value);
                        break;
                    case MdConstant.DATA_OP_NE:
                        criteria.ne(value);
                        break;
                    case MdConstant.DATA_OP_GT:
                        criteria.gt(value);
                        break;
                    case MdConstant.DATA_OP_GTE:
                        criteria.gte(value);
                        break;
                    case MdConstant.DATA_OP_LT:
                        criteria.lt(value);
                        break;
                    case MdConstant.DATA_OP_LTE:
                        criteria.lte(value);
                        break;

                    default:
                        throw new RuntimeException("BizDataDAO: 不支持的过滤操作");
                }
                // 存入mongodb的查询条件集合
                criteriaList.add(criteria);
            }

            // mongodb查询条件集合 加入查询中
            query.addCriteria(new Criteria().andOperator(criteriaList));
        }

        // 执行查询
        return mongoTemplate.find(query, Map.class, dataCode);
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

    /**
     * 根据 多个唯一标识的组合 查询业务数据
     *
     * @param tenantId 租户id
     * @param dataCode 业务数据编号
     * @param idMap    唯一标识组合
     * @return 业务数据
     */
    public Map<String, Object> findByIds(String tenantId, String dataCode, Map<String, Object> idMap) {
        Query query = new Query();
        idMap.forEach((k, v) -> {
            query.addCriteria(Criteria.where(k).is(v));
        });
        return mongoFactory.getTemplate(tenantId).findOne(query, BasicDBObject.class, dataCode);
    }

    public void drop(String tenantId, String dataCode) {
        mongoFactory.getTemplate(tenantId).dropCollection(dataCode);
    }

}
