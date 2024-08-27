package com.youngcamp.server.service;

import com.youngcamp.server.dto.ExampleDto;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

//  Get 읽기 예시
    public String getTestMessage() {
        return "TEST API";
    }

//  POST 읽기 예시
    public String getPostMessage(ExampleDto requestDto) {
        return "Post TEST : " + requestDto.getMessage();
    }
}
