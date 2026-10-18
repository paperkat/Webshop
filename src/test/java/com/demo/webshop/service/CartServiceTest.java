package com.demo.webshop.service;

import com.demo.webshop.entity.Cart;
import com.demo.webshop.entity.User;
import com.demo.webshop.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void getAllCarts_shouldReturnAllCarts() {
        Cart cart = new Cart();
        when(cartRepository.findAll()).thenReturn(List.of(cart));

        Iterable<Cart> result = cartService.getAllCarts();

        assertThat(result).containsExactly(cart);
        verify(cartRepository).findAll();
    }

    @Test
    void getCartById_shouldReturnCartWhenFound() {
        Cart cart = new Cart();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getCartById(1L);

        assertThat(result).contains(cart);
        verify(cartRepository).findById(1L);
    }

    @Test
    void getCartById_shouldReturnEmptyWhenNotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getCartById(1L);

        assertThat(result).isEmpty();
        verify(cartRepository).findById(1L);
    }

    @Test
    void getCartByUserId_shouldReturnCartWhenFound() {
        Cart cart = new Cart();
        when(cartRepository.findByUserId(10L)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getCartByUserId(10L);

        assertThat(result).contains(cart);
        verify(cartRepository).findByUserId(10L);
    }

    @Test
    void getCartByUserId_shouldReturnEmptyWhenNotFound() {
        when(cartRepository.findByUserId(10L)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getCartByUserId(10L);

        assertThat(result).isEmpty();
        verify(cartRepository).findByUserId(10L);
    }

    @Test
    void saveCart_shouldReturnSavedCart() {
        Cart cart = new Cart();
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.saveCart(cart);

        assertThat(result).isSameAs(cart);
        verify(cartRepository).save(cart);
    }

    @Test
    void deleteCart_shouldClearUserReferenceAndDeleteCart() {
        Cart cart = new Cart();
        User user = new User();

        cart.setUser(user);
        user.setCart(cart);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        cartService.deleteCart(1L);

        assertThat(user.getCart()).isNull();
        verify(cartRepository).delete(cart);
    }

    @Test
    void deleteCart_shouldThrowWhenCartNotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.deleteCart(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cart not found: 1");

        verify(cartRepository, never()).delete(any(Cart.class));
    }

    @Test
    void deleteCart_shouldDeleteCartWithoutUser() {
        Cart cart = new Cart();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        cartService.deleteCart(1L);

        verify(cartRepository).delete(cart);
    }
}
