package com.myschool.timetable.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomErrorInfo {
    private String errorMessage;
    private Integer errorCode;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
}
