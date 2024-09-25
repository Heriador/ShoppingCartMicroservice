package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.exceptionHandler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.LimitItemPerCategoryException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.QuantityNotPositiveException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {


    @ExceptionHandler(NoItemFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoItemFoundException(NoItemFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ExceptionResponse> handleNotEnoughStockException(NotEnoughStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(QuantityNotPositiveException.class)
    public ResponseEntity<ExceptionResponse> handleQuantityNotPositiveException(QuantityNotPositiveException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(LimitItemPerCategoryException.class)
    public ResponseEntity<ExceptionResponse> handleLimitItemPerCategoryException(LimitItemPerCategoryException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(e.getMessage(), HttpStatus.FORBIDDEN.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionResponse> handleMalformedJwtException(MalformedJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponse> handleSignatureException(SignatureException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now()));
    }

}
