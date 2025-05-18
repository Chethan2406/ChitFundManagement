package com.chitfund.members.service;


import com.chitfund.members.dto.MemberTransactionRequest;
import com.chitfund.members.model.MemberTransaction;

public interface MemberTransactionService {
    MemberTransaction createTransaction(MemberTransactionRequest request);
}

