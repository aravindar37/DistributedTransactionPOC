package com.aravind.temporal.async.restserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class ContextNameController {

    private final BusinessService service1;

    @PostMapping("/asyncOps")
    public ResponseEntity<String> create(@RequestBody String name) {
        service1.callTheAsyncClientService(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
