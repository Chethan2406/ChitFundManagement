package com.chitfund.auction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidResponse {
    private String message;
    private BigDecimal latestBid;
}
