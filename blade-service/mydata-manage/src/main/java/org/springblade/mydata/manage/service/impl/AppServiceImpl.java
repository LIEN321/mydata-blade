package org.springblade.mydata.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.mydata.manage.dto.AppDTO;
import org.springblade.mydata.manage.entity.App;
import org.springblade.mydata.manage.mapper.AppMapper;
import org.springblade.mydata.manage.service.IApiService;
import org.springblade.mydata.manage.service.IAppService;
import org.springblade.mydata.manage.vo.AppVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 应用 服务实现类
 *
 * @author LIEN
 * @since 2023-01-31
 */
@Service
@AllArgsConstructor
public class AppServiceImpl extends BaseServiceImpl<AppMapper, App> implements IAppService {

    private final IApiService apiService;

    @Override
    public IPage<AppVO> selectAppPage(IPage<AppVO> page, AppVO app) {
        return page.setRecords(baseMapper.selectAppPage(page, app));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(AppDTO appDTO) {
        // 参数校验
        checkApp(appDTO);

        // 复制提交的参数
        App app = BeanUtil.copyProperties(appDTO, App.class);

        return saveOrUpdate(app);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteApp(List<Long> ids) {
        // 删除关联的接口
        if (CollUtil.isNotEmpty(ids)) {
            ids.forEach(apiService::deleteByApp);
            return deleteLogic(ids);
        }

        return false;
    }

    /**
     * 校验参数
     *
     * @param appDTO 待校验的参数
     */
    private void checkApp(AppDTO appDTO) {
        Assert.notNull(appDTO, "提交失败：参数无效！");
        String appCode = appDTO.getAppCode();
        String appName = appDTO.getAppName();

        // 应用编号 不能为空
        Assert.notBlank(appCode, "提交失败：应用编号 不能为空！");
        // 应用编号 长度不能超过限制
        Assert.isTrue(appCode.length() <= MdConstant.MAX_CODE_LENGTH, "提交失败：应用编号 不能超过{}位！", MdConstant.MAX_URI_LENGTH);

        // 应用名称 不能为空
        Assert.notBlank(appName, "提交失败：应用名称 不能为空！");
        // 应用名称 长度不能超过限制
        Assert.isTrue(appName.length() <= MdConstant.MAX_NAME_LENGTH, "提交失败：应用名称 不能超过{}位！", MdConstant.MAX_NAME_LENGTH);
    }
}
