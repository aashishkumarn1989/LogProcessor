package com.cs.logprocessor.processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cs.logprocessor.bean.EventLogDTO;

public class JobProcessor implements ItemProcessor<EventLogDTO, EventLogDTO> {

	Logger logger = LoggerFactory.getLogger(JobProcessor.class);
	
	private static final int MAX_DURATION = 4;
	private static Map<String,EventLogDTO> eventLogMap= new ConcurrentHashMap<>();
	
	@Override
	public EventLogDTO process(EventLogDTO eventItem) throws Exception {
		logger.info("JobProcessor : Start process");
		logger.debug(eventItem.toString());
		if(!eventLogMap.containsKey(eventItem.getId())){
			eventLogMap.put(eventItem.getId(), eventItem);
			return null;
		}	
		EventLogDTO eventInMap = eventLogMap.get(eventItem.getId());
		Long diff = Math.abs(eventItem.getTimestamp()-eventInMap.getTimestamp());
		eventItem.setEventDuration(diff);
		String alert = diff>MAX_DURATION?"true":"false";
		eventItem.setEventAlert(alert);
		eventLogMap.remove(eventItem.getId());
		logger.info("JobProcessor : End process");
		return eventItem;
	}

}
