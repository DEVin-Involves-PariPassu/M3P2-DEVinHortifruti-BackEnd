package tech.devinhouse.devinhortifrutiapi.controller.handler;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.devinhouse.devinhortifrutiapi.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EntityNotFoundHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException e) {

        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(LocalDateTime.now());
        error.getMessages().add(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}