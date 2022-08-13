package com.myschool.timetable.exception;

import org.springframework.http.HttpStatus;

public class TimeTableException extends RuntimeException {
    private final HttpStatus httpStatus;

    public TimeTableException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
