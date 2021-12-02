package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardItemDto {
    String name;
    String storeImageName;
    Integer originPrice;
    Integer price;
}
