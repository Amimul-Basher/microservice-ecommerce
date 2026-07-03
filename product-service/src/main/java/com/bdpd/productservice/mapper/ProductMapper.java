package com.bdpd.productservice.mapper;

import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;
import com.bdpd.productservice.model.Product;

public final class ProductMapper {
    public static Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .id(product.getId())
                .build();
    }
}
