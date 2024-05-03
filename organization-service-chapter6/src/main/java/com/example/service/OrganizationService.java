package com.example.service;

import com.example.model.Organization;
import com.example.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Optional<Organization> getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId);
    }

    public Organization createOrganization(Organization organization) {
        organization.setOrganizationId(UUID.randomUUID().toString());
        return organizationRepository.save(organization);
    }

    public Organization updateOrganization(String organizationId, Organization organization) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isPresent()) {
            organizationOptional.get().setOrganizationId(organizationId);
            organizationOptional.get().setName(organization.getName());
            organizationOptional.get().setContactName(organization.getContactName());
            organizationOptional.get().setContactEmail(organization.getContactEmail());
            organizationOptional.get().setContactPhone(organization.getContactPhone());
        } else {
            throw new IllegalArgumentException(String.format("Organization id [%s] does not exist", organizationId));
        }
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(String organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Organization id [%s] does not exist", organizationId)));
        organizationRepository.delete(organization);
    }
}
