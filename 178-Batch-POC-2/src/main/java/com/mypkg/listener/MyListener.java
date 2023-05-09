package com.mypkg.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("MyListener.beforeJob()");

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("MyListener.afterJob()");
	}

}
