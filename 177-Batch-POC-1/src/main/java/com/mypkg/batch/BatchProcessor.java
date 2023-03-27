package com.mypkg.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mypkg.processor.MyProcessor;
import com.mypkg.reader.MyReader;
import com.mypkg.writer.MyWriter;

@EnableBatchProcessing
@Component
public class BatchProcessor {

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Autowired
	private JobBuilder jobBuilder;

	@Autowired
	private JobExecutionListener listener;

	@Bean
	public ItemReader<String> createItemReader() {
		return new MyReader();
	}

	@Bean
	public ItemWriter<String> creaItemWriter() {
		return new MyWriter();
	}

	@Bean
	public ItemProcessor<String, String> createItemProcessor() {
		return new MyProcessor();
	}

	@Bean("step1")
	public Step createStep() {
		return stepBuilder.get("step1").<String, String>chunk(3).writer(creaItemWriter()).reader(createItemReader())
				.processor(createItemProcessor()).build();
	}

	@Bean
	public Job job(JobRepository jobRepository) {
		return new JobBuilder("myJob").repository(jobRepository).start(createStep()).build();
	}
}
