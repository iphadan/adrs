package cbo.core.adrs.services;

import cbo.core.adrs.dtos.ApplicationRequest;
import cbo.core.adrs.dtos.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse createApplication(ApplicationRequest request);

    ApplicationResponse updateApplication(Long id, ApplicationRequest request);

    void deleteApplication(Long id);

    ApplicationResponse getApplicationById(Long id);

    List<ApplicationResponse> getAllApplications();

    // Additional finders
    List<ApplicationResponse> getByOwner(String ownerId);
    List<ApplicationResponse> getByCategory(Long categoryId);
}
