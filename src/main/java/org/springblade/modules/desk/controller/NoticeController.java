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
package org.springblade.modules.desk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.desk.entity.Notice;
import org.springblade.modules.desk.service.INoticeService;
import org.springblade.modules.desk.vo.NoticeVO;
import org.springblade.modules.desk.wrapper.NoticeWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@PreAuth(RoleConstant.HAS_CRYPTO)
@RestController
@RequestMapping("/blade-desk/notice")
@AllArgsConstructor
@ApiSort(2)
@Api(value = "用户博客", tags = "博客接口")
public class NoticeController extends BladeController implements CacheNames {

	private INoticeService noticeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入notice")
	public R<IPage<NoticeVO>> list(@ApiIgnore @RequestParam Map<String, Object> notice, Query query) {
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return R.data(NoticeWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入notice")
	public R save(@RequestBody Notice notice) {
		return R.status(noticeService.save(notice));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入notice")
	public R update(@RequestBody Notice notice) {
		return R.status(noticeService.updateById(notice));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入notice")
	public R submit(@RequestBody Notice notice) {
		return R.status(noticeService.saveOrUpdate(notice));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

}
