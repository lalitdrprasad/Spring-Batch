package com.mypkg.batch;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

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
	public JdbcCursorItemReader<Employee> createReader() {
		/*
		 * JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
		 * reader.setDataSource(ds); reader.setSql("Select * from Batch_Employee");
		 * reader.setName("reader"); reader.setRowMapper((rs, rowNum) -> { return new
		 * Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
		 * rs.getFloat(5), rs.getFloat(6)); }); return reader;
		 */

		return new JdbcCursorItemReaderBuilder<Employee>().name("reader").dataSource(ds)
				.sql("Select * from Batch_Employee").beanRowMapper(Employee.class).build();
	}

	@Bean("writer")
	public FlatFileItemWriter<Employee> createWriter() {
		/*
		 * FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
		 * writer.setResource(new FileSystemResource(new
		 * File("D:\\lalit\\employees.csv"))); BeanWrapperFieldExtractor<Employee>
		 * extractor = new BeanWrapperFieldExtractor<>(); extractor.setNames(new
		 * String[] { "id", "name", "address", "salary", "grosssalary", "netsalary" });
		 * DelimitedLineAggregator<Employee> aggregator = new
		 * DelimitedLineAggregator<>(); aggregator.setDelimiter(",");
		 * aggregator.setFieldExtractor(extractor);
		 * writer.setLineAggregator(aggregator); return writer;
		 */

		/*
		 * FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
		 * writer.setResource(new FileSystemResource(new
		 * File("D:\\lalit\\employees.csv"))); writer.setLineAggregator(new
		 * DelimitedLineAggregator<>() { { setDelimiter("."); setFieldExtractor(new
		 * BeanWrapperFieldExtractor<>() { { setNames(new String[] { "id", "name",
		 * "address", "salary", "grosssalary", "netsalary" }); } }); } }); return
		 * writer;
		 */

		return new FlatFileItemWriterBuilder<Employee>()
				.resource(new FileSystemResource(new File("D:\\lalit\\employees.csv"))).name("writer")
				.lineAggregator(new DelimitedLineAggregator<>() {
					{
						setDelimiter(",");
					}
				}).build();

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
