package com.mythik.orderManager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mythik.orderManager.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addProduct(Product product, int quantityToAdd) {
        var existing = orderItems.stream()
                .filter(item -> item.getProduct().getId()
                        .equals(product.getId()))
                .findFirst();

        if (existing.isPresent()) {
            OrderItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantityToAdd);
        } else {
            OrderItem item = OrderItem.builder()
                    .order(this)
                    .product(product)
                    .quantity(quantityToAdd)
                    .priceOfProduct(product.getPrice())
                    .build();

            orderItems.add(item);
            product.getOrderItems().add(item);
        }
    }

    public void removeProduct(Product product) {
        orderItems.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        product.getOrderItems().removeIf(item -> item.getOrder().id.equals(id));
    }

}
