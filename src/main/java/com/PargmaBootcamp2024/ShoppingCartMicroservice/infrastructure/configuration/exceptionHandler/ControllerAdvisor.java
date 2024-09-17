package com.PargmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.exceptionHandler;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {


    @ExceptionHandler(NoItemFoundException.class)
    public ResponseEntity<String> handleNoItemFoundException(NoItemFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
