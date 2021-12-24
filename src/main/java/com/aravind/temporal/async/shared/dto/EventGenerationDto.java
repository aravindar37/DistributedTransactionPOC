package com.aravind.temporal.async.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface EventGenerationDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class Request{
        String param1;
        String param2;
        String param3;
        String param4;
    }
}
