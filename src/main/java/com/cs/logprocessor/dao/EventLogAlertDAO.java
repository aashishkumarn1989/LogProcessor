package com.cs.logprocessor.dao;

import java.util.List;

import com.cs.logprocessor.bean.EventLogDTO;

public interface EventLogAlertDAO {
	
	public void saveOrUpdate(List<EventLogDTO> list);
	public int getCount();

}
