package cn.gs.system.repository;

import org.mapstruct.Mapper;

import cn.gs.base.IBaseMapper;
import cn.gs.system.model.User;

@Mapper
public interface UserRepository extends IBaseMapper<User>  {
	
}
