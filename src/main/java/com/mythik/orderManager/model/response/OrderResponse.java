package com.mythik.orderManager.model.response;

import com.mythik.orderManager.model.entity.OrderItemDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDetail> items;

    public Integer getGrandTotal() {
        return items.stream()
                .map(OrderItemDetail::getLineTotal)
                .reduce(0, Integer::sum);
    }
}
