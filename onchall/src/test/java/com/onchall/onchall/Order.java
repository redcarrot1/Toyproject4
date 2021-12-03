package com.onchall.onchall;

import java.util.List;

public class Order {
    int orderNumber;
    List<OrderItem> orderItems;

    public Order(int orderNumber, List<OrderItem> orderItems) {
        this.orderNumber = orderNumber;
        this.orderItems = orderItems;
    }
}
