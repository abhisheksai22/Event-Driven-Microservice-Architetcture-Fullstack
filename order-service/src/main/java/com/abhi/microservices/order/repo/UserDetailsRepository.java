package com.abhi.microservices.order.repo;

import com.abhi.microservices.order.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
