package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final String status;
    private final LocalDateTime timestamp;


}
