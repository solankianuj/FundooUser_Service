package com.example.fundoo_user_service.exception;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
@AllArgsConstructor
public class UserNotFound extends RuntimeException{
    private long errorCode;
    private String statusMessage;
}
