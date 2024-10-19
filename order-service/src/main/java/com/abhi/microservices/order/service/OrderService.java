package com.abhi.microservices.order.service;

import com.abhi.microservices.order.dto.OrderRequest;
import com.abhi.microservices.order.feign.InventoryClient;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Order createOrder(OrderRequest orderRequest) {
//        Boolean ispresent = inventoryClient.isInStock(orderRequest.getSkuCode());

        WebClient webClient = webClientBuilder.build();

        Boolean ispresent = webClient.get()
                .uri("http://localhost:9003/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", orderRequest.getSkuCode()).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (ispresent) {
            Order order = Order.builder()
                    .quantity(orderRequest.getQuantity())
                    .price(orderRequest.getPrice())
                    .serialNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.getSkuCode())
                    .build();

            return orderRepo.save(order);
        }
        return new Order();

    }
}
