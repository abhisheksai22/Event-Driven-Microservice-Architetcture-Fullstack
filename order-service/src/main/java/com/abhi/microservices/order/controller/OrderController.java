package com.abhi.microservices.order.controller;

import com.abhi.microservices.order.dto.OrderRequest;
import com.abhi.microservices.order.dto.OrderResponse;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.repo.OrderRepo;
import com.abhi.microservices.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/order")
@Slf4j
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepo orderRepo;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        log.info("orderRequest : {}", orderRequest.getSkuCode());
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.OK);
    }

    @GetMapping("kafka")
    public String testKafka(@RequestParam("email") String email){
        return orderService.testKafka(email);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
}
