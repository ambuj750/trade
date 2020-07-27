package com.assignment.trade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LowerVersionTradeException extends RuntimeException {

    public LowerVersionTradeException(String exception) {
        super(exception);
    }
}