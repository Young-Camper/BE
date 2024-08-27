package com.youngcamp.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public Map<String, String> getTest() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "test success");
        return response;
    }
}
