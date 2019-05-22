package cn.gs.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Configure {

    /**
	 * 应用版本
	 */
	private String version = AppConstants.PRODUCT_VERSION;
	

//	@Value("${oms.MQServer.url:}")
//	private String MQServerUrl;
	
	
}
