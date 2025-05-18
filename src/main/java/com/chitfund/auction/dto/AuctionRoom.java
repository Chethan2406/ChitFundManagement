package com.chitfund.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionRoom {
    private Long groupId;
    private LocalDateTime endTime;
    private Map<Long, BigDecimal> bids = new ConcurrentHashMap<>(); // memberId -> bid amount
}
