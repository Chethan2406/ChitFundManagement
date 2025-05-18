package com.chitfund.members.controller;
import com.chitfund.members.dto.MemberTransactionRequest;
import com.chitfund.members.model.MemberTransaction;
import com.chitfund.members.service.MemberTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member-transactions")
public class MemberTransactionController {

    @Autowired
    private MemberTransactionService transactionService;

    @PostMapping
    public ResponseEntity<MemberTransaction> createTransaction(@Valid @RequestBody MemberTransactionRequest request) {
        MemberTransaction transaction = transactionService.createTransaction(request);
        return ResponseEntity.ok(transaction);
    }
}

