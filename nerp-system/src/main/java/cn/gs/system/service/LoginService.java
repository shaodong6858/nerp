package cn.gs.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.gs.base.AbstractBaseService;
import cn.gs.base.IBaseMapper;
import cn.gs.system.model.OrgUnit;
import cn.gs.system.model.OrgUser;
import cn.gs.system.model.OrgUsraccount;
import cn.gs.system.model.SysApp;
import cn.gs.system.model.SysNav;
import cn.gs.system.repository.OrgUnitMapper;
import cn.gs.system.repository.OrgUserMapper;
import cn.gs.system.repository.OrgUsraccountMapper;
import cn.gs.system.repository.SysNavMapper;

@Service
public class LoginService extends AbstractBaseService<OrgUsraccount> {

	@Autowired
	OrgUsraccountMapper orgUsraccountMapper;
	
	@Autowired
	OrgUserMapper orgUserMapper;
	
	@Autowired
	OrgUnitMapper orgUnitMapper;
	
	@Autowired
	SysNavMapper sysNavMapper;
	
	@Override
	public IBaseMapper<OrgUsraccount> getRepository() {
		// TODO Auto-generated method stub
		return orgUsraccountMapper;
	}
	public OrgUsraccount getUserInfo(String empnumber){
		Map<String, Object> map = new HashMap();
		OrgUsraccount userAccount = new OrgUsraccount();
		userAccount.setUsraccEmpnumber(empnumber);
		userAccount = orgUsraccountMapper.selectOne(userAccount);
		//获取用户详细资料
		OrgUser user = new OrgUser();
		user.setUserEmpnumber(empnumber);
		user = orgUserMapper.selectOne(user);
		userAccount.setUserInfo(user);
		
		//获取用户的部门
		OrgUnit unit = new OrgUnit();
		unit.setUnitDeptcode(user.getUserDeptcode());
		unit = orgUnitMapper.selectOne(unit);
		userAccount.setUserUnit(unit);
		
		//获取权限菜单
		SysNav sysNav = new SysNav();
		sysNav.setNavDeptcode(user.getUserDeptcode());
		List<SysNav> navList = sysNavMapper.fillByDeptcode(user.getUserDeptcode());
		
		userAccount.setNavList(getNavIndex(navList));
		
		return userAccount;
	}
	
	public Map<String,List<SysApp>> getNavIndex(List<SysNav> navList){
		Map<String, Map<String, List<SysApp>>> subMap = new HashMap<>();
		navList.forEach(item -> {
			if (StringUtils.isEmpty(item.getNavSubcat()) ) {
				return;
			}
			if (subMap.containsKey(item.getNavCat())) {
				Map<String, List<SysApp>> map = subMap.get(item.getNavCat());
				if (map.containsKey(item.getNavSubcat())) {
					map.get(item.getNavSubcat()).add(item.getApp());
					return;
				} else {
					List<SysApp> list = new ArrayList<>();
					list.add(item.getApp());
					map.put(item.getNavSubcat(),list);
				}
				return;
			}
			Map<String, List<SysApp>> map = new HashMap<>();
			List<SysApp> list = new ArrayList<>();
			list.add(item.getApp());
			map.put(item.getNavSubcat(), list);
			subMap.put(item.getNavCat(), map);
		});
		
		Map<String, List<SysApp>> map = new HashMap<>();
		
		for (String key : subMap.keySet()) { 
			List<SysApp> list = new ArrayList<>();
			for (String subKey : subMap.get(key).keySet()) { 
				SysApp subNav = new SysApp();
				subNav.setAppName(subKey);
				subNav.setSubList(subMap.get(key).get(subKey));
				list.add(subNav);
			}
			map.put(key, list);
		}
		
		navList.forEach(item -> {
			if (!StringUtils.isEmpty(item.getNavSubcat()) ) {
				return;
			}
			if (map.containsKey(item.getNavCat())) {
				map.get(item.getNavCat()).add(item.getApp());
				return;
			}
			List<SysApp> list = new ArrayList<>();
			list.add(item.getApp());
			map.put(item.getNavCat(), list);
		});
		return map;
	}
}
