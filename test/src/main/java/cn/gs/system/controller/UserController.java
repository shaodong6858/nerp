package cn.gs.system.controller;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.gs.base.AbstractBaseController;
import cn.gs.base.AbstractBaseService;
import cn.gs.base.JsonResult;
import cn.gs.system.model.User;
import cn.gs.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 日志管理
 * @author wangshaodong
 * @date 2018年2月6日
 */
@Api(tags={"系统 - 日志管理"})
@RestController
@RequestMapping("system/user")
public class UserController extends AbstractBaseController<User> {
	
	@Autowired
	UserService accessLogService;

	@Override
	public AbstractBaseService<User> getService() {
		return accessLogService;
	}
}
