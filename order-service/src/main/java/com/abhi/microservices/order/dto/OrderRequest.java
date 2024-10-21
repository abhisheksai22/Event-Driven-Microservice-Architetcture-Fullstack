package com.abhi.microservices.order.dto;

import com.abhi.microservices.order.model.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private String email ;
    private BigDecimal price;
    private Integer quantity;
    private String skuCode;

}
