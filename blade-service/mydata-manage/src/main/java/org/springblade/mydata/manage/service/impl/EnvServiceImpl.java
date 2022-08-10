package org.springblade.mydata.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.dto.EnvDTO;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.mapper.EnvMapper;
import org.springblade.mydata.manage.service.IEnvService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springblade.mydata.manage.vo.EnvVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 环境配置 服务实现类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Service
@AllArgsConstructor
public class EnvServiceImpl extends BaseServiceImpl<EnvMapper, Env> implements IEnvService {

    private final ITaskService taskService;

    @Override
    public IPage<EnvVO> selectEnvPage(IPage<EnvVO> page, EnvVO env) {
        return page.setRecords(baseMapper.selectEnvPage(page, env));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(EnvDTO envDTO) {
        Assert.notNull(envDTO, "提交失败：参数无效！");

        // 若id为空，则执行新增，否则执行更新
        if (ObjectUtil.isNull(envDTO.getId())) {
            return save(envDTO);
        }
        return update(envDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteEnv(List<Long> ids) {

        // 删除关联的任务
        ids.forEach(taskService::deleteByEnv);

        return deleteLogic(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean syncTask(Long id) {
        Assert.notNull(id, "同步失败，参数id无效");

        Env env = MdCache.getEnv(id);
        Assert.notNull(env, "同步失败，参数id不存在，id={}", id);

        // 更新任务地址 并重启运行中的任务
        taskService.updateApiUrlByEnv(id, env.getEnvPrefix());

        env.setSyncTaskTime(new Date());
        boolean result = updateById(env);
        if (!result) {
            throw new ServiceException("同步失败，更新环境失败，请重试！");
        }
        return true;
    }

    private boolean save(EnvDTO envDTO) {
        checkEnv(envDTO);

        Env env = BeanUtil.copyProperties(envDTO, Env.class);
        return save(env);
    }

    private boolean update(EnvDTO envDTO) {
        checkEnv(envDTO);


        Env env = BeanUtil.copyProperties(envDTO, Env.class);
        return updateById(env);
    }

    private void checkEnv(EnvDTO envDTO) {
        String envName = envDTO.getEnvName();
        String envPrefix = envDTO.getEnvPrefix();

        Assert.notBlank(envName, "提交失败：环境名称 不能为空！");
        Assert.notBlank(envPrefix, "提交失败：前置路径 不能为空！");
        Assert.isTrue(envName.length() <= MdConstant.MAX_NAME_LENGTH, "提交失败：环境名称 不能超过{}位！", MdConstant.MAX_NAME_LENGTH);
        Assert.isTrue(envPrefix.length() <= MdConstant.MAX_URI_LENGTH, "提交失败：前置路径 不能超过{}位！", MdConstant.MAX_URI_LENGTH);
    }
}
