package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order not found")
public class OrderNoFoundException extends RuntimeException {

    public OrderNoFoundException() {
        super();
    }
}
