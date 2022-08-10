package org.springblade.test.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.test.entity.CmsUser;
import org.springblade.test.mapper.CmsUserMapper;
import org.springblade.test.service.ICmsUserService;
import org.springframework.stereotype.Service;

/**
 * @author LIEN
 * @date 2022-08-08
 */
@Service
public class CmsUserServiceImpl extends BaseServiceImpl<CmsUserMapper, CmsUser> implements ICmsUserService {

}
