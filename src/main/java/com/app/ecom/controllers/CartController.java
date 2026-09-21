package com.app.ecom.controllers;

import com.app.ecom.dtos.CartItemRequest;
import com.app.ecom.models.CartItem;
import com.app.ecom.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request
    ){
        if(!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ) {
       boolean deleted = cartService.deleteItemFromCart(userId, productId);
       return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> fetchCartItems(
            @RequestHeader("X-User-ID") String userId
    ){
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
