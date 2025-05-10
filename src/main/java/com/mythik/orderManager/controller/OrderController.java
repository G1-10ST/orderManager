package com.mythik.orderManager.controller;

import com.mythik.orderManager.model.enums.OrderStatus;
import com.mythik.orderManager.model.request.OrderItemDto;
import com.mythik.orderManager.model.request.OrderRequest;
import com.mythik.orderManager.model.response.OrderResponse;
import com.mythik.orderManager.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponse>> fetchAllOrders() {
        return ResponseEntity
                .status(OK)
                .body(orderService.fetchAllOrders());
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponse> create(
            @RequestBody @Valid OrderRequest request) {

        OrderResponse saved = orderService.createOrder(request);

        return ResponseEntity
                .status(CREATED)
                .body(saved);
    }

    @PostMapping("/addProductToOrder")
    public ResponseEntity<OrderResponse> addProductToOrder(
            @RequestParam Long orderId,
            @Valid @RequestBody OrderItemDto dto) {
        OrderResponse updated = orderService.addProduct(orderId, dto);

        return ResponseEntity
                .status(OK)
                .body(updated);
    }

    @DeleteMapping("/deleteProductFromOrder")
    public ResponseEntity<OrderResponse> deleteProductFromOrder(
            @RequestParam Long orderId,
            @RequestParam Long productId) {
        OrderResponse updated = orderService.removeProduct(orderId, productId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<OrderResponse> updateStatus(@RequestParam @Valid OrderStatus status,
                                                      @RequestParam @Valid Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }
}
