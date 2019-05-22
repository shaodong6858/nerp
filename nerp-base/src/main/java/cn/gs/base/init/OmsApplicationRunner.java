package cn.gs.base.init;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.gs.base.filter.AccessLogFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用启动后初始化
 * @author xiapengtao
 * @date 2018年6月19日
 */
@Slf4j
@Component
@Order(value = 1)
public class OmsApplicationRunner implements ApplicationRunner {
	
    @Override
    public void run(ApplicationArguments var1) throws Exception {
    	
    	// 初始化映射数据
        AccessLogFilter.initMappingDataMap();
        
    }
    

}
