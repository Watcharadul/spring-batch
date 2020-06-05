package com.go.basic.config;

import com.go.basic.chunk.ChunkProcessor;
import com.go.basic.chunk.ChunkReader;
import com.go.basic.chunk.ChunkWriter;
import com.go.basic.tasklet.TaskletProcessor;
import com.go.basic.tasklet.TaskletReader;
import com.go.basic.tasklet.TaskletWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.batch.item.ItemReader;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory){
        this.jobBuilderFactory  = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {

    }

    @Bean
    public Step taskletReader(){
        return stepBuilderFactory.get("MyTaskletReader")
                                 .tasklet(new TaskletReader())
                                 .build();
    }

    @Bean
    public Step taskletProcessor(){
        return stepBuilderFactory.get("MyTaskletProcessor")
                .tasklet(new TaskletProcessor())
                .build();
    }

    @Bean
    public Step taskletWriter(){
        return stepBuilderFactory.get("MyTaskletWriter")
                .tasklet(new TaskletWriter())
                .build();
    }

    @Bean
    public ItemReader<String> chunkReader(){
        return new ChunkReader();
    }

    @Bean
    public ItemProcessor<String, String> chunkProcessor(){
        return new ChunkProcessor();
    }

    @Bean
    public ItemWriter<String> chunkWriter(){
        return new ChunkWriter();
    }

    @Bean
    public Step chunkStep(ItemReader<String> reader,ItemProcessor<String, String> processor,ItemWriter<String> writer){
        return stepBuilderFactory.get("MyStep")
                                 .<String,String> chunk(2)
                                 .reader(reader)
                                 .processor(processor)
                                 .writer(writer)
                                 .build();
    }

    @Bean
    public Job jobController(Step chunkStep,Step taskletReader,Step taskletProcessor,Step taskletWriter){
        return jobBuilderFactory.get("MyJob")
                                .start(chunkStep)
                                .next(taskletReader)
                                .next(taskletProcessor)
                                .next(taskletWriter)
                                .build();
    }

}