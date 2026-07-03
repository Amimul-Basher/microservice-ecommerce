package com.bdpd.productservice.service;

import com.bdpd.productservice.dto.ProductPatchRequest;
import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;
import com.bdpd.productservice.mapper.ProductMapper;
import com.bdpd.productservice.model.Product;
import com.bdpd.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow( ()-> new RuntimeException("Product not found"));
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        productRepository.save(product);

        return ProductMapper.toProductResponse(product);
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toProduct(productRequest);
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    public ProductResponse patchUpdate(String id, ProductPatchRequest productPatchRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product with " + id + " not found"));
        if(productPatchRequest.name()!= null){
            product.setName(productPatchRequest.name());
        }
        if(productPatchRequest.price()!= null){
            product.setPrice(productPatchRequest.price());
        }
        if(productPatchRequest.description()!= null){
            product.setDescription(productPatchRequest.description());
        }

        productRepository.save(product);
        return ProductMapper.toProductResponse(product);
    }
}
