package com.demo.webshop.service;

import com.demo.webshop.entity.Product;
import com.demo.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product createTestProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test product");
        product.setDescription("Test description");
        product.setPrice(new BigDecimal("9.99"));
        product.setStockQuantity(10);
        return product;
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        Product p1 = createTestProduct();
        Product p2 = createTestProduct();
        p2.setId(2L);
        p2.setName("Another product");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        Iterable<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("name")
                          .contains("Test product", "Another product");

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_shouldReturnProductIfExists() {
        Product product = createTestProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test product");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_shouldReturnEmptyIfNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(99L);

        assertThat(result).isEmpty();
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void getProductByName_shouldReturnMatchingProducts() {
        Product product = createTestProduct();

        when(productRepository.findByName("Test product"))
                .thenReturn(List.of(product));

        List<Product> result = productService.getProductByName("Test product");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test product");

        verify(productRepository, times(1)).findByName("Test product");
    }

    @Test
    void saveProduct_shouldReturnSavedProduct() {
        Product product = createTestProduct();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test product");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct_shouldCallRepositoryDelete() {
        Product product = createTestProduct();

        doNothing().when(productRepository).deleteById(product.getId());

        productService.deleteProduct(product.getId());

        verify(productRepository, times(1))
                .deleteById(product.getId());
    }
}
