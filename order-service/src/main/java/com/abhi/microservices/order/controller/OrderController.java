package com.abhi.microservices.order.controller;

import com.abhi.microservices.order.dto.OrderRequest;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest){
        log.info("orderRequest : {}", orderRequest.getSkuCode());
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.OK);
    }


}
