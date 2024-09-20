package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.exceptionHandler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {


    @ExceptionHandler(NoItemFoundException.class)
    public ResponseEntity<String> handleNoItemFoundException(NoItemFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<Map<String,String>> handleNotEnoughStockException(NotEnoughStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
    }

}
