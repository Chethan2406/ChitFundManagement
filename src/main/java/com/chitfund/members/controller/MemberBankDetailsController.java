package com.chitfund.members.controller;

import com.chitfund.members.dto.MemberBankDetailsRequest;
import com.chitfund.members.model.MemberBankDetails;
import com.chitfund.members.service.MemberBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/member-bank-details")
public class MemberBankDetailsController {

    private final MemberBankDetailsService bankDetailsService;

    public MemberBankDetailsController(MemberBankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping
    public ResponseEntity<MemberBankDetails> addBankDetails(@Valid @RequestBody MemberBankDetailsRequest request) {
        MemberBankDetails savedDetails = bankDetailsService.saveBankDetails(request);
        return ResponseEntity.ok(savedDetails);
    }
}
