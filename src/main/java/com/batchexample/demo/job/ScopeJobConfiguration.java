package com.batchexample.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ScopeJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job scopeJob(){
        return jobBuilderFactory.get("scopeJob")
                .start(scopeStep1(null))
                .next(scopeStep2())
                .build();
    }

    @Bean
    @JobScope
    public Step scopeStep1(@Value("#{jobParameters[requestDate]}") String requestDate){
        return stepBuilderFactory.get("scopeStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> this is scopeStep1");
                    log.info(">>>>>> requestDate : {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step scopeStep2(){
        return stepBuilderFactory.get("scopeStep2")
                .tasklet(scopeStep2Tasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet scopeStep2Tasklet(@Value("#{jobParameters[requestDate]}") String requestDate){
        return (contribution, chunkContext) -> {
            log.info(">>>>>> this is scopeStep2");
            log.info(">>>>>> requestDate : {}", requestDate);

            /*String crawlingURL = "http://korea.djship.co.kr/dj/";
            Document document = Jsoup.connect(crawlingURL).get();
            log.info("{}", document.toString());
            log.info("-----------------------");
            log.info("{}", document.title());
            log.info("-----------------------");
            log.info("{}", document.body());*/

            String url = "http://korea.djship.co.kr/dj/servlet/kr.eservice.action.sub3_1_Action";
            Document doc = null;
            String text = "";
            String text2 = "";
            System.out.println("실행");
            try {
                doc = Jsoup.connect(url)
                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Origin", "http://korea.djship.co.kr")
                        .header("Referer", "http://korea.djship.co.kr/dj/ui/kr/eservice/sub3_1_1.jsp")

                        .data("mode", "R")
                        .data("SEL_YEAR", "2021")
                        .data("SEL_MONTH", "02")
                        .data("pol_cd", "KBS")
                        .data("pod_cd", "JTK")
                        .data("searchGbn","1")
                        .data("cargoGbn","C")
                        .get();
                System.out.println("doc : " + doc);
            }catch(IOException e) {
                e.printStackTrace();
            }

            Elements elements = doc.select("a[href]");

            for(Element element : elements) {
                System.out.println(element.toString().substring(9,108));
                String[] split_data = element.toString().substring(9,108).split("'");
                for(int i=0; i < split_data.length; i++) {
                    System.out.println( i + "====== " + split_data[i]);
                }
            }
            return RepeatStatus.FINISHED;
        };
    }
}
