package com.cs.logprocessor.writer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.logprocessor.bean.EventLogDTO;
import com.cs.logprocessor.dao.EventLogAlertDAO;

public class JobCompletionWriter implements ItemWriter<EventLogDTO> {

	Logger logger = LoggerFactory.getLogger(JobCompletionWriter.class);
	
	@Autowired
	EventLogAlertDAO eventLogAlertDao;
	
	@Override
	public void write(List<? extends EventLogDTO> items) throws Exception {
		logger.info("JobCompletionWriter : start write");
    	if(items.size()==0){
    	logger.info("JobCompletionWriter : No items in the DB");
    	logger.info("JobCompletionWriter : End write");	
    		return;
    	}
    	logger.info("*************Writing data in DB*******************");
    	eventLogAlertDao.saveOrUpdate(new ArrayList<>(items));
    	logger.info("****Inserted : "+items.size()+"*******************");
    	logger.info("JobCompletionWriter : End write");
	}
	

}
