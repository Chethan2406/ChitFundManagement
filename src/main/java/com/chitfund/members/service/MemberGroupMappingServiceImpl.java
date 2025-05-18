package com.chitfund.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chitfund.chitGroups.model.ChitGroup;
import com.chitfund.chitGroups.repository.ChitGroupRepository;
import com.chitfund.members.dto.MemberGroupMappingRequest;
import com.chitfund.members.model.Member;
import com.chitfund.members.model.MemberGroupMapping;
import com.chitfund.members.repository.MemberGroupMappingRepository;
import com.chitfund.members.repository.MemberRepository;
import com.chitfund.util.exceptions.MemberGroupException;
import com.chitfund.util.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberGroupMappingServiceImpl implements MemberGroupMappingService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChitGroupRepository chitGroupRepository;

    @Autowired
    private MemberGroupMappingRepository mappingRepository;

    @Override
    public MemberGroupMapping mapMemberToGroup(MemberGroupMappingRequest request) {
        try {
            Member member = memberRepository.findById(request.getMemberId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Member not found with ID: " + request.getMemberId()));

            ChitGroup group = chitGroupRepository.findById(request.getGroupId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Group not found with ID: " + request.getGroupId()));

            MemberGroupMapping mapping = MemberGroupMapping.builder()
                    .member(member)
                    .group(group)
                    .walletBalance(request.getWalletBalance())
                    .winningMonth(request.getWinningMonth())
                    .winningAmount(request.getWinningAmount())
                    .isWinner(request.getIsWinner())
                    .installmentPaid(request.getInstallmentPaid())
                    .status(request.getStatus())
                    .build();

            return mappingRepository.save(mapping);
        } catch (Exception e) {
            throw new MemberGroupException("Unable to fetch member with ID: ", e);
        }
    }
}
