package com.bdpd.productservice.service;


import com.bdpd.productservice.dto.ProductPatchRequest;
import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse updateProduct(String id, ProductRequest productRequest);

    public ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getProducts();

    ProductResponse patchUpdate(String id, ProductPatchRequest productPatchRequest);

    ProductResponse getProduct(String productId);

    ProductResponse deleteProduct(String productId);
}
