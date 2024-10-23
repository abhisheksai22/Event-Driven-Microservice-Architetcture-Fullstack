package com.abhi.microservices.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetailsRequest {

    @NotNull(message = "User Name is empty")
    @NotBlank(message = "Name is blank")
    String name;
    @NotNull(message = "User email is empty")
    @Email(message = "User email is not valid")
    String email;

}
