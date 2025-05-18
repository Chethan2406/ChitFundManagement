package com.chitfund.chitGroups.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chitfund.chitGroups.model.ChitGroup;

@Repository
public interface ChitGroupRepository extends JpaRepository<ChitGroup, Long> {

    // List<ChitGroup> findUpcomingBiddings(LocalDateTime now);

}
