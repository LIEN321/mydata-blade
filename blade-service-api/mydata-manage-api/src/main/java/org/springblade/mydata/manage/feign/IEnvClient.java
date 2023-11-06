package org.springblade.mydata.manage.feign;

import org.springblade.common.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 环境 和 环境变量 Feign接口
 *
 * @author LIEN
 * @since 2023/11/5
 */
@FeignClient(value = AppConstant.APPLICATION_MYDATA_MANAGE)
public interface IEnvClient {

    String API_PREFIX = "/api/env";
    String SAVE_VAR = API_PREFIX + "/saveVar";

    String GET_VARS = API_PREFIX + "/getVars";

    /**
     * 保存环境变量值
     *
     * @param envVar 环境变量
     * @return 操作结果，true-成功，false-失败
     */
    @PostMapping(SAVE_VAR)
    R saveVar(@RequestBody EnvVar envVar);

    @PostMapping(GET_VARS)
    R<List<EnvVar>> getVars(@RequestParam("envId") Long envId, @RequestBody Collection<String> varNames);
}
