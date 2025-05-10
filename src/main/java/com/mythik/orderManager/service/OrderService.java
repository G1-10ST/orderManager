package com.mythik.orderManager.service;

import com.mythik.orderManager.model.entity.Order;
import com.mythik.orderManager.model.enums.OrderStatus;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.response.OrderResponse;

import java.util.List;

public interface OrderService {

    List<OrderResponse> fetchAllOrders();

    OrderResponse createOrder(OrderRequest req);

    OrderResponse addProduct(Long orderId, OrderItemDto dto);

    OrderResponse removeProduct(Long orderId, Long productId);

    OrderResponse updateStatus(Long orderId, OrderStatus status);
}
