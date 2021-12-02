package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDetail {
    Long itemId;
    String name;
    Integer price;
    Integer originPrice;
    String description;
    List<CommentDto> comments;
    String storeImageName;
    Long category;
    String categoryName;
}
