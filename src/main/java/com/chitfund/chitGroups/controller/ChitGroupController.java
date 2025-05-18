package com.chitfund.chitGroups.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chitfund.chitGroups.dto.ChitGroupRequest;
import com.chitfund.chitGroups.model.ChitGroup;
import com.chitfund.chitGroups.service.ChitGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

@Slf4j
@RestController
@RequestMapping("/chitgroups")
public class ChitGroupController {

    private final ChitGroupService chitGroupService;

    public ChitGroupController(ChitGroupService chitGroupService) {
        this.chitGroupService = chitGroupService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createChitGroup(@Valid @RequestBody ChitGroupRequest request,
            BindingResult result) {
        log.info("CFM_CG_001 - Creating new ChitGroup: {}", request);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            response.put("statusCode", 400);
            return ResponseEntity.badRequest().body(response);
        }

        ChitGroup group = chitGroupService.saveChitGroup(request);
        response.put("group", group);
        response.put("statusCode", 200);
        log.info("CFM_CG_001 - ChitGroup created successfully with ID: {}", group.getGroupId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChitGroup> getChitGroupById(@PathVariable Long id) {
        log.info("CFM_CG_002 - Getting ChitGroup by ID: {}", id);
        return ResponseEntity.ok(chitGroupService.getChitGroupById(id));
    }

    @GetMapping
    public ResponseEntity<List<ChitGroup>> getAllChitGroups() {
        log.info("CFM_CG_003 - Fetching all ChitGroups");
        return ResponseEntity.ok(chitGroupService.getAllChitGroups());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChitGroup> updateChitGroup(@PathVariable Long id,
            @Valid @RequestBody ChitGroupRequest request,
            BindingResult result) {
        log.info("CFM_CG_004 - Updating ChitGroup ID: {}", id);
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            log.warn("CFM_CG_004 - Validation errors: {}", errors);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chitGroupService.updateChitGroup(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChitGroup(@PathVariable Long id) {
        log.info("CFM_CG_005 - Deleting ChitGroup ID: {}", id);
        chitGroupService.deleteChitGroup(id);
        return ResponseEntity.noContent().build();
    }
}
