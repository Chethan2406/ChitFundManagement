package com.chitfund.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chitfund.members.dto.MemberBankDetailsRequest;
import com.chitfund.members.model.Member;
import com.chitfund.members.model.MemberBankDetails;
import com.chitfund.members.repository.MemberBankDetailsRepository;
import com.chitfund.members.repository.MemberRepository;
import com.chitfund.util.exceptions.ResourceNotFoundException;

@Service
public class MemberBankDetailsServiceImpl implements MemberBankDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberBankDetailsRepository bankDetailsRepository;

    @Override
    public MemberBankDetails saveBankDetails(MemberBankDetailsRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + request.getMemberId()));

        MemberBankDetails bankDetails = MemberBankDetails.builder()
                .accountNumber(request.getAccountNumber())
                .ifscCode(request.getIfscCode())
                .bankName(request.getBankName())
                .accountHolderName(request.getAccountHolderName())
                .member(member)
                .build();

        return bankDetailsRepository.save(bankDetails);
    }
}
