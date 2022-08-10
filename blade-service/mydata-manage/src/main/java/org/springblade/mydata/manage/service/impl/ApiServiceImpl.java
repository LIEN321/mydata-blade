package org.springblade.mydata.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.common.util.MdUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.dto.ApiDTO;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.mapper.ApiMapper;
import org.springblade.mydata.manage.service.IApiService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springblade.mydata.manage.vo.ApiVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用接口 服务实现类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@Service
@AllArgsConstructor
public class ApiServiceImpl extends BaseServiceImpl<ApiMapper, Api> implements IApiService {

    private final ITaskService taskService;

    @Override
    public IPage<ApiVO> selectApiPage(IPage<ApiVO> page, ApiVO api) {
        return page.setRecords(baseMapper.selectApiPage(page, api));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(ApiDTO apiDTO) {
        // 参数校验
        checkApi(apiDTO);

        Api api = BeanUtil.copyProperties(apiDTO, Api.class, "reqHeaders", "reqParams");

        // 转换header参数
        List<Map<String, String>> reqHeaderMaps = apiDTO.getReqHeaders();
        if (CollUtil.isNotEmpty(reqHeaderMaps)) {
            LinkedHashMap<String, String> reqHeaders = new LinkedHashMap<>();
            reqHeaderMaps.forEach(map -> {
                reqHeaders.put(map.get("k"), map.get("v"));
            });
            api.setReqHeaders(reqHeaders);
        }

        // 转换param参数
        List<Map<String, String>> reqParamMaps = apiDTO.getReqParams();
        if (CollUtil.isNotEmpty(reqParamMaps)) {
            LinkedHashMap<String, String> reqParams = new LinkedHashMap<>();
            reqParamMaps.forEach(map -> {
                reqParams.put(map.get("k"), map.get("v"));
            });
            api.setReqParams(reqParams);
        }

        return saveOrUpdate(api);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteApi(List<Long> ids) {
        // 删除关联的任务
        ids.forEach(taskService::deleteByApi);

        return deleteLogic(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean syncTask(Long id) {
        Assert.notNull(id, "同步失败，参数id无效");

        Api api = MdCache.getApi(id);
        Assert.notNull(api, "同步失败，参数id不存在，id={}", id);

        // 更新任务地址 并重启运行中的任务
        taskService.updateApiUrlByApi(id, api.getApiUri());

        api.setSyncTaskTime(new Date());
        boolean result = updateById(api);
        if (!result) {
            throw new ServiceException("同步失败，更新API失败，请重试！");
        }
        return true;
    }

    private void checkApi(ApiDTO apiDTO) {
        Assert.notNull(apiDTO, "提交失败：参数无效");

        String apiName = apiDTO.getApiName();
        Assert.notBlank(apiName, "提交失败：名称 不能为空！");
        apiName = apiName.trim();
        Assert.isTrue(apiName.length() <= MdConstant.MAX_NAME_LENGTH, "提交失败：名称 不能超过{}位！", MdConstant.MAX_NAME_LENGTH);
        apiDTO.setApiName(apiName);

        Integer opType = apiDTO.getOpType();
        Assert.isTrue(MdUtil.isValidOpType(opType), "提交失败：API类型 {} 无效！", opType);

        String apiMethod = apiDTO.getApiMethod();
        Assert.isTrue(MdUtil.isValidHttpMethod(apiMethod), "提交失败：请求方法 {} 无效！", apiMethod);
        apiMethod = apiMethod.toUpperCase();
        apiDTO.setApiMethod(apiMethod);

        String apiUri = apiDTO.getApiUri();
        Assert.notBlank(apiUri, "提交失败：路径 不能为空！");
        apiUri = apiUri.trim();
        Assert.isTrue(apiName.length() <= MdConstant.MAX_URI_LENGTH, "提交失败：相对路径 不能超过{}位！", MdConstant.MAX_URI_LENGTH);
        apiDTO.setApiUri(apiUri);

        String dataType = apiDTO.getDataType();
        Assert.isTrue(MdUtil.isValidDataType(dataType), "提交失败：数据类型 {} 无效！", dataType);
        dataType = dataType.toUpperCase();
        apiDTO.setDataType(dataType);
    }
}
