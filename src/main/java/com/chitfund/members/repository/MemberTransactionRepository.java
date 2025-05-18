package com.chitfund.members.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.chitfund.members.model.MemberTransaction;

public interface MemberTransactionRepository extends JpaRepository<MemberTransaction, Long> {
}

