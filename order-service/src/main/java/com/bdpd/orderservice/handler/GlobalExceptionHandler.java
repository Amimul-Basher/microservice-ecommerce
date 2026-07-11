package com.bdpd.orderservice.handler;

import com.bdpd.orderservice.handler.exception.BusinessErrorCode;
import com.bdpd.orderservice.handler.exception.InventoryServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bdpd.orderservice.handler.exception.BusinessErrorCode.SERVICE_NOT_AVAILABLE;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InventoryServiceUnavailableException.class)
    public ResponseEntity<ExceptionResponse> handleInventoryUnavailable(InventoryServiceUnavailableException ex){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(SERVICE_NOT_AVAILABLE.getCode())
                                .businessErrorDescription(SERVICE_NOT_AVAILABLE.getDescription())
                                .errorMessage(ex.getMessage())
                                .build()
                );
    }
}
