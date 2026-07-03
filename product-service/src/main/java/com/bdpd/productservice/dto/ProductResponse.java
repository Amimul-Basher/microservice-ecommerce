package com.bdpd.productservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        BigDecimal price,
        String description
) {
}
