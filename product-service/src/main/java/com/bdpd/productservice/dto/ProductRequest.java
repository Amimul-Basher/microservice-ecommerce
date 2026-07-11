package com.bdpd.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
public record ProductRequest (
        @NotBlank(message="Product must be named")
    String name,
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price,

    @NotNull(message="Description is required")
    String description
){}
