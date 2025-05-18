package com.chitfund.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chitfund.chitGroups.model.ChitGroup;
import com.chitfund.chitGroups.repository.ChitGroupRepository;
import com.chitfund.members.dto.MemberTransactionRequest;
import com.chitfund.members.model.Member;
import com.chitfund.members.model.MemberTransaction;
import com.chitfund.members.repository.MemberRepository;
import com.chitfund.members.repository.MemberTransactionRepository;
import com.chitfund.util.exceptions.ResourceNotFoundException;

@Service
public class MemberTransactionServiceImpl implements MemberTransactionService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChitGroupRepository chitGroupRepository;

    @Autowired
    private MemberTransactionRepository transactionRepository;

    @Override
    public MemberTransaction createTransaction(MemberTransactionRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + request.getMemberId()));

        ChitGroup group = chitGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + request.getGroupId()));

        MemberTransaction txn = MemberTransaction.builder()
                .member(member)
                .group(group)
                .amount(request.getAmount())
                .walletBalanceAfter(request.getWalletBalanceAfter())
                .modeOfPayment(request.getModeOfPayment())
                .status(request.getStatus())
                .build();

        return transactionRepository.save(txn);
    }
}
