package com.bdpd.productservice.controller;

import com.bdpd.productservice.dto.ProductPatchRequest;
import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.dto.ProductResponse;
import com.bdpd.productservice.service.ProductService;
import com.bdpd.productservice.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
         return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/get/{id}")
    public ProductResponse getProduct(@PathVariable("id") String productId){
        return productService.getProduct(productId);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @RequestBody ProductRequest productRequest
    ){
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<ProductResponse> updateProductPatch(
            @PathVariable("id") String id,
            @RequestBody ProductPatchRequest productPatchRequest
    ){
            return ResponseEntity.ok(productService.patchUpdate(id, productPatchRequest));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") String productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }


}
