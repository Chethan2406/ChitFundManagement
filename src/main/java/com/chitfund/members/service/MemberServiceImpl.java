package com.chitfund.members.service;


import com.chitfund.members.dto.MemberRequest;
import com.chitfund.members.model.Member;
import com.chitfund.members.repository.MemberRepository;
import com.chitfund.util.exceptions.MemberException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    public MemberServiceImpl(MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Member getMemberById(Long id) {
        try {
            return memberRepository.findById(id)
                    .orElseThrow(() -> new MemberException("Member not found with ID: " + id));
        } catch (Exception e) {
            log.error("CFM_MEM_001 - Error fetching member by ID", e);
            throw new MemberException("Unable to fetch member with ID: " + id, e);
        }
    }

    @Override
    public List<Member> getAllMembers() {
        try {
            List<Member> members = memberRepository.findAll();
            if (members.isEmpty()) {
                throw new MemberException("No members found");
            }
            return members;
        } catch (Exception e) {
            log.error("CFM_MEM_002 - Error fetching all members", e);
            throw new MemberException("Unable to fetch all members", e);
        }
    }

    @Override
    public Member saveMember(MemberRequest request) {
        try {
            Member member = objectMapper.convertValue(request, Member.class);
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            log.error("CFM_MEM_003 - Data integrity violation while saving member", e);

            String cause = Optional.ofNullable(e.getRootCause())
                    .map(Throwable::getMessage).orElse("");

            if (cause.contains("uk_") || cause.contains("Duplicate")) {
                throw new MemberException("A member with the same email, mobile, or Aadhar already exists.");
            }

            throw new MemberException("Unable to save member due to data integrity violation");
        } catch (Exception e) {
            log.error("CFM_MEM_003 - Error saving member", e);
            throw new MemberException("Unable to save member", e);
        }
    }

    @Override
    public Member updateMember(Long id, MemberRequest request) {
        try {
            Member existing = getMemberById(id);

            existing.setFullName(request.getFullName());
            existing.setMobileNumber(request.getMobileNumber());
            existing.setEmail(request.getEmail());
            existing.setAadharNumber(request.getAadharNumber());
            existing.setAge(request.getAge());
            existing.setGender(request.getGender());
            existing.setKycStatus(request.getKycStatus());
            existing.setCreatedDate(request.getCreatedDate());
            existing.setUpdatedDate(request.getUpdatedDate());
            existing.setStatus(request.getStatus());

            return memberRepository.save(existing);
        } catch (Exception e) {
            log.error("CFM_MEM_004 - Error updating member", e);
            throw new MemberException("Unable to update member with ID: " + id, e);
        }
    }

    @Override
    public void deleteMember(Long id) {
        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            log.error("CFM_MEM_005 - Error deleting member", e);
            throw new MemberException("Unable to delete member with ID: " + id, e);
        }
    }
}

