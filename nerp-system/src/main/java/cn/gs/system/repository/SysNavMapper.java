package cn.gs.system.repository;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.mapstruct.Mapper;

import cn.gs.base.IBaseMapper;
import cn.gs.system.model.SysNav;

@Mapper
public interface SysNavMapper extends IBaseMapper<SysNav> {
	
	@Select("select * from sys_nav where nav_deptcode=#{deptcode}")
	@Results({
		@Result(id=true,column="nav_id",property="navId"),
		@Result(column="nav_name",property="navName"),
		@Result(column="nav_cat",property="navCat"),
		@Result(column="nav_subcat",property="navSubcat"),
		@Result(column="fkapp_id",property="app",one=@One(select="cn.gs.system.repository.SysAppMapper.getAppById",fetchType= FetchType.EAGER))
	})
	public List<SysNav> fillByDeptcode(String deptcode);
}