package com.chitfund.members.service;


import com.chitfund.members.dto.MemberRequest;
import com.chitfund.members.model.Member;

import java.util.List;

public interface MemberService {
    Member getMemberById(Long id);
    List<Member> getAllMembers();
    Member saveMember(MemberRequest request);
    Member updateMember(Long id, MemberRequest request);
    void deleteMember(Long id);
}

