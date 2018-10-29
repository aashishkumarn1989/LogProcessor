package com.cs.logprocessor.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.logprocessor.bean.EventLogDTO;
import com.cs.logprocessor.dao.EventLogAlertDAO;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TestPropertySource(locations="classpath:application.properties")
public class EventLogAlertDaoImplTest {
	
	@Autowired
    private EventLogAlertDAO eventLogAlertDao;
	
	@MockBean
    private ApplicationArguments applicationArguments;
	
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveAndGetCount()
    {
    	Mockito.when(applicationArguments.getSourceArgs()).thenReturn(new String[]{});
        EventLogDTO event = new EventLogDTO();
        event.setId("Test");
        event.setEventDuration(new Long(5));
        event.setEventAlert("true");
        List<EventLogDTO> list = new ArrayList<>();
        list.add(event);
        eventLogAlertDao.saveOrUpdate(list);
        Assert.assertEquals(1, eventLogAlertDao.getCount());
    }

}
