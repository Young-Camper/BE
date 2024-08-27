package com.youngcamp.server.controller;

import com.youngcamp.server.dto.ExampleDto;
import com.youngcamp.server.service.ExampleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private final ExampleService example_service;

//  생성자
    public ExampleController(ExampleService example_service) {
        this.example_service = example_service;
    }

//  Get 메소드
    @GetMapping("/test")
    public String getTest() {
        return example_service.getTestMessage();
    }

//  Post 메소드
    @PostMapping("/test")
    public String postTest(@RequestBody ExampleDto requestBody) {
        return example_service.getPostMessage(requestBody);
    }
}