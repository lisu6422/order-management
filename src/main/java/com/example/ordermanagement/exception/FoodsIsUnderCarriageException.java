package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Foods not found")
public class FoodsIsUnderCarriageException extends RuntimeException {

    public FoodsIsUnderCarriageException() {
        super();
    }
}
