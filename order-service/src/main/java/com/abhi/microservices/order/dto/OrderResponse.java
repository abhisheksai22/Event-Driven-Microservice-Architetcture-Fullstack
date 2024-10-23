package com.abhi.microservices.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String name;
    private String email;
    private Long userId;
    private Long id;
    private String serialNumber;
    private BigDecimal price;
    private Integer quantity;
    private String skuCode;
    private Double totalPrice;
}
