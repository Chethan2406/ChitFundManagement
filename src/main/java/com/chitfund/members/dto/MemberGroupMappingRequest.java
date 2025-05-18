package com.chitfund.members.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MemberGroupMappingRequest {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Wallet balance cannot be negative")
    private BigDecimal walletBalance;

    private Integer winningMonth;

    private BigDecimal winningAmount;

    private Boolean isWinner = false;

    @Min(value = 0, message = "Installments paid cannot be negative")
    private Integer installmentPaid = 0;

    @NotBlank(message = "Status is required")
    private String status;
}
