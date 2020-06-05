package com.go.basic.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskletProcessor implements Tasklet, StepExecutionListener {

    private List<String> list = new ArrayList<>();

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){
        for(int i=0;i<list.size();i++){
            list.set(i,"I like "+list.get(i)+"!");
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        list = (List)executionContext.get("FRUIT");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("FRUIT",list);
        return ExitStatus.COMPLETED;
    }

}
