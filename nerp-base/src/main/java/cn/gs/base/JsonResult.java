package cn.gs.base;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;

/**
 * json结果对象
 * 
 * @author wangshaodong 2019年5月13日
 */
public class JsonResult{
	
	private static final String SUCCESS = "success";
	
    public String getTimestamp() {
		return DateTime.now().toString();
	}

	private int status;
	private long total;
	private Object message;
	private Object data;
	
	private boolean state = true;
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getMessage() {
		return message;
	}
	
	public int getStatus() {
		return status;
	}
	
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
    	this.data = data;
    }

    private JsonResult() {
        
    }
    
    public JsonResult(Object data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = data;
    }

    public static JsonResult fail(Object message){
        JsonResult result =  new JsonResult();
        result.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        result.message = message;
        return result;
    }
    
    public static JsonResult success(Object data){
       if(data instanceof JsonResult){
           return (JsonResult)data;
       }
        JsonResult result =  new JsonResult();
        result.status = HttpStatus.OK.value();
        result.message = HttpStatus.OK.name();
        result.data = data;
        return result;
    }
    
    public static JsonResult parse(String data){
        if(SUCCESS.equals(data)){
            return success();
        }else{
            return fail(data);
        }
    }
    
    public static JsonResult success(){
        return build(HttpStatus.OK);
    }
    
    public static JsonResult unauthorized(){
    	return build(HttpStatus.UNAUTHORIZED);
    }
    
    public static JsonResult forbidden(){
    	return build(HttpStatus.FORBIDDEN);
    }
    
    public static JsonResult notFound(){
    	return build(HttpStatus.NOT_FOUND);
    }
    
    public static JsonResult build(HttpStatus httpStatus) {
    	JsonResult result =  new JsonResult();
    	result.status = httpStatus.value();
    	result.message = httpStatus.name();
    	return result;
    }
    
}
