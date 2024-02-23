package com.demo.MQTT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class TestController {

    @GetMapping("")
    public String testOauth2(){
        return "WelCome";
    }

}
