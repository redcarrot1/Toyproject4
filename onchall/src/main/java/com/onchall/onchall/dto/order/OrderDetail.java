package com.onchall.onchall.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDetail {
    Long orderId;
    LocalDateTime orderDate;
    Integer usePoint;
    Integer totalPrice;
    String payMethod;
    List<OrderItemDetail> orderItemDetails;
}
