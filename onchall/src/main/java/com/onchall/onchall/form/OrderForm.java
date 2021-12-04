package com.onchall.onchall.form;

import lombok.Data;

import java.util.List;

@Data
public class OrderForm {
    Long memberId;
    String memberName;
    String memberEmail;
    Integer memberPoint;
    Integer totalOrderPrice;
    String payMethod;
    Integer usePoint;
    List<OrderItemForm> orderItemFormList;
}
