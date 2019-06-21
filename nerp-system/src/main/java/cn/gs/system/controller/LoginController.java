package cn.gs.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.gs.base.AbstractBaseController;
import cn.gs.base.AbstractBaseService;
import cn.gs.base.JsonResult;
import cn.gs.system.model.OrgUsraccount;
import cn.gs.system.service.LoginService;
import freemarker.template.utility.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;

/**
 * 日志管理
 * @author wangshaodong
 * @date 2018年2月6日
 */
@Log4j2
@Api(tags={"系统 - 日志管理"})
@RestController
@RequestMapping("system/user")
public class LoginController  extends AbstractBaseController<OrgUsraccount>{
	
	@Autowired
	LoginService loginService;
	
	/**
	 * 根据获取用户信息
	 * @param id
	 * @return 实体详情
	 */
	@GetMapping("info")
	public ResponseEntity<JsonResult> index(String empnumber) {
		if (!StringUtils.isEmpty(empnumber)) {
			 return success(loginService.getUserInfo(empnumber));
		}
		AttributePrincipal userPrincipal = (AttributePrincipal) httpRequest.getUserPrincipal();
        if (userPrincipal != null) {
            String userName = userPrincipal.getName();
            log.info("userName============"+userName);
            return success(loginService.getUserInfo(userName));
        }
		return fail("未登录！");
	}

	@Override
	public AbstractBaseService<OrgUsraccount> getService() {
		// TODO Auto-generated method stub
		return loginService;
	}
}
