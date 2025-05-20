package com.chitfund.chitGroups.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.chitfund.members.model.MemberGroupMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chit_group")
public class ChitGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name", unique = true, nullable = false)
    private String groupName;
    @Column(name = "fund_value")
    private BigDecimal fundValue;
    @Column(name = "installment_amount")
    private BigDecimal installmentAmount;
    @Column(name = "member_count")
    private Integer memberCount;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "fund_collection_date")
    private int fundCollectionDate;
    @Column(name = "bidding_start_time")
    private LocalDateTime biddingStartTime;
    @Column(name = "bidding_duration_inSeconds")
    private Integer biddingDurationInSeconds;
    @Column(name = "auction_started")
    @Builder.Default
    private Boolean auctionStarted = false;
    @Column(name = "auction_completed")
    @Builder.Default
    private Boolean auctionCompleted = false;
    @Enumerated(EnumType.STRING)
    @Column(name = "installment_cycle")
    private InstallmentCycle installmentCycle;
    @ManyToOne
    @JoinColumn(name = "fund_type_id")
    private ChitFundType fundType;
    @OneToMany(mappedBy = "group")
    private List<MemberGroupMapping> members;

}
