package com.chitfund.members.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_bank_details")
public class MemberBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;
    @Column(name = "account_number", unique = true)
    private String accountNumber;
    @Column(name = "ifsc_code", unique = true)
    private String ifscCode;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
