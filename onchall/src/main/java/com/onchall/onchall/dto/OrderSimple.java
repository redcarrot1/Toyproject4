package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimple {
    String repreItemName;
    Integer orderItemCount;
    LocalDateTime orderDate;
    Integer totalPrice;
}
