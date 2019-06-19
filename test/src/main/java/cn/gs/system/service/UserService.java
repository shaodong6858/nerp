package cn.gs.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gs.base.AbstractBaseService;
import cn.gs.base.IBaseMapper;
import cn.gs.system.model.User;
import cn.gs.system.repository.UserRepository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 日志相关服务
 * @author xiapengtao
 * @date 2018年4月10日
 */

@Service
public class UserService extends AbstractBaseService<User> {
	
	@Autowired 
	UserRepository userRepository;
	
	@Override
	public IBaseMapper<User> getRepository() {
		return userRepository;
	}
	
    public User getById (String id) {
    	return get(id);
    }
    
    public PageInfo getByEntity (User user) {
    	return getListAll(1,10);
    }
}