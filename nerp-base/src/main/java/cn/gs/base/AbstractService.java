package cn.gs.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.gs.base.entity.IBaseEntity;

/**
 * 服务基类
  * @author wangshaodong
 * 2019年05月13日
 * @param <K>
 */
@Transactional
public abstract class AbstractService extends AbstractBase {
	
	@Autowired
	protected GenericService genericService;
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public <T> T get (Class<T> clazz, String id) {
		if (id == null) {
			return null;
		}
		return genericService.get(clazz, id);
	}
	
	/**
	 * 创建实体
	 * @param entity
	 * @return
	 */
    public <T> T create(T entity) {
    	return genericService.create(entity);
    }
    
    /**
     * 批量创建实体
     * @param entity
     * @return
     */
    public <T extends IBaseEntity> List<T> createAll(List<T> entities) {
    	return genericService.createAll(entities);
    }
    
    /**
     * 更新实体
     * @param entity
     * @return
     */
    public <T> T update(T entity) {
    	return genericService.update(entity);
    }
    
    public <T> T saveOrUpdate(T entity) {
    	return genericService.saveOrUpdate(entity);
	}
    
    /**
     * 删除实体
     * @param entity
     * @return
     */
    public <K> void delete(K entity) {
    	genericService.delete(entity);
    }
    
    /**
	  * 执行 qlString
	  */
	 public void executeHql(String hqlString, String name, Object value) {
		 genericService.executeQuery(hqlString, name, value);
	 }
	 
	 /**
	  * 执行 qlString
	  */
	 @SuppressWarnings("rawtypes")
	public List query(String hqlString, Map<String, ? extends Object> params) {
		 return genericService.query(hqlString, params);
	 }
	 
	/**
     * 查询
     * @return
     */
    public <T extends IBaseEntity> List<T> findAll(Class<T> entityClass) {
    	return genericService.findAll(entityClass);
    }
    
}
