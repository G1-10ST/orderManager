package com.mythik.orderManager.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class OrderRequest {

    @Valid
    @NotNull(message = "items cannot be null")
    private List<OrderItemDto> items;
}
