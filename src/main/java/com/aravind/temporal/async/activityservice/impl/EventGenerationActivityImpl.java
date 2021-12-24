package com.aravind.temporal.async.activityservice.impl;

import com.aravind.temporal.async.shared.EventGenerationActivity;
import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import com.aravind.temporal.async.activityservice.service.ActivityService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;

public class EventGenerationActivityImpl implements EventGenerationActivity {

    /**
     * ActivityCompletionClient is used to asynchronously complete activities. In this example we
     * will use this client alongside with {@link
     * ActivityExecutionContext#doNotCompleteOnReturn()} which means our
     * activity method will not complete when it returns, however is expected to be completed
     * asynchronously using the client.
     */

    private final ActivityCompletionClient completionClient;
	private final ActivityService activityService;
	Logger logger = LoggerFactory.getLogger(EventGenerationActivityImpl.class);

    public EventGenerationActivityImpl(ActivityCompletionClient completionClient, ActivityService activityService){
    	this.completionClient = completionClient;
    	this.activityService = activityService;
    }

	@Override
	public void createCSPAndEvents(EventGenerationDto.Request request) {
		ActivityExecutionContext context = Activity.getExecutionContext();
		byte[] taskToken = context.getTaskToken();
		ForkJoinPool.commonPool().execute(() -> createCSPAndEventsAsync(taskToken, request));
		//Ensures activity is not recorded as complete till com.aravind.temporal.async method execution completes
		context.doNotCompleteOnReturn();
	}

	public void createCSPAndEventsAsync(byte[] taskToken, EventGenerationDto.Request request) {
		activityService.executeLogic(request);
		completionClient.complete(taskToken, "Events Generated");
	}

	private void generateEventsAsync(byte[] taskToken, UUID schoolId, UUID domainId) {
		long sleepTime = (long) (100000*Math.random());
		System.out.println("generateEventsAsync events for "+ schoolId + " " + domainId + " " + new Date() + " " + sleepTime);
		try {
			Thread.currentThread().sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("generateEventsAsync** events for "+ schoolId + " " + domainId + " " + new Date());
		completionClient.complete(taskToken, "Events Generated");
		//return "Events Generated";
	}

}
