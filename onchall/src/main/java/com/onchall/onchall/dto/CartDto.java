package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    List<CartItemDto> cartItemDtoList;
    Integer totalPrice;
}
