package com.bdpd.productservice.service;

import com.bdpd.productservice.dto.ProductPatchRequest;
import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;
import com.bdpd.productservice.mapper.ProductMapper;
import com.bdpd.productservice.model.Product;
import com.bdpd.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;


    //Two way we can implement redis cache operation, 1. Annotation based  2. Using cache manager interface
    //Redis cache manager implements cache manager
    private final CacheManager cacheManager;

    //Using cache put we always replace or enter the data with the given id
    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#id()")
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow( ()-> new RuntimeException("Product not found"));
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        productRepository.save(product);
        ProductResponse productResponse = ProductMapper.toProductResponse(product);
        //Here the cache manager will will find the cash section with name PRODUCT_SERVICE
        //If found will return that particular cache section to make query if not will create one
        Cache redisCache = cacheManager.getCache("PRODUCT_SERVICE");
        //Casche will put the info as key value pair
        redisCache.put(id,productResponse );
        return ProductMapper.toProductResponse(product);
    }

    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#result.id()")
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toProduct(productRequest);
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return ProductMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    //Here you must use unless because if return the method returns null, you are calling id() of a null value
    //  -- todo If you want to refer to the response of the method you must use the keyword "result"
    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#result.id()", unless = "#result == null")
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
        ProductResponse productResponse = ProductMapper.toProductResponse(product);
        //Here the cache manager will will find the cash section with name PRODUCT_SERVICE
        //If found will return that particular cache section to make query if not will create one
//        Cache redisCache = cacheManager.getCache("PRODUCT_CACHE");
//        //Casche will put the info as key value pair
//        redisCache.put(id,productResponse );
        return productResponse;
    }


    //Cacheable annotation helps us searching on cache first if not found in redis the database search operation get executed
    @Override
    @Cacheable(value = "PRODUCT_CACHE", key = "#productId")
    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found with id: " + productId));
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    @Override
    @CacheEvict(value = "PRODUCT_CACHE", key = "#productId")
    public ProductResponse deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found with Id: " + productId));
        productRepository.deleteById(productId);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }
}
