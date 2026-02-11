package com.investsight.api.controller;

import org.springframework.web.bind.annotation  .GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String check() {
        return "InvestSight API is running";
    }
}