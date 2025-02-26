package com.fp.FoodMage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fp.entity.Product;
import com.fp.repository.ProductRepository;
import com.fp.service.ProductService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testFetchByPriceRange_Success() {
        // Arrange
        double lowPrice = 10.0;
        double highPrice = 50.0;
        Product product1 = new Product(1, "Product 1", "Description 1", "Category A", 20.0, LocalDate.now(), LocalDate.now().plusYears(1), null);
        Product product2 = new Product(2, "Product 2", "Description 2", "Category B", 30.0, LocalDate.now(), LocalDate.now().plusYears(1), null);
        Product product3 = new Product(3, "Product 3", "Description 3", "Category C", 60.0, LocalDate.now(), LocalDate.now().plusYears(1), null); 

        List<Product> mockProductList = Arrays.asList(product1, product2, product3);

        when(productRepository.findAll()).thenReturn(mockProductList);

        // Act
        ResponseEntity<List<Product>> response = productService.fetchByPriceRange(lowPrice, highPrice);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> filteredList = response.getBody();
        assertNotNull(filteredList);
        assertEquals(2, filteredList.size()); 
        assertTrue(filteredList.contains(product1));
        assertTrue(filteredList.contains(product2));
        assertFalse(filteredList.contains(product3));
    }

    @Test
    public void testFetchByPriceRange_NoProductsInRange() {
        // Arrange
        double lowPrice = 100.0;
        double highPrice = 200.0;
        Product product1 = new Product(1, "Product 1", "Description 1", "Category A", 20.0, LocalDate.now(), LocalDate.now().plusYears(1), null);
        Product product2 = new Product(2, "Product 2", "Description 2", "Category B", 30.0, LocalDate.now(), LocalDate.now().plusYears(1), null);

        List<Product> mockProductList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(mockProductList);

        // Act
        ResponseEntity<List<Product>> response = productService.fetchByPriceRange(lowPrice, highPrice);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> filteredList = response.getBody();
        assertNotNull(filteredList);
        assertTrue(filteredList.isEmpty()); 
    }

    @Test
    public void testFetchByPriceRange_InvalidPriceRange() {
        // Arrange
        double lowPrice = 50.0;
        double highPrice = 10.0; 
        Product product1 = new Product(1, "Product 1", "Description 1", "Category A", 20.0, LocalDate.now(), LocalDate.now().plusYears(1), null);
        Product product2 = new Product(2, "Product 2", "Description 2", "Category B", 30.0, LocalDate.now(), LocalDate.now().plusYears(1), null);

        List<Product> mockProductList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(mockProductList);

        // Act
        ResponseEntity<List<Product>> response = productService.fetchByPriceRange(lowPrice, highPrice);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> filteredList = response.getBody();
        assertNotNull(filteredList);
        assertTrue(filteredList.isEmpty());
    }
}