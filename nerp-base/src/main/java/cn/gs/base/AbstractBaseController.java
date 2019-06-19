package cn.gs.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


import cn.gs.base.entity.IBaseEntity;
import cn.gs.base.util.BeanRefUtil;


/**
 * controller 基类
 * @author 王少东
 * @date 2019年6月18日
 */
public abstract class AbstractBaseController<T extends IBaseEntity>  extends AbstractController {
	
	/**
	 * 获取服务，由子类提供
	 * @return
	 */
	public abstract AbstractBaseService<T> getService ();
	
	@GetMapping
	public ResponseEntity<JsonResult> list(@RequestParam(value = "pageNum", required = false, defaultValue = "0")int pageNum, 
			   @RequestParam(value = "pageSize", required = false, defaultValue = "20")int pageSize) {
		return entityResult(getService().getListAll(pageNum, pageSize));
	}
	
	/**
	 * 根据id获取实体详情信息
	 * @param id
	 * @return 实体详情
	 */
	@GetMapping("{id}")
	public ResponseEntity<JsonResult> get(@PathVariable String id) {
		return entityResult(getService().get(id));
	}
	
	/**
	 * 创建实体
	 */
	@PostMapping
    public ResponseEntity<JsonResult> create(T entity) {
		getService().create(entity);
		return entityResult(entity);
    }
	
	/**
	 * 更新实体
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@PutMapping("{id}")
	public ResponseEntity<JsonResult> update(@PathVariable String id, T entity) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		setEntityId(entity, id);
		int cont = getService().update(entity);
		return entityResult(cont);
	}
	
	/**
	 * 删除实体
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<JsonResult> delete(@PathVariable String id) {
		getService().delete(id);
		return success();
	}
	
	private void setEntityId(T entity,String id) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for(Field field : entity.getClass().getDeclaredFields()) {
			Id idField = field.getAnnotation(Id.class);
			field.setAccessible(true);
			if (idField != null) {
				Method method = entity.getClass().getMethod(BeanRefUtil.parSetName(field.getName()),String.class);
				method.invoke(entity, id);
			}
		}
	}
	private void setEntityId(T entity,int id) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for(Field field : entity.getClass().getDeclaredFields()) {
			Id idField = field.getAnnotation(Id.class);
			field.setAccessible(true);
			if (idField != null) {
				Method method = entity.getClass().getMethod(BeanRefUtil.parSetName(field.getName()),Integer.class);
				method.invoke(entity, id);
			}
		}
	}
}
