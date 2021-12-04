package com.onchall.onchall.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetail {
    Long orderId;
    LocalDateTime orderDate;
    Integer usePoint;
    Integer totalPrice;
    Integer payPrice;
    String payMethod;
    List<OrderItemDetail> orderItemDetails;

    public OrderDetail(Long orderId, LocalDateTime orderDate, Integer usePoint, Integer totalPrice, String payMethod, List<OrderItemDetail> orderItemDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.usePoint = usePoint;
        this.totalPrice = totalPrice;
        this.payPrice = totalPrice-usePoint;
        this.payMethod = payMethod;
        this.orderItemDetails = orderItemDetails;
    }
}
