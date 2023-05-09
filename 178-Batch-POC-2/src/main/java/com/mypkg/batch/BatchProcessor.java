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
	@Autowired
	private MyWriter writer;
	@Autowired
	private MyReader reader;
	@Autowired
	private MyProcessor processor;
	@Autowired
	private MyListener listener;

	@Bean("step1")
	public Step createStep() {
		return stepBuilder.get("step1").<String, String>chunk(6).writer(writer).reader(reader).processor(processor)
				.build();
	}

	@Bean
	public Job createJob() {
		return jobBuilder.get("job1").incrementer(new RunIdIncrementer()).listener(listener).start(createStep())
				.build();
	}
}
