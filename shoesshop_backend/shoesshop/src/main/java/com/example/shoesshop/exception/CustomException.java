package com.example.shoesshop.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class CustomException extends RuntimeException{
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public CustomException(ErrorResponseEnum errorResponseEnum) {
        this.message = errorResponseEnum.message;
        this.status  = errorResponseEnum.status;
        this.timestamp = LocalDateTime.now();
    }

    public CustomException(String message, int status, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
