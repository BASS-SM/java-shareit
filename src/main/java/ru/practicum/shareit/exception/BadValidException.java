package ru.practicum.shareit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadValidException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BadValidException(String message) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(409);
    }
}
