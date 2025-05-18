package com.chitfund.chitGroups.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chitfund.chitGroups.model.ChitFundType;

@Repository
public interface ChitFundTypeRepository extends JpaRepository<ChitFundType, Long> {

}
