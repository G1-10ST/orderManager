package com.mythik.orderManager.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "name cannot be null or blank")
    private String name;

    private String description;

    @Min(value = 0)
    @NotNull(message = "price cannot be null")
    private Integer price;
}
