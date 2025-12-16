package com.demo.webshop.service;

import com.demo.webshop.entity.User;
import com.demo.webshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        return user;
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User user1 = createTestUser();
        User user2 = createTestUser();
        user2.setId(2L);
        user2.setUsername("anotheruser");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        Iterable<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("username").contains("testuser", "anotheruser");

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUserIfExists() {
        User user = createTestUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldReturnEmptyIfNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99L);

        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void saveUser_shouldReturnSavedUser() {
        User user = createTestUser();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_shouldCallRepositoryDelete() {
        User user = createTestUser();

        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
