package com.microservices.limitservices.controller;


import com.microservices.limitservices.config.Configuration;
import com.microservices.limitservices.model.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration getLimits(){
        return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
    }

    @GetMapping("/fault-tolerance")
    @HystrixCommand(fallbackMethod = "fallbackRetriveConfiguration")
    public LimitConfiguration getLimitConfiguration()
    {
        throw new RuntimeException("Not available ");
    }

    public LimitConfiguration fallbackRetriveConfiguration()
    {
        return new LimitConfiguration(9999,9);
    }
}
