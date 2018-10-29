package com.cs.logprocessor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.logprocessor.dao.EventLogAlertDAO;

@Component
public class JobCompletionListener implements JobExecutionListener {

	Logger logger = LoggerFactory.getLogger(JobCompletionListener.class);
	
	@Autowired
	EventLogAlertDAO eventLogAlertDao;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("**************** BATCH JOB EXECUTION BEGINS : ******");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("**************** BATCH JOB EXECUTION ENDS : ******");
	}

}
