package com.mythik.orderManager.service.impl;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.request.ProductRequest;
import com.mythik.orderManager.repository.ProductRepository;
import com.mythik.orderManager.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAllProducts() {
        Product product = new Product();
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.fetchAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testAddProduct() {
        ProductRequest request = TestUtil.populateProductRequest();
        request.setName("Test Product");
        request.setDescription("Test Description");
        request.setPrice(100);

        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(request);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        ProductRequest request = TestUtil.populateProductRequest();
        request.setName("Updated Product");
        request.setDescription("Updated Description");
        request.setPrice(200);

        Product existingProduct = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));

        Product result = productService.updateProduct(1L, request);

        assertNotNull(result);
        assertEquals("Updated Product", existingProduct.getName());
        assertEquals("Updated Description", existingProduct.getDescription());
        assertEquals(200, existingProduct.getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProductRequest request = TestUtil.populateProductRequest();
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, request));
        verify(productRepository, times(1)).findById(1L);
    }
}