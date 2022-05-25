package tech.devinhouse.devinhortifrutiapi.controller.handler;

import javax.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.devinhouse.devinhortifrutiapi.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EntityExistsHandler {
    @ExceptionHandler({EntityExistsException.class })
    public ResponseEntity<ErrorResponse> entityExists(EntityExistsException e) {

        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setTimestamp(LocalDateTime.now());
        error.getMessages().add(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
