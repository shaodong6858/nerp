package cn.gs.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gs.system.model.AccessLog;

/**
 * 异步处理访问日志
  * @author wangshaodong
 * 2019年05月13日
 */
@Component
public class AsyncAccessLogHandler  extends AbstractBase  implements InitializingBean, DisposableBean {
	protected boolean started = false;
	private BlockingQueue<AccessLog> blockingQueue = new ArrayBlockingQueue<>(1024);
	
	@Autowired
	GenericService genericService;
	
	Worker worker = new Worker();
	
	public boolean isStarted() {
        return started;
    }
	
	/**
	 * 将访问日志添加到队列
	 * @return
	 */
	public void append(AccessLog al) {
		blockingQueue.offer(al);
    }
	
	/**
	 * 启动线程处理
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		worker.start();
	}
	
	/**
	 * 停止线程处理
	 */
	@Override
    public void destroy() throws Exception {
		started = false;
		worker.interrupt();
    }
	
	class Worker extends Thread {

        public void run() {
        	started = true;
    		while (isStarted()) {
                try {
                	AccessLog al = blockingQueue.take();
                	genericService.createWithoutSetCreateUserId(al);
                } catch (InterruptedException ie) {
                    break;
                }
            }
        }
	  }
	
}
