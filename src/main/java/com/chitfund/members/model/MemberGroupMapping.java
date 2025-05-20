package com.chitfund.members.model;

import java.lang.annotation.Native;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.chitfund.chitGroups.model.ChitGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_group_mapping")
public class MemberGroupMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Long mapId;
    @Column(name = "wallet_balance")
    private BigDecimal walletBalance;
    @Column(name = "winning_month")
    private Integer winningMonth;
    @Column(name = "winning_amount")
    private BigDecimal winningAmount;
    @Column(name = "is_winner")
    private Boolean isWinner;
    @Column(name = "installetment_paid")
    private Integer installmentPaid;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ChitGroup group;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
