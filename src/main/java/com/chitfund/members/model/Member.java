package com.chitfund.members.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "mobile_number", unique = true, length = 10, nullable = false)
    private String mobileNumber;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "aadhar_number", unique = true, length = 12, nullable = false)
    private String aadharNumber;
    @Column(name = "age", nullable = false)
    private String age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "kyc_status", nullable = false)
    private String kycStatus;
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
    @Column(name = "updated_date")
    private LocalDate updatedDate;
    @Column(name = "status", nullable = false)
    private String status;

}
