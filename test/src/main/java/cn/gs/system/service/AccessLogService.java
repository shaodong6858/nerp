package cn.gs.system.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.gs.base.AbstractBaseService;
import cn.gs.base.IBaseRepository;
import cn.gs.system.model.AccessLog;
import cn.gs.system.repository.AccessLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志相关服务
 * @author xiapengtao
 * @date 2018年4月10日
 */
@Slf4j
@Service
public class AccessLogService extends AbstractBaseService<AccessLog> {
	
	@Autowired
	AccessLogRepository accessLogRepository;
	
	@Override
	public IBaseRepository<AccessLog> getRepository() {
		return accessLogRepository;
	}

	/**
	 * 搜索
	 */
	public Page<AccessLog>  search(String createUserId, Date startDate, Date endDate, Pageable page) {
		return accessLogRepository.findAll(((root, query, cb) -> {
			 List<Predicate> predicates = new ArrayList<>();
             if(!StringUtils.isEmpty(createUserId)){
            	 predicates.add(cb.equal(root.get("createUserId"), createUserId));
             }
             if(startDate != null){
            	 predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), startDate));
             }
             if(endDate != null){
            	 predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), plusOneDay(endDate)));
             }
             return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			})
		,page);
	}
	
	
	// 查询用户一段日期内的日志
	public List<AccessLog>  findByCreateUserIdAndCreateTimeBetween(String userId, Timestamp startTime, Timestamp endTime) {
		return accessLogRepository.findByCreateUserIdAndCreateTimeBetween(userId, startTime, endTime);
	}
	
    
}
