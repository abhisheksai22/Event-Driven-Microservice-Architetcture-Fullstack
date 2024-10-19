package com.abhi.microservices.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OrderConfigurations {

    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

}
