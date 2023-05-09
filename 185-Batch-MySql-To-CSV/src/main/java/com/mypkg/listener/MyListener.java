package com.mypkg.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Started At : " + jobExecution.getStartTime());
		System.out.println("Current Status : " + jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Ended At : " + jobExecution.getEndTime());
		System.out.println("Current Status : " + jobExecution.getStatus());
		System.out.println("Exit Status : " + jobExecution.getExitStatus());

	}

}
