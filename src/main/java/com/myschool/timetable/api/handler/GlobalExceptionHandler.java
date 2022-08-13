package com.myschool.timetable.api.handler;

import com.myschool.timetable.exception.TimeTableException;
import com.myschool.timetable.models.CustomErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private Environment environment;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorInfo> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ResponseEntity<>(CustomErrorInfo.builder()
                .errorMessage(Objects.requireNonNull(methodArgumentNotValidException
                        .getFieldError()).getDefaultMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeTableException.class)
    public ResponseEntity<CustomErrorInfo> handleTimeTableException(
            TimeTableException timeTableException) {
        return new ResponseEntity<>(CustomErrorInfo.builder()
                .errorMessage(timeTableException.getMessage())
                .errorCode(timeTableException.getHttpStatus().value())
                .httpStatus(timeTableException.getHttpStatus())
                .timestamp(LocalDateTime.now())
                .build(), timeTableException.getHttpStatus());
    }
}
