package com.cs.logprocessor.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cs.logprocessor.bean.EventLogDTO;
import com.cs.logprocessor.config.BatchConfig;
import com.cs.logprocessor.constants.IApplicationConstants;
import com.cs.logprocessor.dao.EventLogAlertDAO;
import com.google.common.collect.Lists;

@Repository
public class EventLogAlertDaoImpl implements EventLogAlertDAO {

	Logger logger = LoggerFactory.getLogger(EventLogAlertDaoImpl.class);
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void saveOrUpdate(List<EventLogDTO> list) {
	logger.info("EventLogAlertDaoImpl : Start saveOrUpdate");
		final int batchSize = IApplicationConstants.DEFAULT_BATCH_SIZE;
	    List<List<EventLogDTO>> batchLists = Lists.partition(list, batchSize);

	    for(List<EventLogDTO> batch : batchLists) {  
	    	jdbcTemplate.batchUpdate(IApplicationConstants.EVENT_LOG_ALERT_INSERT_SQL, new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i)
	                    throws SQLException {
	            	EventLogDTO eventLog = batch.get(i);
	                ps.setString(1, eventLog.getId());
	                ps.setString(2, eventLog.getEventType());
	                ps.setString(3, eventLog.getEventHost());
	                ps.setString(4, eventLog.getEventAlert());
	                ps.setLong(5, eventLog.getEventDuration());
	            }

	            @Override
	            public int getBatchSize() {
	                return batch.size();
	            }
	        });
	    }
	    logger.info("EventLogAlertDaoImpl : End saveOrUpdate");
	}


	@Override
	public int getCount() {
		logger.info("EventLogAlertDaoImpl : Start getCount");
		int numberOfRows = jdbcTemplate.queryForObject(IApplicationConstants.EVENt_LOG_ALERT_COUNT_SQL, Integer.class);
		logger.info("EventLogAlertDaoImpl : End getCount");
		return numberOfRows;
	}
}
