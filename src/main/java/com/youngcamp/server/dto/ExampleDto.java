package com.youngcamp.server.dto;

public class ExampleDto {
    private String msg;

    public ExampleDto() {}

    public ExampleDto(String msg) {
        this.msg = msg;
    }

    // Get/Set post
    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }
}
