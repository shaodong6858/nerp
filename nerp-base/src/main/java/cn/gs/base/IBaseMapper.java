package cn.gs.base;

import cn.gs.base.entity.IBaseEntity;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * The Generic JPA DAO
 * @author wangshaodong
 * @date 2019年6月13日 
 * @param <T>
 */
public interface  IBaseMapper <T extends IBaseEntity> extends Mapper<T>,IdsMapper<T> {
}
