package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Download {
    String itemName;
    LocalDateTime expireDate;
    String storeName;
    Long downloadFileId;
}
