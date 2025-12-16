package com.demo.webshop.controller;

import com.demo.webshop.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private User createTestUser() {
        long now = System.currentTimeMillis();

        User user = new User();
        user.setUsername("user_" + now);
        user.setEmail("user_" + now + "@test.com");
        user.setPassword("password");
        return user;
    }

    @Test
    void createUser_shouldReturnOk() {
        User user = createTestUser();

        ResponseEntity<User> response =
                restTemplate.postForEntity("/api/users", user, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void getAllUsers_shouldReturnOk() {
        ResponseEntity<User[]> response =
                restTemplate.getForEntity("/api/users", User[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getUserByUsername_shouldReturnUser() {
        // create user first
        User user = createTestUser();
        ResponseEntity<User> createResponse =
                restTemplate.postForEntity("/api/users", user, User.class);

        String username = createResponse.getBody().getUsername();

        // query by the SAME username
        ResponseEntity<User[]> response =
                restTemplate.getForEntity(
                        "/api/users/username/" + username,
                        User[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getUsername()).isEqualTo(username);
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = createTestUser();
        ResponseEntity<User> createResponse =
                restTemplate.postForEntity("/api/users", user, User.class);

        Long userId = createResponse.getBody().getId();

        ResponseEntity<User> response =
                restTemplate.getForEntity("/api/users/" + userId, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        User user = createTestUser();
        ResponseEntity<User> createResponse =
                restTemplate.postForEntity("/api/users", user, User.class);

        Long userId = createResponse.getBody().getId();

        restTemplate.delete("/api/users/" + userId);

        ResponseEntity<User> response =
                restTemplate.getForEntity("/api/users/" + userId, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createUser_shouldReturnConflict_whenUsernameExists() {
        User user = createTestUser();

        restTemplate.postForEntity("/api/users", user, User.class);

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/users", user, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

}
