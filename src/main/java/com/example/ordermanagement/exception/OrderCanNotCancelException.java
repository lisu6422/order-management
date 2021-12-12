package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Order can not cancel")
public class OrderCanNotCancelException extends RuntimeException {

    public OrderCanNotCancelException() {
        super();
    }
}