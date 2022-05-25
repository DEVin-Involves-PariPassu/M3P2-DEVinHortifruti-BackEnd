package tech.devinhouse.devinhortifrutiapi.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.devinhouse.devinhortifrutiapi.dto.ErrorResponse;
import tech.devinhouse.devinhortifrutiapi.service.exception.UserIsUnderAgeException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserIsUnderAgeHandler {
    @ExceptionHandler({ UserIsUnderAgeException.class })
    public ResponseEntity<ErrorResponse> userIsUnderAge(UserIsUnderAgeException e) {

        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(LocalDateTime.now());
        error.getMessages().add(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}