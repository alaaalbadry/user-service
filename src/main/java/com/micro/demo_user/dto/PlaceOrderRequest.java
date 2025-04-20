package com.micro.demo_user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PlaceOrderRequest {
    private String productId;
    private int quantity;
    private double amount;

    // Getters and setters
}
