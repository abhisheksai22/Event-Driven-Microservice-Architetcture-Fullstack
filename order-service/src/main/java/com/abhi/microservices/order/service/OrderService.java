package com.abhi.microservices.order.service;

import com.abhi.microservices.order.dto.OrderRequest;
import com.abhi.microservices.order.event.OrderPlacedEvent;
import com.abhi.microservices.order.feign.InventoryClient;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public Order createOrder(OrderRequest orderRequest) {
       Boolean ispresent = inventoryClient.isInStock(orderRequest.getSkuCode());

        if (ispresent) {
            String orderNum = UUID.randomUUID().toString();
            Order order = Order.builder()
                    .quantity(orderRequest.getQuantity())
                    .price(orderRequest.getPrice())
                    .serialNumber(orderNum)
                    .skuCode(orderRequest.getSkuCode())
                    .build();

            //kafka event
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(orderNum, orderRequest.getEmail());
            log.info("Sending order placed event: {}", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("Kafka sent successfully");

            return orderRepo.save(order);
        }
        return new Order();

    }

    public String testKafka(String email) {
        String orderNum = UUID.randomUUID().toString();
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(orderNum, email);
        log.info("Sending order placed events: {}", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        return "Order Placed successfully";
    }
}
