package com.aravind.temporal.async.workflowservice.impl;

import com.aravind.temporal.async.shared.EventGenerationActivity;
import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import com.aravind.temporal.async.shared.EventGenerationWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class EventGenerationWorkflowImpl implements EventGenerationWorkflow {
	private final ActivityOptions options =
		      ActivityOptions.newBuilder()
		          .setStartToCloseTimeout(Duration.ofHours(1))
		          .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
		          .build();
	  private final EventGenerationActivity activity =
		      Workflow.newActivityStub(EventGenerationActivity.class, options);
	  
	@Override
	public void generateEvents(EventGenerationDto.Request request) {
		System.out.println("calling activity to generate events "+Workflow.getInfo().getAttempt());
		activity.createCSPAndEvents(request);
		System.out.println("finished calling activity to generate events ");
	}

}
