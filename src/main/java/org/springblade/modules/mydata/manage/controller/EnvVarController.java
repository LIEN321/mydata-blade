package org.springblade.modules.mydata.manage.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.auth.granter.ITokenGranter;
import org.springblade.modules.auth.granter.PasswordTokenGranter;
import org.springblade.modules.auth.granter.TokenGranterBuilder;
import org.springblade.modules.auth.granter.TokenParameter;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.mydata.manage.cache.EnvVarCache;
import org.springblade.modules.mydata.manage.dto.EnvVarDTO;
import org.springblade.modules.mydata.manage.entity.EnvVar;
import org.springblade.modules.mydata.manage.service.IEnvVarService;
import org.springblade.modules.mydata.manage.vo.EnvVarVO;
import org.springblade.modules.mydata.manage.wrapper.EnvVarWrapper;
import org.springblade.modules.system.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 环境变量 控制器
 *
 * @author LIEN
 * @since 2023/11/1
 */
@RestController
@AllArgsConstructor
@RequestMapping(MdConstant.API_PREFIX_MANAGE + "/env_var")
@Api(value = "环境变量", tags = "环境变量接口")
public class EnvVarController extends BladeController {

    private final IEnvVarService envVarService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入envVar")
    public R<EnvVarVO> detail(EnvVar envVar) {
        EnvVar detail = envVarService.getOne(Condition.getQueryWrapper(envVar));
        return R.data(EnvVarWrapper.build().entityVO(detail));
    }

    /**
     * 分页 环境变量
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入env")
    public R<IPage<EnvVarVO>> list(EnvVar envVar, Query query) {
        LambdaQueryWrapper<EnvVar> queryWrapper = Wrappers.lambdaQuery();
        if (envVar != null) {
            queryWrapper.like(ObjectUtil.isNotNull(envVar.getVarName()), EnvVar::getVarName, envVar.getVarName());
            queryWrapper.eq(ObjectUtil.isNotNull(envVar.getEnvId()), EnvVar::getEnvId, envVar.getEnvId());
        }
        IPage<EnvVar> pages = envVarService.page(Condition.getPage(query), queryWrapper);
        return R.data(EnvVarWrapper.build().pageVO(pages));
    }

    /**
     * 自定义分页 环境变量
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入envVar")
    public R<IPage<EnvVarVO>> page(EnvVarVO envVar, Query query) {
        IPage<EnvVarVO> pages = envVarService.selectEnvVarPage(Condition.getPage(query), envVar);
        return R.data(pages);
    }

    /**
     * 新增 环境变量
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入envVar")
    public R save(@Valid @RequestBody EnvVar envVar) {
        boolean result = envVarService.save(envVar);
        return R.status(result);
    }

    /**
     * 修改 环境变量
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入envVar")
    public R update(@Valid @RequestBody EnvVar envVar) {
        boolean result = envVarService.updateById(envVar);
        return R.status(result);
    }

    /**
     * 新增或修改 环境变量
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入envVar")
    public R submit(@Valid @RequestBody EnvVarDTO envVarDTO) {
        boolean result = envVarService.submit(envVarDTO);
        if (result) {
            EnvVarCache.clearEnvVar(envVarDTO.getEnvId(), envVarDTO.getVarName());
        }
        return R.status(result);
    }

    /**
     * 删除 环境变量
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @RequestParam(required = false) Long envId, @RequestParam(required = false) String varName) {
        boolean result = envVarService.deleteEnvVar(Func.toLongList(ids));
        if (result && envId != null && varName != null) {
            EnvVarCache.clearEnvVar(envId, varName);
        }
        return R.status(result);
    }

    /**
     * 隐藏环境变量
     */
    @PostMapping("/hide")
    public R hide(@RequestParam Long id) {
        return R.status(envVarService.hideVar(id));
    }

    /**
     * 隐藏环境变量
     */
    @PostMapping("/show")
    public R hide(@RequestParam Long id, @RequestParam String password) {
        BladeUser bladeUser = AuthUtil.getUser();
        Assert.notNull(bladeUser, "操作失败：登录已失效！");

        String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);
        TokenParameter tokenParameter = new TokenParameter();
        tokenParameter.getArgs().set("tenantId", bladeUser.getTenantId())
                .set("account", bladeUser.getAccount())
                .set("password", password)
                .set("grantType", PasswordTokenGranter.GRANT_TYPE)
                .set("userType", userType);

        ITokenGranter granter = TokenGranterBuilder.getGranter(PasswordTokenGranter.GRANT_TYPE);
        UserInfo userInfo = granter.grant(tokenParameter);

        if (userInfo == null || userInfo.getUser() == null) {
            return R.fail("操作失败：身份认证无效！");
        }
        return R.status(envVarService.showVar(id));
    }
}