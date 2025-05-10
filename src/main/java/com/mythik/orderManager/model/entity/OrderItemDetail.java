package com.mythik.orderManager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDetail {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer pricePerItem;
    private Integer lineTotal;
}
