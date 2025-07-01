package com.example.ecommerceapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotBlank(message = "Shipping address cannot be empty")
    private String shippingAddress;

    @NotBlank(message = "Billing address cannot be empty")
    private String billingAddress;
}