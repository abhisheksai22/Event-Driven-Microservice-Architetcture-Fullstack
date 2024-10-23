package com.abhi.microservices.order.service;

import com.abhi.microservices.order.dto.OrderRequest;
import com.abhi.microservices.order.dto.OrderResponse;
import com.abhi.microservices.order.event.OrderPlacedEvent;
import com.abhi.microservices.order.client.InventoryClient;
import com.abhi.microservices.order.exception.UserNotFoundException;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.model.UserDetails;
import com.abhi.microservices.order.repo.OrderRepo;
import com.abhi.microservices.order.repo.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("In create order");
        Boolean isPresent = inventoryClient.isInStock(orderRequest.getSkuCode());

        if (isPresent) {

            UserDetails userDetails = getUserDetails(orderRequest.getEmail());
            String orderNum = UUID.randomUUID().toString();
            Order order = Order.builder()
                    .quantity(orderRequest.getQuantity())
                    .price(orderRequest.getPrice())
                    .serialNumber(orderNum)
                    .skuCode(orderRequest.getSkuCode())
                    .userDetails(userDetails)
                    .build();

            //kafka event
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(orderNum, orderRequest.getEmail());
            log.info("Sending order placed event: {}", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("Kafka sent successfully");

            Order order1 = orderRepo.save(order);

            OrderResponse orderResponse = OrderResponse.builder()
                    .name(userDetails.getName())
                    .userId(userDetails.getUserId())
                    .email(userDetails.getEmail())
                    .id(order1.getId())
                    .serialNumber(order1.getSerialNumber())
                    .skuCode(order1.getSkuCode())
                    .quantity(order1.getQuantity())
                    .price(order1.getPrice())
                    .totalPrice(Double.parseDouble(String.valueOf(order1.getPrice())) * order1.getQuantity())
                    .build();

            log.info("Order Response : {}", orderResponse);
            return orderResponse;

        }
        return new OrderResponse();

    }

    public String testKafka(String email) {
        String orderNum = UUID.randomUUID().toString();
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(orderNum, email);
        log.info("Sending order placed events: {}", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        return "Order Placed successfully";
    }

    private UserDetails getUserDetails(String email) {
        Optional<UserDetails> userDetails = userDetailsRepository.findByEmail(email);
        if (userDetails.isPresent()) {
            return userDetails.get();
        } else {
            throw new UserNotFoundException(email + " is not found");
        }
    }

}
