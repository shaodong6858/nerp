package cn.gs.base;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Id;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gs.base.entity.IBaseEntity;
import cn.gs.base.util.BeanRefUtil;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Condition;

/**
 * 服务基类
 * @author 王少东
 * @date 2019年06月17日 
 * @param <T>
 */
@Transactional
public abstract class AbstractBaseService<T extends IBaseEntity> extends AbstractBase {
	
	public abstract IBaseMapper<T> getRepository();
    
	//返回泛型类的class，在做直接查询实体的时候用到
	public Class<T> getEntityClass(){
		@SuppressWarnings("unchecked")
		Class < T >  entityClass  =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}
	
	public int create(T entity) {
		int count = getRepository().insertSelective(entity);
		return count;
	}
	
	/**
     * 更新实体
     * @param entity
     * @return
     */
    public int update(T entity) {
    	return getRepository().updateByPrimaryKey(entity);
    }
    
    /**
     * 删除实体
     * @param entity
     * @return
     */
    public int delete(T entity) {
    	return getRepository().delete(entity);
    }
    /**
     * @param id 可以是多个id 用“,”隔开
     * @return 返回删除个数
     */
    public int delete(String id) {
    	return getRepository().deleteByIds(id);
    }
	
	/**
	 * @param 查询所有数据
	 * @return 返回实体
	 */
	public PageInfo<T> getListAll(int pageNum,int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
		 List<T>  list = getRepository().selectAll();
    	 PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	public  List<T> getListAll() {
		 List<T>  list = getRepository().selectAll();
		return list;
	}
	
	/**
	 * @param T 根据传入的实体查询
	 * @return 返回实体
	 */
	public List<T> getListAll(T entity) {
		List<T>  list = getRepository().select(entity);
		return list;
	}
	public PageInfo<T> getListAll(T entity,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<T>  list = getRepository().select(entity);
		 PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	/**
	 * @param id Sring类型的主键
	 * @return 返回实体
	 */
	public T get(String id) {
		T entity;
		try {
			entity = setEntityId(getEntityClass(), id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		T  recordc = getRepository().selectOne(entity);
		return recordc;
	}
	/**
	 * @param id Int类型的主键
	 * @return 返回实体
	 */
	public  T get(int id) {
		T entity;
		try {
			entity = setEntityId(getEntityClass(), id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		T  recordc =  getRepository().selectOne(entity);
		return recordc;
	}
	private <T extends IBaseEntity> T setEntityId(Class<T> entityClass,String id) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T entity = entityClass.newInstance();
		for(Field field : entityClass.getDeclaredFields()) {
			Id idField = field.getAnnotation(Id.class);
			field.setAccessible(true);
			if (idField != null) {
				Method method = entityClass.getMethod(BeanRefUtil.parSetName(field.getName()),String.class);
				method.invoke(entity, id);
			}
		}
		return entity;
	}
	private <T extends IBaseEntity> T setEntityId(Class<T> entityClass,int id) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T entity = entityClass.newInstance();
		for(Field field : entityClass.getDeclaredFields()) {
			Id idField = field.getAnnotation(Id.class);
			field.setAccessible(true);
			if (idField != null) {
				Method method = entityClass.getMethod(BeanRefUtil.parSetName(field.getName()),Integer.class);
				method.invoke(entity, id);
			}
		}
		return entity;
	}
	
}
