package cn.gs.system.repository;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.mapstruct.Mapper;

import cn.gs.base.IBaseMapper;
import cn.gs.system.model.SysApp;

@Mapper
public interface SysAppMapper extends IBaseMapper<SysApp> {
	
	@Select("select * from sys_app where app_id=#{appId}")
	@Results({
		@Result(id=true,column="app_id",property="appId"),
		@Result(column="app_name",property="appName"),
		@Result(column="app_cat",property="appCat"),
		@Result(column="app_path",property="appPath")
	})
	public SysApp getAppById(String appId);
}