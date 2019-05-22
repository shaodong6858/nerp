package cn.gs.base;

import static cn.gs.base.entity.IEntityDeletable.UNDELETED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import cn.gs.base.entity.IBaseEntity;
import cn.gs.base.entity.IEntityDeletable;

/**
 * 服务基类
 * @author xiapengtao
 * @date 2018年2月10日 
 * @param <T>
 */
@Transactional
public abstract class AbstractBaseService<T extends IBaseEntity> extends AbstractService {
	
	public abstract IBaseRepository<T> getRepository();
    
    /**
	 * 创建实体
	 * @param entity
	 * @return
	 */
	public T create(T entity) {
    	return super.create(entity);
    }
    
    /**
     * 更新实体
     * @param entity
     * @return
     */
    public T update(T entity) {
    	return super.update(entity);
    }
    
    /**
     * 删除实体
     * @param entity
     * @return
     */
    public void delete(T entity) {
    	super.delete(entity);
    }
	
	
	/**
	 * 获取查询未删除的实体的条件
	 */
	public List<Predicate> getPredicateUndeletedList(Root<T> root, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get("isDelete"), UNDELETED));
		return predicates;
	}
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public T get (String id) {
		if (id == null) {
			return null;
		}
		return  getRepository().findById(id).orElse(null);
	}
	
	
	public boolean existsById (String id) {
		return  getRepository().existsById(id);
	}
	
	
    /**
     * 删除
     * @param entity
     * @return
     */
    public void delete(String id) {
    	T entity = get(id);
    	if (entity != null) {
    		delete(entity);
    	}
    }
    
    /**
     * 删除
     * @return
     */
    public void delete(String... id) {
    	for (int i = 0; i < id.length; i++) {
    		delete(id[i]);
		}
    }
    
    /**
     * 删除所有
     * @return
     */
    public void deleteAll() {
    	getRepository().deleteAll();
    }
    
    /**
     * 统计数量
     * @return
     */
    public long count() {
    	return getRepository().count();
    }
    
    /**
     * 查询
     * @return
     */
    public List<T>  findAll(Sort sort) {
    	return getRepository().findAll(sort);
    }
    
    /**
     * 查询
     * @return
     */
    public List<T>  findAll() {
    	return getRepository().findAll();
    }
    
    /**
     * 查询
     * @return
     */
    public List<T>  findAllById(Iterable<String> ids) {
    	return getRepository().findAllById(ids);
    }
    
    /**
     * 查询
     * @return
     */
    public Page<T>  findAll(Pageable pageable) {
    	return getRepository().findAll(pageable);
    }
    
    
    /**
	 * 获取查询未删除的实体的条件
	 */
	public Predicate getPredicateUndeleted (Root<T> root, CriteriaBuilder cb) {
		boolean hasIsDelete = IEntityDeletable.class.isAssignableFrom(root.getJavaType());
		if (hasIsDelete) {
			return cb.equal(root.get("isDelete"), UNDELETED);
		} else {
			return null;
		}
	}
    
    /**
     * 查询
     * @return
     */
    public List<T>  findAllUndeleted () {
    	return getRepository().findAll(((root, query, cb) -> {
    		return getPredicateUndeleted(root, cb);
			}));
    }
    
    /**
     * 排序查询
     * @return
     */
    public List<T>  findAllUndeleted (Sort sort) {
    	return getRepository().findAll(((root, query, cb) -> {
    		return getPredicateUndeleted(root, cb);
			}), sort);
    }
    
    /**
     * 分页查询
     * @return
     */
    public Page<T>  findAllUndeleted (Pageable page) {
    	return getRepository().findAll(((root, query, cb) -> {
    		return getPredicateUndeleted(root, cb);
    	}), page);
    }
    
    public List<T> findByParentId(String parentId) {
    	return getRepository().findAll(((root, query, cb) -> {
		 List<Predicate> predicates = new ArrayList<>();
         predicates.add(cb.equal(root.get("parentId"), parentId));
         return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}));
	};
	
	/**
	 * 查询
	 * @param query
	 * @param params
	 * @param page
	 * @return
	 */
	public <K>List<K> getQueryResultList(String qlString, Class<K> resultClass, Map<String, Object> params, Pageable page) {
		TypedQuery<K> query = genericService.getEntityManager().createQuery(qlString, resultClass);
		query.setFirstResult((int)page.getOffset());
		query.setMaxResults((int)page.getPageSize());
		params.forEach((k, v) -> {
			query.setParameter(k, v);
		});
		return query.getResultList();
	}
    
	/**
	 * 分页查询
	 * @param query
	 * @param params
	 * @param page
	 * @return
	 */
	public <K>Page<K> getPagetList(String hql, String hqlTotal, Class<K> resultClass, Map<String, Object> params, Pageable page) {
		Query queryTotal = genericService.getEntityManager().createQuery(hqlTotal);
		params.forEach((k, v) -> {
			queryTotal.setParameter(k, v);
		});
		long total = Long.parseLong(queryTotal.getSingleResult().toString());
		
		TypedQuery<K> query = genericService.getEntityManager().createQuery(hql, resultClass);
		query.setFirstResult((int)page.getOffset());
		query.setMaxResults((int)page.getPageSize());
		params.forEach((k, v) -> {
			query.setParameter(k, v);
		});
		
		return new PageImpl<>(query.getResultList(), page, total);
	}
	
}
