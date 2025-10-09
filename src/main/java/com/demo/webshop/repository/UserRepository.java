package com.demo.webshop.repository;

import com.demo.webshop.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
