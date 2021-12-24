package com.aravind.temporal.async.workflowservice.worker;

import com.aravind.temporal.async.workflowservice.impl.EventGenerationWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpecificWorkflowWorker {

	static final String TASK_QUEUE = "EventGeneration";

	  @SuppressWarnings("CatchAndPrintStackTrace")
	  @Autowired
	  public SpecificWorkflowWorker(@Value("${temporal.grpc.url}") String url, @Value("${temporal.http.secure}") boolean https) {
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

	    // Workflows are stateful. So you need a type to create instances.
	    worker.registerWorkflowImplementationTypes(EventGenerationWorkflowImpl.class);

	    
	    // Start all workers created by this factory.
	    factory.start();
	    System.out.println("Worker started for task queue: " + TASK_QUEUE);

	    //System.exit(0);
	  }

}
