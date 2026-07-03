package com.bdpd.productservice.dto;

import java.math.BigDecimal;

public record ProductPatchRequest (
        String name,
        String description,
        BigDecimal price
){
}
