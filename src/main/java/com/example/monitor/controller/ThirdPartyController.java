package com.example.monitor.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/third-party")
public class ThirdPartyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ThirdPartyController.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/data")
    @CircuitBreaker(name = "thirdPartyService", fallbackMethod = "fallbackThirdParty")
    public ResponseEntity<String> getThirdPartyData() {
        // 这里替换成实际的第三方服务URL
        String thirdPartyUrl = "http://third-party-service.com/api/data";
        return restTemplate.getForEntity(thirdPartyUrl, String.class);
    }
    
    public ResponseEntity<String> fallbackThirdParty(Exception ex) {
        logger.error("Third party service call failed", ex);
        return ResponseEntity.ok("Fallback Response: Service is temporarily unavailable");
    }
} 