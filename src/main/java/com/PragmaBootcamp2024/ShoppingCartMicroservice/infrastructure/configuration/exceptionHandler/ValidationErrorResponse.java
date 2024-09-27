package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private Map<String, String> errors;
    private String status;
    private LocalDateTime timestamp;
}