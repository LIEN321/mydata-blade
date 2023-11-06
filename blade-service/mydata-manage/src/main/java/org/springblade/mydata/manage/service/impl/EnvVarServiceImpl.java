package org.springblade.mydata.manage.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mydata.manage.dto.EnvVarDTO;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.mapper.EnvVarMapper;
import org.springblade.mydata.manage.service.IEnvVarService;
import org.springblade.mydata.manage.vo.EnvVarVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 环境变量 服务实现类
 *
 * @author LIEN
 * @since 2023/11/1
 */
@Service
public class EnvVarServiceImpl extends BaseServiceImpl<EnvVarMapper, EnvVar> implements IEnvVarService {
    @Override
    public IPage<EnvVarVO> selectEnvVarPage(IPage<EnvVarVO> page, EnvVarVO envVar) {
        return page.setRecords(baseMapper.selectEnvVarPage(page, envVar));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(EnvVarDTO envVarDTO) {
        // 参数校验
        checkEnvVar(envVarDTO);

        Long id = envVarDTO.getId();
        Long envId = envVarDTO.getEnvId();
        String varName = envVarDTO.getVarName();

        // 校验变量名在指定环境是否唯一
        EnvVar check = findByNameInEnv(id, envId, varName);
        Assert.isNull(check, "操作失败：变量名 {} 已存在！", varName);

        // 复制提交的参数
        EnvVar envVar = BeanUtil.copyProperties(envVarDTO, EnvVar.class);

        return saveOrUpdate(envVar);
    }

    @Override
    public boolean deleteEnvVar(List<Long> ids) {
        return deleteLogic(ids);
    }

    private void checkEnvVar(EnvVarDTO envVarDTO) {
        Assert.notNull(envVarDTO, "提交失败：参数无效！");
        Long envId = envVarDTO.getEnvId();
        String varName = envVarDTO.getVarName();
        String varValue = envVarDTO.getVarValue();

        // 所属环境 不能为空
        Assert.notNull(envId, "提交失败：所属环境 无效！");

        // 变量名 不能为空
        Assert.notBlank(varName, "提交失败：变量名 不能为空！");
        // 变量名 长度不能超过限制
        Assert.isTrue(varName.length() <= MdConstant.MAX_NAME_LENGTH, "提交失败：变量名 不能超过{}位！",
                      MdConstant.MAX_NAME_LENGTH);
        // 变量值 长度不能超过限制
        // Assert.isTrue(varValue.length() <= MdConstant.MAX_LENGTH_512, "提交失败：变量值 不能超过{}位！", MdConstant.MAX_LENGTH_512);
    }

    @Override
    public EnvVar findByNameInEnv(Long envId, String varName) {
        LambdaQueryWrapper<EnvVar> queryWrapper = Wrappers.<EnvVar>lambdaQuery()
                .eq(EnvVar::getEnvId, envId)
                .eq(EnvVar::getVarName, varName);

        return getOne(queryWrapper);
    }

    @Override
    public List<EnvVar> findByNameInEnv(Long envId, Collection<String> varNames) {
        LambdaQueryWrapper<EnvVar> queryWrapper = Wrappers.<EnvVar>lambdaQuery()
                .eq(EnvVar::getEnvId, envId)
                .in(EnvVar::getVarName, varNames);

        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveByNameInEnv(EnvVar envVar) {
        // 参数校验
//        checkEnvVar(envVar);

        Long envId = envVar.getEnvId();
        String varName = envVar.getVarName();

        // 复制提交的参数
//        EnvVar envVar = BeanUtil.copyProperties(envVar, EnvVar.class);

        // 校验变量名在指定环境是否唯一
        EnvVar check = findByNameInEnv(envId, varName);
        if (check != null) {
            envVar.setId(check.getId());
        }

        return saveOrUpdate(envVar);
    }

    private EnvVar findByNameInEnv(Long id, Long envId, String varName) {
        LambdaQueryWrapper<EnvVar> queryWrapper = Wrappers.<EnvVar>lambdaQuery()
                .eq(EnvVar::getEnvId, envId)
                .eq(EnvVar::getVarName, varName)
                .ne(Func.notNull(id), EnvVar::getId, id);

        return getOne(queryWrapper);
    }
}
