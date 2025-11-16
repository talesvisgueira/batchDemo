package br.jus.trepi.demobatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@Configurable
public class BatchConfig {

    @Bean
    public Step getStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step",jobRepository)
                .tasklet( (StepContribution contribution, ChunkContext context) -> {
                    IO.println("====  BEM VINDO AO TREINAMENTO SPRING BATCH ====");
                    return RepeatStatus.FINISHED;
                },transactionManager)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job",jobRepository)
                .start(getStep(jobRepository,transactionManager))
                .build();
    }


}
