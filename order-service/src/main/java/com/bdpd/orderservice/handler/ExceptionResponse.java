package com.bdpd.orderservice.handler;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//It's wrapper class of the exception
public class ExceptionResponse {
    private Integer businessErrorCode;

    private String businessErrorDescription;
    private String errorMessage;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
