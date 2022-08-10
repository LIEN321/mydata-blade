package org.springblade.test.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.test.entity.HrUser;
import org.springblade.test.mapper.HrUserMapper;
import org.springblade.test.service.IHrUserService;
import org.springframework.stereotype.Service;

/**
 * 应用接口 服务实现类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@Service
public class HrUserServiceImpl extends BaseServiceImpl<HrUserMapper, HrUser> implements IHrUserService {

}
