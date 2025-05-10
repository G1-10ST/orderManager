package com.mythik.orderManager.controller;

import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.request.ProductRequest;
import com.mythik.orderManager.service.impl.ProductServiceImpl;
import com.mythik.orderManager.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testFetchProducts() throws Exception {
        when(productServiceImpl.fetchAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/product/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(productServiceImpl, times(1)).fetchAllProducts();
    }

    @Test
    void testAddProduct() throws Exception {
        ProductRequest request = TestUtil.populateProductRequest();
        Product product = new Product();
        when(productServiceImpl.addProduct(any(ProductRequest.class))).thenReturn(product);

        mockMvc.perform(post("/product/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isOk());

        verify(productServiceImpl, times(1)).addProduct(any(ProductRequest.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductRequest request = TestUtil.populateProductRequest();
        Product product = new Product();
        when(productServiceImpl.updateProduct(anyLong(), any(ProductRequest.class))).thenReturn(product);

        mockMvc.perform(put("/product/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isOk());

        verify(productServiceImpl, times(1)).updateProduct(anyLong(), any(ProductRequest.class));
    }
}