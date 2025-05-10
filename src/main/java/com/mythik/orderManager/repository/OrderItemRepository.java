package com.mythik.orderManager.repository;

import com.mythik.orderManager.model.entity.OrderItem;
import com.mythik.orderManager.model.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
