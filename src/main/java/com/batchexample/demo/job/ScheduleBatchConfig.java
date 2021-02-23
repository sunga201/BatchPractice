package com.batchexample.demo.job;

import com.batchexample.demo.listener.JobListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ScheduleBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job CrawlingJob1(){
        System.out.println("Crawling job 1 start.");
        return jobBuilderFactory.get("crawlingJob1")
                .incrementer(new RunIdIncrementer())
                .listener(crawlingListener())
                .start(CrawlingStep1())
                .build();
    }

    @Bean
    public Job CrawlingJob2(){
        System.out.println("Crawling job 2 start.");
        return jobBuilderFactory.get("crawlingJob2")
                .incrementer(new RunIdIncrementer())
                .listener(crawlingListener())
                .start(CrawlingStep2())
                .build();
    }

    @Bean
    public Job CrawlingJob3(){
        System.out.println("Crawling job 3 start.");
        return jobBuilderFactory.get("crawlingJob3")
                .incrementer(new RunIdIncrementer())
                .listener(crawlingListener())
                .start(CrawlingStep3())
                .build();
    }

    @Bean
    public Step CrawlingStep1(){
        return stepBuilderFactory.get("crawlingStep1")
                .chunk(10)
                .reader()
                .processor()
                .writer()
                .build();
    }

    @Bean
    public Step CrawlingStep2(){
        return stepBuilderFactory.get("crawlingStep2")
                .chunk(10)
                .reader()
                .processor()
                .writer()
                .build();
    }

    @Bean
    public Step CrawlingStep3(){
        return stepBuilderFactory.get("crawlingStep3")
                .chunk(10)
                .reader()
                .processor()
                .writer()
                .build();
    }

    @Bean
    public JobExecutionListener crawlingListener(){
        return new JobListener();
    }
}
