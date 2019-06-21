package cn.gs.system.repository;

import org.apache.ibatis.annotations.Select;

import cn.gs.base.IBaseMapper;
import cn.gs.system.model.OrgUser;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface OrgUserMapper extends IBaseMapper<OrgUser> {
	
	@Select("select * from org_user where user_empnumber = #{userEmpnumber } ")
	OrgUser getByUserEmpnumber(String userEmpnumber);
}