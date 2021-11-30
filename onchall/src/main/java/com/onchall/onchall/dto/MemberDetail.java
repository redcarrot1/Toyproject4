package com.onchall.onchall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDetail {
    String name;
    String email;
    Integer point;
}
