package com.example.repository;

import com.example.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends CrudRepository<License, String> {

    List<License> findByOrganizationId(String organizationId);

    Optional<License> findByLicenseIdAndOrganizationId(String licenseId, String organizationId);
}
