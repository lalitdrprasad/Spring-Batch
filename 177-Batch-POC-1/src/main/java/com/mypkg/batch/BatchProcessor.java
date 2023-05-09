package com.mypkg.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mypkg.listener.MyListener;
import com.mypkg.processor.MyProcessor;
import com.mypkg.reader.MyReader;
import com.mypkg.writer.MyWriter;

@Configuration
@EnableBatchProcessing
public class BatchProcessor {

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Autowired
	private JobBuilderFactory jobBuilder;

	@Bean
	public MyListener createListener() {
		return new MyListener();
	}

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
	public Job createJob() {
		return jobBuilder.get("job1").incrementer(new RunIdIncrementer()).listener(createListener()).start(createStep())
				.build();
	}
}
