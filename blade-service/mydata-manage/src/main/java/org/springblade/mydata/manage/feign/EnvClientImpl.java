package org.springblade.mydata.manage.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.entity.EnvVar;
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

    @Override
    public R saveVar(EnvVar envVar) {
        return R.status(envVarService.saveByNameInEnv(envVar));
    }

    @Override
    public R<List<EnvVar>> getVars(Long envId, Collection<String> varNames) {
        return R.data(envVarService.findByNameInEnv(envId, varNames));
    }
}
