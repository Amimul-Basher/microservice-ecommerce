package com.bdpd.orderservice.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
    SERVICE_NOT_AVAILABLE(301, "Service not available"),
    INSUFFICIENT_INVENTORY(302, "Inventory insufficient")
    ;
    private final int code;
    private final String description;

    BusinessErrorCode(int code, String description){
        this.code= code;
        this.description = description;
    }
}
