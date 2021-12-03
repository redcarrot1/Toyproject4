package com.onchall.onchall.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDetail {
    Long orderItemId;
    Long itemId;
    String name;
    Integer price;
    Boolean isComment;
}
