package com.onchall.onchall;

import lombok.Data;

@Data
public class SessionData {
    private Long memberId;

    public SessionData(Long memberId) {
        this.memberId = memberId;
    }
}
