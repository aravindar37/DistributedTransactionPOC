package com.aravind.temporal.async.activityservice.worker;

import com.aravind.temporal.async.shared.EventGenerationActivity;
import com.aravind.temporal.async.activityservice.impl.EventGenerationActivityImpl;
import com.aravind.temporal.async.activityservice.service.ActivityService;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventGenerationActivityWorker {
	 static final String TASK_QUEUE = "EventGeneration";

	 private ActivityService activityService;


	 @SuppressWarnings("CatchAndPrintStackTrace")
	 @Autowired
	 public EventGenerationActivityWorker(ActivityService activityService, @Value("${temporal.grpc.url}") String url, @Value("${temporal.http.secure}") boolean https) {
	 	this.activityService = activityService;
		// gRPC stubs wrapper that talks to the local docker instance of temporal service.
		 	WorkflowServiceStubsOptions workflowServiceStubsOptions =  WorkflowServiceStubsOptions.newBuilder()
				 .setTarget(url)
				 .setEnableHttps(https)
				 .build();
		    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(workflowServiceStubsOptions);
		    // client that can be used to start and signal workflows
		    WorkflowClient client = WorkflowClient.newInstance(service);

		    // worker factory that can be used to create workers for specific task queues
		    WorkerFactory factory = WorkerFactory.newInstance(client);

		    // Worker that listens on a task queue and hosts both workflow and activity implementations.
		    Worker worker = factory.newWorker(TASK_QUEUE);

		    ActivityCompletionClient completionClient = client.newActivityCompletionClient();
		    // Activities are stateless and thread safe. So a shared instance is used.

		    EventGenerationActivity activity = new EventGenerationActivityImpl(completionClient, activityService);
		    worker.registerActivitiesImplementations(activity);
		    
		    factory.start();
		    System.out.println("Worker started for task queue: " + TASK_QUEUE);

	}

}
