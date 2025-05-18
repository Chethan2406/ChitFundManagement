package com.chitfund.members.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.chitfund.members.model.MemberGroupMapping;

public interface MemberGroupMappingRepository extends JpaRepository<MemberGroupMapping, Long> {
}
