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
package org.springblade.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogUsual;
import org.springblade.core.log.model.LogUsualVo;
import org.springblade.core.log.service.ILogUsualService;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-10-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/usual")
public class LogUsualController {

	private ILogUsualService logService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogUsual> detail(LogUsual log) {
		return R.data(logService.getOne(Condition.getQueryWrapper(log)));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogUsualVo>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {
		query.setAscs("create_time");
		query.setDescs(StringPool.EMPTY);
		IPage<LogUsual> pages = logService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogUsual.class));
		List<LogUsualVo> records = pages.getRecords().stream().map(logApi -> {
			LogUsualVo vo = BeanUtil.copy(logApi, LogUsualVo.class);
			vo.setStrId(Func.toStr(logApi.getId()));
			return vo;
		}).collect(Collectors.toList());
		IPage<LogUsualVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
		pageVo.setRecords(records);
		return R.data(pageVo);
	}

}
