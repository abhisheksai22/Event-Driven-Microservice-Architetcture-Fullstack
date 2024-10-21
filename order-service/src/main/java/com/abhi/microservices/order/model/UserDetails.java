package com.abhi.microservices.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails")
    private List<Order> orderList;
}
