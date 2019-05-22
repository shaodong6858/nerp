package cn.gs.base.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * The Generic JPA DAO
 * @author wangshaodong
 * @date 2018年5月9日 
 * @param <T>
 */
@Repository
public class GenericJpaRepository {
	
   @PersistenceContext
   EntityManager entityManager;
 
   public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	    * 根据实体类和id获取实体
	    * @param entityClass
	    * @param id
	    * @return
	    */
	   public <T> T get(Class<T> entityClass, String id){
	      return entityManager.find(entityClass, id );
	   }
	   
	   /**
	    * 查询所有实体
	    * @param entityClass
	    * @return
	    */
	   @SuppressWarnings("unchecked")
	   public <T> List<T> findAll(Class<T> entityClass){
	      return entityManager.createQuery( "from " + entityClass.getName() )
	       .getResultList();
	   }
	 
	   /**
	    * 保存实体
	    * @param entity
	    */
	   public <T> void save(T entity){
	      entityManager.persist(entity);
	   }
	 
	   /**
	    * 保存或更新更新实体
	    * @param entity
	    */
	   public <T> void saveOrUpdate(T entity){
	      entityManager.merge(entity);
	   }
	   
	   /**
	    * 更新实体
	    * @param entity
	    */
	   public <T> T update(T entity) {
		   if (isExist(entity)) {
			  return entityManager.merge(entity);
		   }
		   return null;
	   }

	   public <T> boolean existsById(Class<T> entityClass, String id) {
	     return get(entityClass, id) != null;
	   }
	   
	   public <T> boolean isExist(T entity) {
		   if (entity == null) {
			   return false;
		   }
		   String id = ((IBaseEntity)entity).getId();
		   if (StringUtils.isEmpty(id)) {
			   return false;
		   } else {
			   String query = "select count(1) from " + entity.getClass().getName() + " where id = '" + id + "'";
			   Long count = 0l;
			   try {
				   count = (Long) entityManager.createQuery( query ).getSingleResult();
			   } catch (Exception e) {
				  e.printStackTrace();
			   }
			   return ( ( count.equals( 0L ) ) ? false : true );
		   }
	   }
	 
	   /**
	    * 删除实体
	    * @param entity
	    */
	   public <T> void delete(T entity){
	      entityManager.remove(entity);
	   }
	   
	   /**
	    * 根据实体类和id获取实体
	    * @param entityClass
	    * @param id
	    * @return
	    */
	   public <T> void delete(Class<T> entityClass, String id){
	      T entity =  entityManager.find(entityClass, id );
	      if (entity != null) {
	    	  entityManager.remove(entity);
	      }
	   }
	   
	   /**
	    * 标记为删除
	    * @param entity
	    */
	   public void markAsDeleted (IEntityDeletable entity) {
		   markAsDeleted(entity.getClass(), entity.getId());
	   }
	   
	   /**
	    * 标记为删除
	    * @param entity
	    */
	   public <T> void markAsDeleted (Class<T> entityClass, String... ids) {
		   
		   String qlString = "update " + entityClass.getSimpleName() + " set deleteUserId = :userId, deleteTime = :now, isDelete = '1' where id in :id ";
		   
		   Query query = entityManager.createQuery(qlString)
		   .setParameter("userId", "userId")
		   .setParameter("now", new Timestamp(System.currentTimeMillis()))
		   .setParameter ("id", Arrays.asList(ids));
		   query.executeUpdate();
	   }
	   
	   /**
	    * 从上下文中进行缓存
	    * Remove the given entity from the persistence context
	    * @param entity
	    */
	   public void detach (Object entity) {
		   entityManager.detach(entity);
	   }
	   
}
