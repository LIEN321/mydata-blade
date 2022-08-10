package org.springblade.test.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.test.entity.OaUser;
import org.springblade.test.mapper.OaUserMapper;
import org.springblade.test.service.IOaUserService;
import org.springframework.stereotype.Service;

/**
 * 应用接口 服务实现类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@Service
public class OaUserServiceImpl extends BaseServiceImpl<OaUserMapper, OaUser> implements IOaUserService {

}
