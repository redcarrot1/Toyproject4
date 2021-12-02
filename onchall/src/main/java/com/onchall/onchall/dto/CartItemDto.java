package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
    String itemName;
    Integer price;
    Long itemId;
}
