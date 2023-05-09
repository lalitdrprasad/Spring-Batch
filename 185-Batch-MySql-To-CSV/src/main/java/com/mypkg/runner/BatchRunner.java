package com.mypkg.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchRunner implements CommandLineRunner {

	@Autowired
	private Job job;
	@Autowired
	private JobLauncher launcher;

	@Override
	public void run(String... args) throws Exception {
		JobParameters params = new JobParametersBuilder().addDate("Date", new Date()).toJobParameters();
		launcher.run(job, params);
		
	}

}
