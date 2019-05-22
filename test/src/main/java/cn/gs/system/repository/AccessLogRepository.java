package cn.gs.system.repository;

import java.sql.Timestamp;
import java.util.List;

import cn.gs.base.IBaseRepository;
import cn.gs.system.model.AccessLog;

public interface AccessLogRepository extends IBaseRepository<AccessLog> {
	
    List<AccessLog> findByCreateUserIdAndCreateTimeBetween(String userId, Timestamp startTime, Timestamp endTime);
}