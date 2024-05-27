package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notFound(final NotFoundException e) {
        log.warn("error {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> badValidException(final BadValidException e) {
        log.warn("error {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> badRequest(final BadRequestException e) {
        log.warn("error {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> throwableException(final Exception e) {
        log.warn("error: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.valueOf(400));
    }
}
