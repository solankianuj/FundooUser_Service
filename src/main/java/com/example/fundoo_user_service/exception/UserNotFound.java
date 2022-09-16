package com.example.fundoo_user_service.exception;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus

public class UserNotFound extends RuntimeException{
    private long errorCode;
    private String statusMessage;

    public UserNotFound(long errorCode, String statusMessage) {
        super(statusMessage);
        this.errorCode = errorCode;
        this.statusMessage = statusMessage;
    }
}
