package com.abhi.microservices.order.service;

import com.abhi.microservices.order.dto.OrderDto;
import com.abhi.microservices.order.dto.UserDetailsRequest;
import com.abhi.microservices.order.dto.UserResponse;
import com.abhi.microservices.order.exception.EmailAlreadyPresentException;
import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.model.UserDetails;
import com.abhi.microservices.order.repo.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserResponse createUser(UserDetailsRequest userDetailsRequest) throws EmailAlreadyPresentException{
        if(userDetailsRepository.findByEmail(userDetailsRequest.getEmail()).isPresent()){
            throw new EmailAlreadyPresentException(userDetailsRequest.getEmail() + " is Email Already for present");
        };
        UserDetails userDetails = userDetailsRepository.save(UserDetails.builder()
                .name(userDetailsRequest.getName())
                .email(userDetailsRequest.getEmail())
                .build());

        UserResponse userResponse = UserResponse.builder()
                .uid(userDetails.getUserId())
                .email(userDetailsRequest.getEmail())
                .name(userDetailsRequest.getName())
                .build();

        return userResponse;

    }

    public List<OrderDto> getAllOrdersForAUser(String email) {
        UserDetails userDetails = userDetailsRepository.findByEmail(email).get();

        return mapToOrderDto(userDetails);

    }

    private List<OrderDto> mapToOrderDto(UserDetails userDetails) {
        return userDetails.getOrderList().stream().map(
                order -> {
                    OrderDto orderDto = new OrderDto();
                    orderDto.setId(order.getId());
                    orderDto.setSerialNumber(order.getSerialNumber());
                    orderDto.setQuantity(order.getQuantity());
                    orderDto.setSkuCode(order.getSkuCode());
                    orderDto.setPrice(order.getPrice());
                    return orderDto;
                }
        ).collect(Collectors.toList());

    };
}
