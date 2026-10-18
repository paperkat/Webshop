
package com.demo.webshop.controller;

import com.demo.webshop.entity.Cart;
import com.demo.webshop.entity.User;
import com.demo.webshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private Cart createTestCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cart;
    }

    private User createTestUser() {
        String id = java.util.UUID.randomUUID().toString();

        User user = new User();
        user.setUsername("testuser-" + id);
        user.setEmail("testuser-" + id + "@example.com");

        return userRepository.save(user);
    }

    @Test
    void createCart_shouldReturnOk() {
        User user = createTestUser();
        Cart cart = createTestCart(user);

        ResponseEntity<Cart> response =
                restTemplate.postForEntity("/api/carts", cart, Cart.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void getAllCarts_shouldReturnOk() {
        ResponseEntity<Cart[]> response =
                restTemplate.getForEntity("/api/carts", Cart[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void deleteCart_shouldRemoveCart() {
        User user = createTestUser();
        Cart cart = createTestCart(user);

        ResponseEntity<Cart> createResponse =
                restTemplate.postForEntity("/api/carts", cart, Cart.class);

        assertThat(createResponse.getBody()).isNotNull();
        Long cartId = createResponse.getBody().getId();

        ResponseEntity<Void> deleteResponse =
                restTemplate.exchange(
                        "/api/carts/" + cartId,
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );
        assertThat(deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Cart> response =
                restTemplate.getForEntity("/api/carts/" + cartId, Cart.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
