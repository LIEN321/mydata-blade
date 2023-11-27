/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.*;
import org.springblade.modules.auth.enums.BladeUserEnum;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.entity.UserInfo;
import org.springblade.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private IUserService userService;
	private RedisUtil redisUtil;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		HttpServletRequest request = WebUtil.getRequest();

		String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		// 获取验证码
		String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
		// 判断验证码
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
		}

		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(BladeUserEnum.WEB.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else if (userType.equals(BladeUserEnum.APP.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			}
		}
		return userInfo;
	}

}
