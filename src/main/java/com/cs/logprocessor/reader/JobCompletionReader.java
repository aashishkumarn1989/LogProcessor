package com.cs.logprocessor.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineMapper;

import com.cs.logprocessor.bean.EventLogDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JobCompletionReader implements LineMapper<EventLogDTO>{
	
	Logger logger = LoggerFactory.getLogger(JobCompletionReader.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public EventLogDTO mapLine(String line, int lineNumber) throws Exception {
		logger.info("JobCompletionReader : start mapLine");
		EventLogDTO mapperEvent = mapper.readValue(line, EventLogDTO.class);
		logger.info("JobCompletionReader : end mapLine");
		return mapperEvent;
	}

}
