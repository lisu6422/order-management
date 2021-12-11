package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Foods not found")
public class FoodsNotFoundException extends RuntimeException {

    public FoodsNotFoundException() {
        super();
    }
}