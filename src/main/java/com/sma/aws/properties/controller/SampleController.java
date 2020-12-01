package com.sma.aws.properties.controller;

import com.sma.aws.properties.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mak Sophea
 * @date : 1/20/2020
 **/
@Controller
@ResponseBody
@Slf4j
public class SampleController {

//    @RequestMapping("/unreliable")
//    public String sayHelloWorldUnreliable() {
//        return getMessage();
//    }
//
//    @RequestMapping("/reliable")
//    public Object sayHelloWorldReliable() {
//        return Failsafe.with(test).get(this::getMessage);
//    }

//    @RequestMapping("/reliableWithDelay")
//    public String sayHelloWorldReliableWithDelay() {
//        return Failsafe.with(delay)
//
//                .withFallback("Service unavailable").get(this::getMessage);
//    }
    @Autowired
    private MyService service;
    @RequestMapping("/3rd")
    public ResponseEntity<String> test3rd() {
        log.info("TEST++++++++++++++++");


        return service.test3rdService();
    }

}