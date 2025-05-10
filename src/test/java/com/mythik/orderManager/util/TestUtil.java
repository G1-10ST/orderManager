package com.mythik.orderManager.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.request.ProductRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestUtil {
    public static HttpSecurity createHttpSecurity() throws Exception {
        ObjectPostProcessor<Object> objectPostProcessor = mock(ObjectPostProcessor.class);
        AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
        HashMap<Class<?>, Object> sharedObjects = new HashMap<>();

        return new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects);
    }

    public static OrderItemDto populateOrderItemDto() {
        return OrderItemDto.builder()
                .productId(1L)
                .quantity(2)
                .build();
    }

    public static OrderRequest populateOrderRequest() {
        return OrderRequest.builder()
                .items(List.of(populateOrderItemDto()))
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }

    public static ProductRequest populateProductRequest() {
        return ProductRequest.builder()
                .name("Test Product")
                .description("Test Description")
                .price(100)
                .build();
    }
}
