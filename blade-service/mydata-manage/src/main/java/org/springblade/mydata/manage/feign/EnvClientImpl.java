package org.springblade.mydata.manage.feign;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.cache.EnvVarCache;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.service.IEnvService;
import org.springblade.mydata.manage.service.IEnvVarService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * 环境 和 环境变量 Feign实现类
 *
 * @author LIEN
 * @since 2023/11/5
 */
@RestController
@AllArgsConstructor
public class EnvClientImpl implements IEnvClient {
    private IEnvVarService envVarService;

    private IEnvService envService;

    @Override
    public R saveVar(EnvVar envVar) {
        return R.status(envVarService.saveByNameInEnv(envVar));
    }

    @Override
    public R<List<EnvVar>> getVars(Long envId, Collection<String> varNames) {
        List<EnvVar> resultList = CollUtil.newArrayList();
        Env env = envService.getById(envId);

        // redis中没有缓存 需要查数据库的变量名
        if (env != null && CollUtil.isNotEmpty(varNames)) {
            varNames.forEach(varName -> {
                // 尝试从redis获取变量
                EnvVar envVar = EnvVarCache.getEnvVar(env.getTenantId(), envId, varName);
                if (envVar != null) {
                    // 缓存对象有效 存入返回结果列表
                    resultList.add(envVar);
                }
            });
        }

        return R.data(resultList);
    }
}
