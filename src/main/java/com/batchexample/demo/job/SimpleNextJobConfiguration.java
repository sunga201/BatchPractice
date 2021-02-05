package com.batchexample.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleNextJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleNextJob(){
        return jobBuilderFactory.get("simpleNextJob")
                .start(simpleNextStep1())
                .next(simpleNextStep2())
                .next(simpleNextStep3())
                .build();
    }

    @Bean
    public Step simpleNextStep1(){
        return stepBuilderFactory.get("simpleNextStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>> This is step1.");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleNextStep2(){
        return stepBuilderFactory.get("simpleNextStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>> This is step1.");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleNextStep3(){
        return stepBuilderFactory.get("simpleNextStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>> This is step3.");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
