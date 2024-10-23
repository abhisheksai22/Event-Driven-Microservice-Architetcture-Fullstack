package com.abhi.microservices.order.controller;

import com.abhi.microservices.order.dto.OrderDto;
import com.abhi.microservices.order.dto.UserDetailsRequest;
import com.abhi.microservices.order.dto.UserResponse;
import com.abhi.microservices.order.exception.EmailAlreadyPresentException;
import com.abhi.microservices.order.model.UserDetails;
import com.abhi.microservices.order.repo.UserDetailsRepository;
import com.abhi.microservices.order.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserDetailsRequest userDetailsRequest)
            throws EmailAlreadyPresentException {
            return new ResponseEntity<>(userService.createUser(userDetailsRequest), HttpStatus.OK);
    }

    @GetMapping
    public List<UserDetails> getAllUserDetails(){
        return userDetailsRepository.findAll();
    }

    @GetMapping("/list-of-orders/{email}")
    public List<OrderDto> getAllOrdersForAUser(@PathVariable String email){
        return userService.getAllOrdersForAUser(email);
    }
}
