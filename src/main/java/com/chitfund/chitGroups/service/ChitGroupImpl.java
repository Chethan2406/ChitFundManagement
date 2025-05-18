package com.chitfund.chitGroups.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.chitfund.chitGroups.dto.ChitGroupRequest;
import com.chitfund.chitGroups.model.ChitFundType;
import com.chitfund.chitGroups.model.ChitGroup;
import com.chitfund.chitGroups.repository.ChitFundTypeRepository;
import com.chitfund.chitGroups.repository.ChitGroupRepository;
import com.chitfund.util.exceptions.ChitGroupException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChitGroupImpl implements ChitGroupService {

    private final ChitFundTypeRepository chitFundTypeRepository;
    private final ChitGroupRepository chitGroupRepository;

    public ChitGroupImpl(ChitFundTypeRepository chitFundTypeRepository, ChitGroupRepository chitGroupRepository) {
        this.chitFundTypeRepository = chitFundTypeRepository;
        this.chitGroupRepository = chitGroupRepository;
    }

    public ChitGroup saveChitGroup(ChitGroupRequest request) {
        try {
            ChitFundType fundType = chitFundTypeRepository.findById(request.getFundTypeId())
                    .orElseThrow(
                            () -> new ChitGroupException("Fund type not found with id: " + request.getFundTypeId()));

            ChitGroup group = ChitGroup.builder()
                    .groupName(request.getGroupName())
                    .fundValue(request.getFundValue())
                    .memberCount(request.getMemberCount())
                    .description(request.getDescription())
                    .createdDate(request.getCreatedDate())
                    .fundCollectionDate(request.getFundCollectionDate())
                    .biddingStartTime(request.getBiddingStartTime())
                    .biddingDurationInSeconds(request.getBiddingDurationInSeconds())
                    .auctionStarted(request.getAuctionStarted())
                    .auctionCompleted(request.getAuctionCompleted())
                    .fundType(fundType)
                    .build();

            return chitGroupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            log.error("CFM_CGI_001 - Data integrity violation while saving chit group", e);
            throw new ChitGroupException("Duplicate entry or data integrity issue while saving chit group.", e);
        } catch (Exception e) {
            log.error("CFM_CGI_002 - Error saving chit group", e);
            throw new ChitGroupException("Unable to save chit group", e);
        }
    }

    public ChitGroup getChitGroupById(Long id) {
        try {
            return chitGroupRepository.findById(id)
                    .orElseThrow(() -> new ChitGroupException("ChitGroup not found with id: " + id));
        } catch (Exception e) {
            log.error("CFM_CGI_003 - Error fetching chit group by ID", e);
            throw new ChitGroupException("Unable to fetch chit group with id: " + id, e);
        }
    }

    public List<ChitGroup> getAllChitGroups() {
        try {
            List<ChitGroup> groups = chitGroupRepository.findAll();
            if (groups.isEmpty()) {
                throw new ChitGroupException("No chit groups found.");
            }
            return groups;
        } catch (Exception e) {
            log.error("CFM_CGI_004 - Error fetching all chit groups", e);
            throw new ChitGroupException("Unable to fetch chit groups", e);
        }
    }

    public ChitGroup updateChitGroup(Long id, ChitGroupRequest request) {
        try {
            ChitGroup existing = getChitGroupById(id);

            existing.setGroupName(request.getGroupName());
            existing.setFundValue(request.getFundValue());
            existing.setMemberCount(request.getMemberCount());
            existing.setDescription(request.getDescription());
            existing.setCreatedDate(request.getCreatedDate());
            existing.setFundCollectionDate(request.getFundCollectionDate());
            existing.setBiddingStartTime(request.getBiddingStartTime());
            existing.setBiddingDurationInSeconds(request.getBiddingDurationInSeconds());
            existing.setAuctionStarted(request.getAuctionStarted());
            existing.setAuctionCompleted(request.getAuctionCompleted());

            if (request.getFundTypeId() != null) {
                ChitFundType fundType = chitFundTypeRepository.findById(request.getFundTypeId())
                        .orElseThrow(() -> new ChitGroupException(
                                "Fund type not found with id: " + request.getFundTypeId()));
                existing.setFundType(fundType);
            }

            return chitGroupRepository.save(existing);
        } catch (Exception e) {
            log.error("CFM_CGI_005 - Error updating chit group", e);
            throw new ChitGroupException("Unable to update chit group with id: " + id, e);
        }
    }

    public void deleteChitGroup(Long id) {
        try {
            chitGroupRepository.deleteById(id);
        } catch (Exception e) {
            log.error("CFM_CGI_006 - Error deleting chit group", e);
            throw new ChitGroupException("Unable to delete chit group with id: " + id, e);
        }
    }
}
