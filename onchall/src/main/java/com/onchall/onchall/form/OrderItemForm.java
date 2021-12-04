package com.onchall.onchall.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemForm {
    String itemName;
    Integer itemPrice;
    Long itemId;
}
