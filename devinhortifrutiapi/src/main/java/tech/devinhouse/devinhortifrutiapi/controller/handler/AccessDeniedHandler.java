package tech.devinhouse.devinhortifrutiapi.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.devinhouse.devinhortifrutiapi.dto.ErrorResponse;
import tech.devinhouse.devinhortifrutiapi.service.exception.AccessDeniedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AccessDeniedHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorResponse> acessDenied(AccessDeniedException e) {

        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.FORBIDDEN.value());
        error.setTimestamp(LocalDateTime.now());
        error.getMessages().add(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}