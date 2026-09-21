package com.app.ecom.services;

import com.app.ecom.dtos.ProductRequest;
import com.app.ecom.dtos.ProductResponse;
import com.app.ecom.models.Product;
import com.app.ecom.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct =  productRepository.save(product);
        return mapToProductResponse(savedProduct);

    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findByIsActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response= new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setIsActive(savedProduct.getIsActive());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
    }


    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setIsActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
