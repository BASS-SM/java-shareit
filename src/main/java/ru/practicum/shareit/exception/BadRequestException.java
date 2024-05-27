package ru.practicum.shareit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BadRequestException(String message) {
        super(message);
        httpStatus = HttpStatus.valueOf("400");
    }
}
