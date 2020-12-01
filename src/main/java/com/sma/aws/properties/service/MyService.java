package com.sma.aws.properties.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @author Mak Sophea
 * @date : 1/20/2020
 **/
@Service
@Slf4j
public class MyService {

    @Retryable(value = { ConnectException.class , SocketTimeoutException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public ResponseEntity<String> test3rdService() {
        log.info("calling to the service ");
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> result = template.getForEntity("http://localhost:8080/actuator/info", String.class);
        //throw new RuntimeException("runtime exception");
        return result;
    }


}
