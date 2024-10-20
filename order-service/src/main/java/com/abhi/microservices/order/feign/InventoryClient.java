package com.abhi.microservices.order.feign;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {
    @GetExchange("api/inventory")
    Boolean isInStock(@RequestParam("skuCode") String skuCode);
}

