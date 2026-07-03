package com.bdpd.productservice.service;


import com.bdpd.productservice.dto.ProductPatchRequest;
import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse updateProduct(String id, ProductRequest productRequest);

    public void createProduct(ProductRequest productRequest);

    List<ProductResponse> getProducts();

    ProductResponse patchUpdate(java.lang.String id, com.bdpd.productservice.dto.ProductPatchRequest productPatchRequest);
}
