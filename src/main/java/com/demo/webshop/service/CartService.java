package com.demo.webshop.service;

import com.demo.webshop.entity.User;
import com.demo.webshop.repository.CartRepository;
import com.demo.webshop.entity.Cart;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    
    public Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found: " + id));

        User user = cart.getUser();

        if (user != null) {
            user.setCart(null);
        }

        cartRepository.delete(cart);
    }

}
