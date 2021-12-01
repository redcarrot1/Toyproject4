package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PointDetail {
    String detail;
    LocalDateTime dateTime;
    Integer point;
}
