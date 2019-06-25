package cn.gs.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.gs.base.AbstractBaseController;
import cn.gs.base.AbstractBaseService;
import cn.gs.base.JsonResult;
import cn.gs.system.model.OrgUnit;
import cn.gs.system.model.OrgUsraccount;
import cn.gs.system.service.LoginService;
import cn.gs.system.service.OrgUnitService;
import freemarker.template.utility.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;

/**
 * 日志管理
 * @author wangshaodong
 * @date 2018年2月6日
 */
@Log4j2
@Api(tags={"系统 - 部门管理"})
@RestController
@RequestMapping("system/unit")
public class OrgUnitController  extends AbstractBaseController<OrgUnit>{
	
	@Autowired
	OrgUnitService orgUnitService;
	
	@Override
	public AbstractBaseService<OrgUnit> getService() {
		// TODO Auto-generated method stub
		return orgUnitService;
	}
	
	@GetMapping("all")
	public ResponseEntity<JsonResult> findListAll(OrgUnit unit){
		return success(orgUnitService.getListAll(unit));
	}
	@GetMapping("tree/{id}")
	public ResponseEntity<JsonResult> findListByTreeId(@PathVariable String id){
		OrgUnit unit = new OrgUnit();
		unit.setUnitParentcode(id);
		return success(orgUnitService.getListAll(unit));
	}
}
