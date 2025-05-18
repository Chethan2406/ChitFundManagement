package com.chitfund.auction.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BidRequest {
    private Long groupId;
    private Long memberId;
    private BigDecimal amount;
}
