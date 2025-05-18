package com.chitfund.members.service;

import com.chitfund.members.dto.MemberBankDetailsRequest;
import com.chitfund.members.model.MemberBankDetails;

public interface MemberBankDetailsService {
    MemberBankDetails saveBankDetails(MemberBankDetailsRequest request);
}
