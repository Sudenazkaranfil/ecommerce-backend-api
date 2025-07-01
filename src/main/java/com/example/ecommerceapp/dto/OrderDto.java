package com.example.ecommerceapp.dto;

import com.example.ecommerceapp.entity.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long customerId;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private String billingAddress;
    private List<OrderItemDto> orderItems;
}