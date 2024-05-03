package com.example.controller;

import com.example.model.Organization;
import com.example.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organization/")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable String organizationId) {
        return ResponseEntity.of(organizationService.getOrganization(organizationId));
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.createOrganization(organization));
    }

    @PutMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable String organizationId, @RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationId, organization));
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable String organizationId) {
        organizationService.deleteOrganization(organizationId);
    }
}
