package com.abhi.microservices.order.repo;

import com.abhi.microservices.order.model.Order;
import com.abhi.microservices.order.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserDetailsRepo")
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByEmail(String email);
}
