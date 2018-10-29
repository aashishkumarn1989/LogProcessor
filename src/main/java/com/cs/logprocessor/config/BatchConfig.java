package com.cs.logprocessor.config;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.cs.logprocessor.bean.EventLogDTO;
import com.cs.logprocessor.constants.IApplicationConstants;
import com.cs.logprocessor.listener.JobCompletionListener;
import com.cs.logprocessor.processor.JobProcessor;
import com.cs.logprocessor.reader.JobCompletionReader;
import com.cs.logprocessor.writer.JobCompletionWriter;

@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class BatchConfig {

    Logger logger = LoggerFactory.getLogger(BatchConfig.class);
    
    @Autowired
    private ApplicationArguments  applicationArguments;
    
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    
    @Value("${cs.logprocessor.file.location}")
    private String fileLocation;
    
    @Bean
    @Qualifier("partitioningJob")
    public Job partitioningJob() throws Exception {
    logger.info("BatchConfiguration : Start partitioningJob");	
    Job partitionJob =  jobBuilderFactory.get(IApplicationConstants.PARALLEL_JOB)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(mainParentStep())
                .end()
                .build();
    logger.info("BatchConfiguration : End partitioningJob");
    return partitionJob;
    }
    
    

    @Bean
    public Step mainParentStep() throws Exception {
    	logger.info("BatchConfiguration : Start mainParentStep");	
        Step mainParentStep = stepBuilderFactory.get(IApplicationConstants.PARENT_STEP)
                .partitioner(subChildStep())
                .partitioner("partition", partitioner())
                .gridSize(IApplicationConstants.DEFAULT_GRID_SIZE)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
        
        logger.info("BatchConfiguration : End mainParentStep");
    	return  mainParentStep; 
    }

    /*
     * Added partition to read multiple files in parallel
     * 
     * todo :
     * can split a file into parts and execute simultaneously for one file as well
     * 
     */
    @Bean
    public Partitioner partitioner() throws Exception {
    	logger.info("BatchConfiguration : Start partitioner");	
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if(applicationArguments.getSourceArgs() !=null && applicationArguments.getSourceArgs().length>0){
        	fileLocation = applicationArguments.getSourceArgs()[0].split("=")[1];
        }
        partitioner.setResources(resolver.getResources(fileLocation));
        logger.info("BatchConfiguration : End partitioner");	
        return partitioner;
    }

    @Bean
    public Step subChildStep() throws Exception {
    	logger.info("BatchConfiguration : Start subChildStep");
        Step subChildStep = stepBuilderFactory.get(IApplicationConstants.CHILD_STEP)
                .<EventLogDTO,EventLogDTO>chunk(IApplicationConstants.DEFAULT_CHUNK_SIZE)
                .reader(reader(null))
                .writer(writer())
                .processor(processor())
                .throttleLimit(IApplicationConstants.DEFAULT_THROTTLE_LIMIT)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
        logger.info("BatchConfiguration : End subChildStep");
        return subChildStep;
    }
    
    @Bean
    public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

    @Bean
    @StepScope
    public FlatFileItemReader<EventLogDTO> reader(@Value("#{stepExecutionContext['fileName']}") String file) throws MalformedURLException {
    	logger.info("BatchConfiguration : Start reader");
    	FlatFileItemReader<EventLogDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new UrlResource(file));
        LineMapper<EventLogDTO> lineMapper = new JobCompletionReader();
        reader.setLineMapper(lineMapper);
        logger.info("BatchConfiguration : Stop reader");
        return reader;
    }
    
    @Bean
    public ItemProcessor<EventLogDTO, EventLogDTO> processor() {
        return new JobProcessor();
    }

    @Bean
    public ItemWriter<EventLogDTO> writer() {
        return new JobCompletionWriter();
    }
}
