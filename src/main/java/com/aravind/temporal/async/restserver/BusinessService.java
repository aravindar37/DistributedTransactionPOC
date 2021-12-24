package com.aravind.temporal.async.restserver;

import com.aravind.temporal.async.restserver.EventGenerationClient;
import com.aravind.temporal.async.shared.dto.EventGenerationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    @Value("${temporal.grpc.url}")
    String grpcUrl;


    public void callTheAsyncClientService(String string){

        EventGenerationClient eventGenerationClient = new EventGenerationClient();
        EventGenerationDto.Request request = EventGenerationDto.Request.builder()
                .param1(string)
                .build();
        eventGenerationClient.generateEventsClient(request, grpcUrl);
    }

}
