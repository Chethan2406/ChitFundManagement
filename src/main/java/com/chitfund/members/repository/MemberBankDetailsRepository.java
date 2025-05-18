package com.chitfund.members.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chitfund.members.model.MemberBankDetails;

@Repository
public interface MemberBankDetailsRepository extends JpaRepository<MemberBankDetails, Long> {
}
