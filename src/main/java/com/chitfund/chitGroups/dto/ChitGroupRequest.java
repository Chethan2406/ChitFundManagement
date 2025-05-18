package com.chitfund.chitGroups.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ChitGroupRequest {

    @NotNull
    private String groupName;

    private BigDecimal fundValue;

    private Integer memberCount;

    private String description;

    private LocalDate createdDate;

    private int fundCollectionDate;

    private LocalDateTime biddingStartTime;

    private Integer biddingDurationInSeconds;

    private Boolean auctionStarted = false;

    private Boolean auctionCompleted = false;

    private Long fundTypeId;
}

