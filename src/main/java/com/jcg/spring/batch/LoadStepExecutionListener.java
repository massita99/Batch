package com.jcg.spring.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

import java.util.Queue;

public class LoadStepExecutionListener extends StepExecutionListenerSupport {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Queue<String> files = (Queue<String>) stepExecution.getJobExecution().getExecutionContext().get("fileToDownload");
        if (files.isEmpty())
            return ExitStatus.COMPLETED;
        else {
            stepExecution.getJobExecution().getExecutionContext().put("nextFile",files.remove());
            return new ExitStatus("NEXT_FILE");
        }

    }

}
