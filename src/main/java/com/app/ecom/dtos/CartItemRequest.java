package com.app.ecom.dtos;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long productId;
    private Integer quantity;
}
