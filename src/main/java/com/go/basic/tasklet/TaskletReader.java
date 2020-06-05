package com.go.basic.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class TaskletReader implements Tasklet, StepExecutionListener {

    private final String[] FRUIT = {"Apples","Bananas","Grapes","Oranges","Mangos"};
    private List<String> list = new ArrayList<>();

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){
        int index = 0;
        for(String str : FRUIT){
            log.info("Reader : "+(index++));
            list.add(str);
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("FRUIT",list);
        return ExitStatus.COMPLETED;
    }
}
