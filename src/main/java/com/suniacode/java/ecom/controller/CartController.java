package com.suniacode.java.ecom.controller;

import com.suniacode.java.ecom.dto.CartItemRequest;
import com.suniacode.java.ecom.model.CartItem;
import com.suniacode.java.ecom.repository.CartItemRepository;
import com.suniacode.java.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        // Logic to add item to cart would go here
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Product out of stock or user/product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId){
        // Logic to remove item from cart would go here
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId) {
        // Logic to get cart items would go here
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
