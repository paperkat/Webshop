package com.demo.webshop.controller;

import com.demo.webshop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Product createTestProduct() {
        Product product = new Product();
        product.setName("Product_" + System.currentTimeMillis());
        product.setDescription("Test description");
        product.setPrice(19.99);
        product.setStockQuantity(10);
        return product;
    }

    @Test
    void createProduct_shouldReturnOk() {
        Product product = createTestProduct();

        ResponseEntity<Product> response =
                restTemplate.postForEntity("/api/products", product, Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(product.getName());
    }

    @Test
    void getAllProducts_shouldReturnOk() {
        ResponseEntity<Product[]> response =
                restTemplate.getForEntity("/api/products", Product[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getProductById_shouldReturnProduct() {
        Product product = createTestProduct();
        ResponseEntity<Product> createResponse =
                restTemplate.postForEntity("/api/products", product, Product.class);

        Long productId = createResponse.getBody().getId();

        ResponseEntity<Product> response =
                restTemplate.getForEntity("/api/products/" + productId, Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(productId);
    }

    @Test
    void getProductByName_shouldReturnProduct() {
        Product product = createTestProduct();
        restTemplate.postForEntity("/api/products", product, Product.class);

        ResponseEntity<Product[]> response =
                restTemplate.getForEntity(
                        "/api/products/name/" + product.getName(),
                        Product[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getName()).isEqualTo(product.getName());
    }

    @Test
    void deleteProduct_shouldRemoveProduct() {
        Product product = createTestProduct();
        ResponseEntity<Product> createResponse =
                restTemplate.postForEntity("/api/products", product, Product.class);

        Long productId = createResponse.getBody().getId();

        restTemplate.delete("/api/products/" + productId);

        ResponseEntity<Product> response =
                restTemplate.getForEntity("/api/products/" + productId, Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
