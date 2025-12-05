package cbo.core.adrs.services;

import cbo.core.adrs.dtos.ApplicationDeploymentRequest;
import cbo.core.adrs.dtos.ApplicationDeploymentResponse;

import java.util.List;

public interface ApplicationDeploymentService {
    ApplicationDeploymentResponse createDeployment(ApplicationDeploymentRequest request);
    List<ApplicationDeploymentResponse> getAllDeployments();
    ApplicationDeploymentResponse getDeploymentById(Long id);
    ApplicationDeploymentResponse updateDeployment(Long id, ApplicationDeploymentRequest request);
    void deleteDeployment(Long id);
}
