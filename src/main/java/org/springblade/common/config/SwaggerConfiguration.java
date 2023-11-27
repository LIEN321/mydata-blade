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
package org.springblade.common.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.swagger.EnableSwagger;
import org.springblade.core.swagger.SwaggerProperties;
import org.springblade.core.swagger.SwaggerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@EnableSwagger
@AllArgsConstructor
public class SwaggerConfiguration {

	/**
	 * 引入swagger配置类
	 */
	private final SwaggerProperties swaggerProperties;

	/**
	 * 引入Knife4j扩展类
	 */
	private final OpenApiExtensionResolver openApiExtensionResolver;

	@Bean
	public Docket deskDocket() {
		return docket("工作台模块", Collections.singletonList(AppConstant.BASE_PACKAGES + ".modules.desk"));
	}

	@Bean
	public Docket authDocket() {
		return docket("授权模块", Collections.singletonList(AppConstant.BASE_PACKAGES + ".modules.auth"));
	}

	@Bean
	public Docket sysDocket() {
		return docket("系统模块",
			Arrays.asList(AppConstant.BASE_PACKAGES + ".modules.system", AppConstant.BASE_PACKAGES + ".modules.resource"));
	}

	private Docket docket(String groupName, List<String> basePackages) {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName(groupName)
			.apiInfo(apiInfo())
			.ignoredParameterTypes(BladeUser.class)
			.select()
			.apis(SwaggerUtil.basePackages(basePackages))
			.paths(PathSelectors.any())
			.build().securityContexts(securityContexts()).securitySchemes(securitySchemas())
			.extensions(openApiExtensionResolver.buildExtensions(groupName));
	}

	private List<SecurityContext> securityContexts() {
		return Collections.singletonList(SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.regex("^.*$"))
			.build());
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverywhere");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(SwaggerUtil.clientInfo().getName(), authorizationScopes),
			new SecurityReference(SwaggerUtil.bladeAuth().getName(), authorizationScopes),
			new SecurityReference(SwaggerUtil.bladeTenant().getName(), authorizationScopes));
	}

	private List<SecurityScheme> securitySchemas() {
		return Lists.newArrayList(SwaggerUtil.clientInfo(), SwaggerUtil.bladeAuth(), SwaggerUtil.bladeTenant());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(swaggerProperties.getTitle())
			.description(swaggerProperties.getDescription())
			.license(swaggerProperties.getLicense())
			.licenseUrl(swaggerProperties.getLicenseUrl())
			.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
			.contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
			.version(swaggerProperties.getVersion())
			.build();
	}

}
