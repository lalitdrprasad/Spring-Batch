package com.mypkg.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mypkg.listener.MyListener;
import com.mypkg.model.Employee;
import com.mypkg.processor.EmployeeProcessor;

@Configuration
@EnableBatchProcessing
public class BatchProcessorConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private MyListener listener;
	@Autowired
	private EmployeeProcessor processor;
	@Autowired
	private DataSource ds;

	@Bean("reader")
	public FlatFileItemReader<Employee> createReader() {

		/*
		 * return new FlatFileItemReaderBuilder<Employee>() .name("reader")
		 * .resource(new ClassPathResource("employees.csv")) .delimited().delimiter(",")
		 * .names("id","name","address","salary") .targetType(Employee.class) .build();
		 */

		FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();

		reader.setResource(new ClassPathResource("employees.csv"));

		reader.setLineMapper(new DefaultLineMapper<>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(DELIMITER_COMMA);
						setNames("id", "name", "address", "salary");
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					{
						setTargetType(Employee.class);
					}
				});
			}

		});

		return reader;
	}

	@Bean("writer")
	public JdbcBatchItemWriter<Employee> createWriter() {

		/*
		 * JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
		 * writer.setDataSource(ds); writer.setItemSqlParameterSourceProvider(new
		 * BeanPropertyItemSqlParameterSourceProvider<>()); writer.
		 * setSql("Insert into Batch_Employee values (:id,:name,:address,:salary,:grosssalary,:netsalary)"
		 * ); return writer;
		 */

		return new JdbcBatchItemWriterBuilder<Employee>().dataSource(ds).sql("delete from Batch_Employee")
				.sql("Insert into Batch_Employee values (:id,:name,:address,:salary,:grosssalary,:netsalary)")
				.beanMapped().build();
	}

	@Bean("step1")
	public Step createStep() {

		return stepBuilderFactory.get("step1").<Employee, Employee>chunk(100000).reader(createReader())
				.writer(createWriter()).processor(processor).build();
	}

	@Bean("job1")
	public Job createJob() {
		return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer()).listener(listener).start(createStep())
				.build();
	}

}
