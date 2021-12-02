package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Pagination {
    Integer start;
    Integer end;
    boolean isFirst;
    boolean isEnd;
    Integer cntPage;
    Integer totalPage;

    List<BoardItemDto> itemList;
}
