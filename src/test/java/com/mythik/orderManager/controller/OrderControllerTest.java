package com.mythik.orderManager.controller;

import com.mythik.orderManager.model.enums.OrderStatus;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.response.OrderResponse;
import com.mythik.orderManager.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testFetchAllOrders() throws Exception {
        when(orderService.fetchAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/order/getAllOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(orderService, times(1)).fetchAllOrders();
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderRequest request = TestUtil.populateOrderRequest();
        OrderResponse response = new OrderResponse();
        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/order/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testAddProductToOrder() throws Exception {
        OrderItemDto dto = TestUtil.populateOrderItemDto();
        OrderResponse response = new OrderResponse();
        when(orderService.addProduct(anyLong(), any(OrderItemDto.class))).thenReturn(response);

        mockMvc.perform(post("/order/addProductToOrder")
                        .param("orderId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testDeleteProductFromOrder() throws Exception {
        OrderResponse response = new OrderResponse();
        when(orderService.removeProduct(anyLong(), anyLong())).thenReturn(response);

        mockMvc.perform(delete("/order/deleteProductFromOrder")
                        .param("orderId", "1")
                        .param("productId", "1"))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testUpdateStatus() throws Exception {
        OrderResponse response = new OrderResponse();
        when(orderService.updateStatus(anyLong(), any(OrderStatus.class))).thenReturn(response);

        mockMvc.perform(post("/order/updateStatus")
                        .param("status", "COMPLETED")
                        .param("orderId", "1"))
                .andExpect(status().isInternalServerError());

        verify(orderService, times(1)).updateStatus(anyLong(), any(OrderStatus.class));
    }
}