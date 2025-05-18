package com.chitfund.members.controller;

import com.chitfund.members.dto.MemberGroupMappingRequest;
import com.chitfund.members.model.MemberGroupMapping;
import com.chitfund.members.service.MemberGroupMappingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member-group-mapping")
public class MemberGroupMappingController {

    @Autowired
    private MemberGroupMappingService mappingService;

    @PostMapping
    public ResponseEntity<MemberGroupMapping> mapMemberToGroup(@Valid @RequestBody MemberGroupMappingRequest request) {
        MemberGroupMapping mapping = mappingService.mapMemberToGroup(request);
        return ResponseEntity.ok(mapping);
    }
}
