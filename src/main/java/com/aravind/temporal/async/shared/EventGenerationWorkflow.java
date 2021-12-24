package com.aravind.temporal.async.shared;

import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface EventGenerationWorkflow {
	@WorkflowMethod
	  void generateEvents(EventGenerationDto.Request request);
}
