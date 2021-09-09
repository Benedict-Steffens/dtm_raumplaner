package project.raumplaner.RaumplanerApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(value = {EntityNotFoundException.class, IllegalArgumentException.class, NullPointerException.class, RuntimeException.class})
    public ResponseEntity<Object> handleApiRequestException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof IllegalArgumentException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof NullPointerException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof RuntimeException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ApiException apiException = new ApiException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, status);
    }
}
