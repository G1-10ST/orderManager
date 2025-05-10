package com.mythik.orderManager.service.impl;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import com.mythik.orderManager.model.entity.*;
import com.mythik.orderManager.model.enums.OrderStatus;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.response.OrderResponse;
import com.mythik.orderManager.repository.OrderItemRepository;
import com.mythik.orderManager.repository.OrderRepository;
import com.mythik.orderManager.repository.ProductRepository;
import com.mythik.orderManager.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final OrderItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> fetchAllOrders() {

        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Order order = Order.builder()
                .status(OrderStatus.DRAFT)
                .build();


        for (var item : request.getItems()) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            order.addProduct(product, item.getQuantity());
        }

        order = orderRepo.save(order);
        return buildOrderResponse(order);
    }

    @Override
    public OrderResponse addProduct(Long orderId, OrderItemDto dto) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + dto.getProductId()));

        OrderItemId key = new OrderItemId(orderId, product.getId());
        Optional<OrderItem> existing = itemRepository.findById(key);

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + dto.getQuantity());
        } else {
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .priceOfProduct(product.getPrice())
                    .build();
            order.getOrderItems().add(item);
        }

        Order saved = orderRepo.save(order);
        return buildOrderResponse(saved);
    }

    public OrderResponse removeProduct(Long orderId, Long productId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        order.removeProduct(product);
        Order updated = orderRepo.save(order);

        return buildOrderResponse(updated);
    }

    @Override
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        order.setStatus(status);
        Order updated = orderRepo.save(order);

        return buildOrderResponse(updated);
    }

    private OrderResponse buildOrderResponse(Order order) {
        List<OrderItemDetail> itemDetails = order.getOrderItems().stream()
                .map(item -> {
                    int itemPrice = item.getPriceOfProduct();
                    int quantity = item.getQuantity();
                    int lineTotal = itemPrice * quantity;

                    return OrderItemDetail.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .quantity(quantity)
                            .pricePerItem(itemPrice)
                            .lineTotal(lineTotal)
                            .build();
                })
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .items(itemDetails)
                .build();
    }
}
