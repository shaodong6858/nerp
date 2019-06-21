package cn.gs.base;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * controller 基类
 * @author 王少东
 * @date 2019年6月18日
 */
public abstract class AbstractController  extends AbstractBase {
	
	/**
	 * 请求
	 */
	@Autowired
	public HttpServletRequest httpRequest;

	
	/**
	 * 全局转换日期方法
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));        
	}
	
	protected ResponseEntity<JsonResult> success () {
		return ResponseEntity.ok(JsonResult.success());
	}
	
	protected <K>ResponseEntity<JsonResult> success (Page<K> data) {
		JsonResult result = JsonResult.success(data.getContent());
		return ResponseEntity.ok(result);
	}
	
	protected ResponseEntity<JsonResult> success (Object data) {
		return ResponseEntity.ok(JsonResult.success(data));
	}
	
	protected ResponseEntity<JsonResult> fail (int httpStatus, Object message) {
		return ResponseEntity.status(httpStatus).body(JsonResult.fail(message));
	}
	
	protected ResponseEntity<JsonResult> fail (Object message) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResult.fail(message));
	}
	
	protected <K> ResponseEntity<JsonResult> entityResult (K entity) {
		if (entity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JsonResult.notFound());
		} else {
			return ResponseEntity.ok(JsonResult.success(entity));
		}
	}
	
	protected ResponseEntity<JsonResult> entityResult (JsonResult entity) {
		if (entity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JsonResult.notFound());
		} else {
			return ResponseEntity.ok(entity);
		}
	}
	
	/**
	 * 允许跨域
	 * @param response
	 */
	protected void cors(HttpServletResponse response) {
		//*表示允许所有域名跨域
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        //允许跨域的Http方法
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
	}
	
//	protected <T> ResponseEntity<JsonResult> list (Object entity) {
//		if (entity == null) {
//			return ResponseEntity.notFound().build();
//		} else {
//			return ResponseEntity.ok(JsonResult.success(entity));
//		}
//	}
	
}
