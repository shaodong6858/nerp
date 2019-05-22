package cn.gs.system.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gs.base.AbstractController;
import cn.gs.base.JsonResult;
import cn.gs.system.model.AccessLog;
import cn.gs.system.service.AccessLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 日志管理
 * @author wangshaodong
 * @date 2018年2月6日
 */
@Api(tags={"系统 - 日志管理"})
@RestController
@RequestMapping("system/access-log")
public class AccessLogController extends AbstractController {
	
	@Autowired
	AccessLogService accessLogService;
	
	/**
	 * 查询列表
	 * @param name
	 * @return
	 */
	@ApiOperation("查询日志")
	@GetMapping
	public ResponseEntity<JsonResult> search(String createUserId, Date startDate, Date endDate,
											@RequestParam(defaultValue = "0")int page,
										    @RequestParam(defaultValue = "20")int size) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<AccessLog> pageData = accessLogService.search(createUserId, startDate, endDate, pageRequest);
		
		return success(pageData);
	}
	
}
