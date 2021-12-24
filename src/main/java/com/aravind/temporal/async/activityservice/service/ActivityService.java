package com.aravind.temporal.async.activityservice.service;

import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    public void executeLogic(EventGenerationDto.Request request) {
        try{
            Thread.sleep(10000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
