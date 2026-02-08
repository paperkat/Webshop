package com.demo.webshop.repository;

import com.demo.webshop.entity.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>{

    List<Product> findByName(String name);
}
