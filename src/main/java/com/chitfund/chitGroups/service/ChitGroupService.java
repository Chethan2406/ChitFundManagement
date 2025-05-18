package com.chitfund.chitGroups.service;

import java.util.List;

import com.chitfund.chitGroups.dto.ChitGroupRequest;
import com.chitfund.chitGroups.model.ChitGroup;

public interface ChitGroupService {

    ChitGroup saveChitGroup(ChitGroupRequest request);

    ChitGroup getChitGroupById(Long id);

    List<ChitGroup> getAllChitGroups();

    ChitGroup updateChitGroup(Long id, ChitGroupRequest request);

    void deleteChitGroup(Long id);

}
