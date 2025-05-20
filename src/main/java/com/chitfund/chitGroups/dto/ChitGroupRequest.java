package com.chitfund.chitGroups.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.chitfund.chitGroups.model.InstallmentCycle;

import lombok.Data;

@Data
public class ChitGroupRequest {

    @NotNull
    private String groupName;
    @NotNull
    private BigDecimal fundValue;
    @NotNull
    private Integer memberCount;
    @NotNull
    private String description;
    @NotNull
    private int fundCollectionDate;
    @NotNull
    private LocalDateTime biddingStartTime;
    @NotNull
    private Integer biddingDurationInSeconds;
    @NotNull
    private Long fundTypeId;
    @NotNull
    private InstallmentCycle installmentCycle;
}
