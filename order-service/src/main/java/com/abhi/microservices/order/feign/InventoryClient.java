package com.abhi.microservices.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "http://localhost:9003")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory", params = "skuCode")
    Boolean isInStock(@RequestParam("skuCode") String skuCode);
}

