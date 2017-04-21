package me.aboullaite.batch;

import me.aboullaite.batch.steps.Step1;
import me.aboullaite.batch.steps.Step2;
import me.aboullaite.domain.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by aboullaite on 2017-04-21.
 */
@Configuration
@EnableBatchProcessing
public class Batch {
    @Bean
    Job job(JobBuilderFactory jbf,
            StepBuilderFactory sbf,
            Step1 step1,
            Step2 step2) throws Exception {

        Step s1 = sbf.get("file-db")
                .<Student, Student>chunk(100)
                .reader(step1.fileReader(null))
                .writer(step1.jdbcWriter(null))
                .build();

        Step s2 = sbf.get("db-file")
                .<Map<Integer, Integer>, Map<Integer, Integer>>chunk(100)
                .reader(step2.jdbcReader(null))
                .writer(step2.fileWriter(null))
                .build();

        return jbf.get("etl")
                .incrementer(new RunIdIncrementer())
                .start(s1)
                .next(s2)
                .build();

    }
}
