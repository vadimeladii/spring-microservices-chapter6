package com.example.service;

import com.example.config.ServiceConfig;
import com.example.model.License;
import com.example.model.Organization;
import com.example.repository.LicenseRepository;
import com.example.service.client.OrganizationFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final ServiceConfig serviceConfig;
    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByLicenseIdAndOrganizationId(licenseId, organizationId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(messageSource.getMessage(
                        "license.search.error.message", null, Locale.getDefault()), licenseId, organizationId)));

        Organization organization = organizationFeignClient.getOrganization(organizationId);
        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactPhone(organization.getContactPhone());
        license.setContactEmail(organization.getContactEmail());

        license.withComment(serviceConfig.getProperty());
        return license;
    }

    public License createLicense(String organizationId, License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        license.setOrganizationId(organizationId);
        return licenseRepository.save(license.withComment(serviceConfig.getProperty()));
    }

    public License updateLicense(String organizationId, String licenseId, License newLicense) {
        License license = licenseRepository.findByLicenseIdAndOrganizationId(licenseId, organizationId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(messageSource.getMessage(
                        "license.search.error.message", null, Locale.getDefault()), licenseId, organizationId)));

        license.setDescription(newLicense.getDescription());
        license.setOrganizationId(organizationId);
        license.setProductName(newLicense.getProductName());
        license.setLicenseType(newLicense.getLicenseType());
        return licenseRepository.save(license.withComment(serviceConfig.getProperty()));
    }

    public String deleteLicense(String organizationId, String licenseId) {
        String responseMessage;

        License license = licenseRepository.findByLicenseIdAndOrganizationId(licenseId, organizationId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(messageSource.getMessage(
                        "license.search.error.message", null, Locale.getDefault()), licenseId, organizationId)));

        licenseRepository.delete(license);

        responseMessage = String.format(messageSource.getMessage("license.delete.message", null,
                    Locale.getDefault()), licenseId, organizationId);

        return responseMessage;
    }
}
