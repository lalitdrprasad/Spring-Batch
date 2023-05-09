package com.mypkg;

import java.util.Random;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class BatchRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher launcher;
	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		
		JobParameters parameters = new JobParametersBuilder().addLong("id", new Random().nextLong(10000000))
				.toJobParameters();
		launcher.run(job, parameters);
	}
}