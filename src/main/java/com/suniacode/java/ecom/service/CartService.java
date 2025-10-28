package com.suniacode.java.ecom.service;

import com.suniacode.java.ecom.dto.CartItemRequest;
import com.suniacode.java.ecom.model.CartItem;
import com.suniacode.java.ecom.model.Product;
import com.suniacode.java.ecom.model.User;
import com.suniacode.java.ecom.repository.CartItemRepository;
import com.suniacode.java.ecom.repository.ProductRepository;
import com.suniacode.java.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        // Implement the logic to add the item to the user's cart
        // This might involve checking if the product exists, if there's enough stock, etc.
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem exisistingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (exisistingCartItem != null) {
            // Update quantity and price
            exisistingCartItem.setQuantity(exisistingCartItem.getQuantity() + request.getQuantity());
            exisistingCartItem.setPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(exisistingCartItem.getQuantity())));
            cartItemRepository.save(exisistingCartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(productId);

        if (userOpt.isPresent() && productOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser).orElse(List.of());
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
