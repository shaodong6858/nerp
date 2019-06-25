package cn.gs.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import cn.gs.base.AbstractBaseService;
import cn.gs.base.IBaseMapper;
import cn.gs.base.JsonResult;
import cn.gs.system.model.OrgUnit;
import cn.gs.system.model.OrgUser;
import cn.gs.system.model.OrgUsraccount;
import cn.gs.system.model.SysApp;
import cn.gs.system.model.SysNav;
import cn.gs.system.repository.OrgUnitMapper;
import cn.gs.system.repository.OrgUserMapper;
import cn.gs.system.repository.OrgUsraccountMapper;
import cn.gs.system.repository.SysNavMapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class OrgUnitService extends AbstractBaseService<OrgUnit> {

	@Autowired
	OrgUnitMapper orgUnitMapper;
	
	@Override
	public IBaseMapper<OrgUnit> getRepository() {
		// TODO Auto-generated method stub
		return orgUnitMapper;
	}
	public List<OrgUnit> getAllBySearch(OrgUnit unit){
		Example  exampl = new Example(OrgUnit.class);
		if (!StringUtils.isEmpty(unit.getUnitDeptcode())) {
			exampl.createCriteria()
			.andLike("unitDeptcode", unit.getUnitDeptcode());
		}
		if (!StringUtils.isEmpty(unit.getUnitName())) {
			exampl.createCriteria()
			.andLike("unitName", unit.getUnitName());
		}
		if (!StringUtils.isEmpty(unit.getUnitParentcode())) {
			exampl.createCriteria()
			.andEqualTo("unitParentcode", unit.getUnitParentcode());
		}
		return orgUnitMapper.selectByExample(exampl);
	}
}
