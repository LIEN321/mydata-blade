package org.springblade.test.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.test.entity.CmsUser;
import org.springblade.test.entity.HrUser;
import org.springblade.test.entity.OaUser;
import org.springblade.test.service.ICmsUserService;
import org.springblade.test.service.IHrUserService;
import org.springblade.test.service.IOaUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author LIEN
 */
@RestController
@AllArgsConstructor
@RequestMapping("test")
public class TestController extends BladeController {

    private final IHrUserService hrUserService;
    private final IOaUserService oaUserService;
    private final ICmsUserService cmsUserService;

    @GetMapping("/hr_user")
    public R<List<HrUser>> listHrUser(@RequestHeader HttpHeaders headers, @RequestParam(required = false) BigDecimal salary) {
        String key = headers.getFirst("key");
        if (!"123456".equals(key)) {
            return R.fail("key无效");
        }
        LambdaQueryWrapper<HrUser> queryWrapper = Wrappers.<HrUser>lambdaQuery()
                .gt(ObjectUtil.isNotNull(salary), HrUser::getSalary, salary);
        return R.data(hrUserService.list(queryWrapper));
    }

    @GetMapping("/oa_user")
    public R<List<OaUser>> listOaUser() {
        return R.data(oaUserService.list());
    }

    @GetMapping("/cms_user")
    public R<List<CmsUser>> listCmsUser() {
        return R.data(cmsUserService.list());
    }

    @PostMapping("/save_oa_user")
    public R saveOaUser(@RequestHeader HttpHeaders headers, @RequestBody List<OaUser> users) {
        String key = headers.getFirst("key");
        if (!"123456".equals(key)) {
            return R.fail("key无效");
        }

        if (CollUtil.isNotEmpty(users)) {
            users.forEach(user -> {
                LambdaQueryWrapper<OaUser> queryWrapper = Wrappers.<OaUser>lambdaQuery()
                        .eq(OaUser::getUserCode, user.getUserCode());
                OaUser check = oaUserService.getOne(queryWrapper);
                if (check == null) {
                    oaUserService.save(user);
                } else {
                    BeanUtil.copyProperties(user, check, "id");
                    oaUserService.updateById(check);
                }
            });
        }
        return R.status(true);
    }

    @PostMapping("/save_cms_user")
    public R saveCmsUser(@RequestHeader HttpHeaders headers, @RequestBody List<CmsUser> users) {
        String key = headers.getFirst("key");
        if (!"123456".equals(key)) {
            return R.fail("key无效");
        }
        
        if (CollUtil.isNotEmpty(users)) {
            users.forEach(user -> {
                LambdaQueryWrapper<CmsUser> queryWrapper = Wrappers.<CmsUser>lambdaQuery()
                        .eq(CmsUser::getUserCode, user.getUserCode());
                CmsUser check = cmsUserService.getOne(queryWrapper);
                if (check == null) {
                    cmsUserService.save(user);
                } else {
                    BeanUtil.copyProperties(user, check, "id");
                    cmsUserService.updateById(check);
                }
            });
        }
        return R.status(true);
    }
}
