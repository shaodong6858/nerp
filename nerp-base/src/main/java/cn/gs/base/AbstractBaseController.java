package cn.gs.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import cn.gs.base.entity.IBaseEntity;

/**
 * controller 基类
 * @author wangshaodong
 * 2019年05月13日
 */
public abstract class AbstractBaseController<T extends IBaseEntity>  extends AbstractController {
	
	/**
	 * 获取服务，由子类提供
	 * @return
	 */
	public abstract AbstractBaseService<T> getService ();
	
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
	 */
	@PutMapping("{id}")
	public ResponseEntity<JsonResult> update(@PathVariable String id, T entity) {
		entity.setId(id);
		entity = getService().update(entity);
		return entityResult(entity);
	}
	
	/**
	 * 删除实体
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<JsonResult> delete(@PathVariable String id) {
		String[] ids = id.split(",");
		getService().delete(ids);
		return success();
	}
	
}
