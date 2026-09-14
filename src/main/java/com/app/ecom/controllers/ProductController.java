package com.app.ecom.controllers;

import com.app.ecom.dtos.ProductRequest;
import com.app.ecom.dtos.ProductResponse;
import com.app.ecom.services.ProductService;
import com.app.ecom.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController  {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct (@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {

        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
