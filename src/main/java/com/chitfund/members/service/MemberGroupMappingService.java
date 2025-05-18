package com.chitfund.members.service;


import com.chitfund.members.dto.MemberGroupMappingRequest;
import com.chitfund.members.model.MemberGroupMapping;

public interface MemberGroupMappingService {
    MemberGroupMapping mapMemberToGroup(MemberGroupMappingRequest request);
}
