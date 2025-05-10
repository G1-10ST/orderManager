package com.mythik.orderManager.service.impl;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import com.mythik.orderManager.model.entity.Order;
import com.mythik.orderManager.model.entity.OrderItemId;
import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.enums.OrderStatus;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.response.OrderResponse;
import com.mythik.orderManager.repository.OrderItemRepository;
import com.mythik.orderManager.repository.OrderRepository;
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

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private ProductRepository productRepo;

    @Mock
    private OrderItemRepository itemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAllOrders() {
        Order order = new Order();
        order.setStatus(OrderStatus.DRAFT);
        when(orderRepo.findAll()).thenReturn(List.of(order));

        List<OrderResponse> responses = orderService.fetchAllOrders();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void testCreateOrder() {
        OrderRequest request = TestUtil.populateOrderRequest();

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);

        when(productRepo.findById(anyLong())).thenReturn(Optional.of(product));
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response);
        verify(productRepo, times(1)).findById(1L);
        verify(orderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrder_ProductNotFound() {
        OrderRequest request = TestUtil.populateOrderRequest();

        when(productRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(request));
        verify(productRepo, times(1)).findById(1L);
    }

    @Test
    void testAddProduct() {
        Order order = new Order();
        order.setStatus(OrderStatus.DRAFT);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);

        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(1L);
        dto.setQuantity(2);

        when(orderRepo.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepo.findById(anyLong())).thenReturn(Optional.of(product));
        when(itemRepository.findById(any(OrderItemId.class))).thenReturn(Optional.empty());
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.addProduct(1L, dto);

        assertNotNull(response);
        verify(orderRepo, times(1)).findById(1L);
        verify(productRepo, times(1)).findById(1L);
        verify(orderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void testAddProduct_OrderNotFound() {
        when(orderRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.addProduct(1L, new OrderItemDto()));
        verify(orderRepo, times(1)).findById(1L);
    }

    @Test
    void testRemoveProduct() {
        Order order = new Order();
        Product product = new Product();
        product.setId(1L);
        order.setStatus(OrderStatus.DRAFT);

        when(orderRepo.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepo.findById(anyLong())).thenReturn(Optional.of(product));
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.removeProduct(1L, 1L);

        assertNotNull(response);
        verify(orderRepo, times(1)).findById(1L);
        verify(productRepo, times(1)).findById(1L);
        verify(orderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void testRemoveProduct_OrderNotFound() {
        when(orderRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.removeProduct(1L, 1L));
        verify(orderRepo, times(1)).findById(1L);
    }

    @Test
    void testUpdateStatus() {
        Order order = new Order();
        when(orderRepo.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.updateStatus(1L, OrderStatus.COMPLETED);

        assertNotNull(response);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        verify(orderRepo, times(1)).findById(1L);
        verify(orderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatus_OrderNotFound() {
        when(orderRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateStatus(1L, OrderStatus.COMPLETED));
        verify(orderRepo, times(1)).findById(1L);
    }
}