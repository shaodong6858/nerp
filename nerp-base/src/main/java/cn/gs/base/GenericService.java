package cn.gs.base;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.gs.base.entity.AbstractEntity;
import cn.gs.base.entity.AbstractEntityManualId;
import cn.gs.base.entity.GenericJpaRepository;
import cn.gs.base.entity.IBaseEntity;
import cn.gs.base.entity.IBaseEntityManualId;
import cn.gs.base.entity.IEntityDeletable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 通用服务
 * @author wangshaodong
 * 2019年05月13日
 * @param <T>
 */
@Transactional
@Service
public class GenericService extends AbstractBase {
	
	@Autowired
    GenericJpaRepository genericJpaRepository;
	
	public GenericJpaRepository getRepository() {
		return genericJpaRepository;
	}
	
	public EntityManager getEntityManager() {
		return getRepository().getEntityManager();
	}
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public <T>T get (Class<T> clazz,String id) {
		if (id == null) {
			return null;
		}
		return  getRepository().get(clazz, id);
	}
	
	/**
	 * 创建实体
	 * @param entity
	 * @return
	 */
    public <T> T create(T entity) {
    	if (!(entity instanceof IBaseEntityManualId)) {
    		((IBaseEntity)entity).setId(null);
    	}
//    	if (entity instanceof AbstractEntity) {
////    		((AbstractEntity)entity).setCreateUserId(currentUserId());
//    	} else if (entity instanceof AbstractEntityManualId) {
////    		((AbstractEntityManualId)entity).setCreateUserId(currentUserId());
//    	}
    	getRepository().save(entity);
    	return entity;
    }
    
    
    /**
     * 创建实体,不设置createUserId
     * @param entity
     * @return
     */
    public <T> T createWithoutSetCreateUserId(T entity) {
    	if (!(entity instanceof IBaseEntityManualId)) {
    		((IBaseEntity)entity).setId(null);
    	}
    	getRepository().save(entity);
    	return entity;
    }
    
    /**
     * 批量创建实体
     * @param entity
     * @return
     */
    public <T>List<T> createAll(List<T> entities) {
    	entities.stream().forEach(entity -> {
    		create(entity);
    	});
    	return entities;
    }
    
    /**
     * 批量更新实体
     * @param entity
     * @return
     */
    public <T>List<T> updateAll(List<T> entities) {
    	entities.stream().forEach(entity -> {
    		update(entity);
    	});
    	return entities;
    }
    
    /**
     * 更新实体
     * @param entity
     * @return
     */
    public <T> T update(T entity) {
    	if(entity != null) {
//    		if (entity instanceof AbstractEntityManualId) {
//    			((AbstractEntityManualId)entity).setLastModifiedUserId(currentUserId());
//    		} else if (entity instanceof AbstractEntity) {
//    			((AbstractEntity)entity).setLastModifiedUserId(currentUserId());
//    		}
    		getRepository().update(entity);
    	};
    	return entity;
    }
    
    public <T> T saveOrUpdate(T entity) {
    	if (getRepository().isExist(entity)) {
    		return update(entity);
    	} else {
    		return create(entity);
    	}
	}
    
    /**
     * 删除实体
     * @param entity
     * @return
     */
    public <T> void delete(T entity) {
    	if (entity instanceof IEntityDeletable) {
			getRepository().markAsDeleted((IEntityDeletable)entity);
    	} else {
    		getRepository().delete(entity);
    	}
    }
    
    /**
     * 删除实体
     * @param entity
     * @return
     */
    public <T>void delete(Class<T> clazz,String id) {
    	T t = get(clazz, id);
    	if (t != null) {
    		delete(t);
    	}
    }
    
    /**
     * 查询
     * @return
     */
    public <T> List<T> findAll(Class<T> entityClass) {
    	return getRepository().findAll(entityClass);
    }
    
    /**
     * 从上下文中进行缓存
     * Remove the given entity from the persistence context
     * @param entity
     */
	 public void detach (Object entity) {
		 getRepository().detach(entity);
	 }
	 
	 /**
	  * 执行 qlString
	  */
	 public void executeQuery(String qlString, String name, Object value) {
		 Query query = genericJpaRepository.getEntityManager().createQuery(qlString);
		 query.setParameter(name, value);
		 query.executeUpdate();
	 }
	 
	 /**
	  * 执行 qlString
	  */
	 @SuppressWarnings("rawtypes")
	public List query(String qlString, Map<String, ? extends Object> params) {
		 Query query = genericJpaRepository.getEntityManager().createQuery(qlString);
		 params.forEach((k, v) -> {
			 query.setParameter(k, v);
		 });
		 return query.getResultList();
	 }
    
	 /**
	  * 执行 qlString
	  */
	 @SuppressWarnings("rawtypes")
	 public List query(String qlString, Map<String, ? extends Object> params, Pageable page) {
		 Query query = genericJpaRepository.getEntityManager().createQuery(qlString);
		 params.forEach((k, v) -> {
			 query.setParameter(k, v);
		 });
		 query.setFirstResult((int)page.getOffset());
		 query.setMaxResults((int)page.getPageSize());
		 return query.getResultList();
	 }
	 
}
