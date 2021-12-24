package com.aravind.temporal.async.restserver;

import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import com.aravind.temporal.async.shared.EventGenerationWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class EventGenerationClient {

	static final String TASK_QUEUE = "EventGeneration";
	//static final String WORKFLOW_ID = "EventGenerationWorkflow";

	Logger logger = LoggerFactory.getLogger(EventGenerationClient.class);

	@SuppressWarnings("CatchAndPrintStackTrace")


	public void generateEventsClient(EventGenerationDto.Request input, String grpcUrl) {

	    // gRPC stubs wrapper that talks to the local docker instance of temporal service.
		WorkflowServiceStubsOptions workflowServiceStubsOptions = WorkflowServiceStubsOptions.newBuilder()
				.setTarget(grpcUrl)
				.build();
	    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(workflowServiceStubsOptions);
	    // client that can be used to start and signal workflows
	    WorkflowClient client = WorkflowClient.newInstance(service);
	    // now we can start running instances of our saga - its state will be persisted
	    	long startTime = System.currentTimeMillis();
	    	String WORKFLOW_ID = UUID.randomUUID().toString();
		    WorkflowOptions options = WorkflowOptions.newBuilder()
					.setWorkflowId(WORKFLOW_ID)
					.setTaskQueue(TASK_QUEUE)
					.setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
					.build();
	    	EventGenerationWorkflow eventGenerator = client.newWorkflowStub(EventGenerationWorkflow.class, options);
	    	try {
	    		WorkflowClient.execute(eventGenerator::generateEvents,input);
	    	} catch (WorkflowException e) {
				logger.error("Exception:" +e.toString());
 	    	}
	    	System.out.println("Client finished " + new Date() + " " + (System.currentTimeMillis() - startTime) + " " + WORKFLOW_ID);
	  }
}
