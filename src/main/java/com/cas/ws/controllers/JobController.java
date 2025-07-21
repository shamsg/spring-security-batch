package com.cas.ws.controllers;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;


	@PostMapping("/importTrxHistory")
	public String jobLauncherTrxHistory() {

		final JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		try {
			// Launch the job
			final JobExecution jobExecution = jobLauncher.run(job, jobParameters);

			// Return job status
			return jobExecution.getStatus().toString();
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
			return "Job failed with exception: " + e.getMessage();
		}
	}
	}



