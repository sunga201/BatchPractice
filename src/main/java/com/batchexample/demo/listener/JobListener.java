package com.batchexample.demo.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobListener extends JobExecutionListenerSupport {
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            System.out.println("Crawling batch job completed.");
        }
        else if(jobExecution.getStatus() == BatchStatus.FAILED){
            System.out.println("Crawling batch job failed!");
        }
    }
}
