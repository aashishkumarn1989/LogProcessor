package com.aashish.springbootbatchlogprocessing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TestPropertySource(locations="classpath:application.properties")
public class SpringBootBatchLogprocessingApplicationTests {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job partitioningJob;
	
	@MockBean
    private ApplicationArguments applicationArguments;
	
	@Test
	public void main() throws Exception {
		Mockito.when(applicationArguments.getSourceArgs()).thenReturn(new String[]{});
		JobExecution jobExecution = jobLauncher.run(this.partitioningJob,
				new JobParametersBuilder().toJobParameters());
		Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
	
	
}



