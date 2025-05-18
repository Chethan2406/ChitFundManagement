package com.chitfund.members.dto;


import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MemberTransactionRequest {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Wallet balance after transaction is required")
    private BigDecimal walletBalanceAfter;

    @NotBlank(message = "Mode of payment is required")
    private String modeOfPayment;

    @NotBlank(message = "Status is required")
    private String status;
}
