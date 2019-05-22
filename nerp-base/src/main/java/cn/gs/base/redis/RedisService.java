package cn.gs.base.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.gs.base.AbstractBase;

@Service
public class RedisService extends AbstractBase {

	@Autowired
	private StringRedisTemplate template;
	
    
	public void test () {
		ValueOperations<String, String> ops = this.template.opsForValue();
		String key = "spring.boot.redis.test";
		if (!this.template.hasKey(key)) {
			ops.set(key, "foo");
		}
		System.out.println("Found key " + key + ", value=" + ops.get(key));
	}
	
	/**
	 * 根据key获取value
	 * @param key
	 */
	public String get (String key) {
		String value = template.opsForValue().get(key);
		return value;
	}
	
	/**
	 * 设置key对应的value
	 * @param key
	 */
	public void set (String key, String value) {
		template.opsForValue().set(key, value);
	}
	
	/**
	 * 设置key对应的value
	 * @param key
	 */
	public void set (String key, String value, long timeout, TimeUnit unit) {
		template.opsForValue().set(key, value, timeout, unit);
	}
	
	/**
	 * 设置key对应的value
	 * @param key
	 */
	public void setMap (String key, Map<String, String> value, long timeout, TimeUnit unit) {
		BoundHashOperations<String, Object, Object> operations = template.boundHashOps(key);
		operations.expire(timeout, unit);
		operations.putAll(value);
	}
	
	/**
	 * 设置key对应的value
	 * @param key
	 */
	public void setSet (String key, String[] values) {
		template.delete(key);
		if (values != null && values.length != 0) {
			template.boundSetOps(key).add(values);
		}
	}
	
	
	
}
