package com.aravind.temporal.async.shared;

import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface EventGenerationActivity {

	void createCSPAndEvents(EventGenerationDto.Request request);
}
