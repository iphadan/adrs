package cbo.core.adrs.services.implementation;

import cbo.core.adrs.dtos.ApplicationDeploymentRequest;
import cbo.core.adrs.dtos.ApplicationDeploymentResponse;
import cbo.core.adrs.services.ApplicationDeploymentService;
import cbo.core.adrs.utils.InputSanitizer;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationDeploymentServiceImpl implements ApplicationDeploymentService {

    // Inject repositories here (e.g., deploymentRepository, applicationRepository)

    @Override
    public ApplicationDeploymentResponse createDeployment(ApplicationDeploymentRequest request) {
        sanitizeDeploymentRequest(request);
        // Map to entity, save, map to response
        return null; // implement saving logic
    }

    @Override
    public List<ApplicationDeploymentResponse> getAllDeployments() {
        return null; // implement fetching logic
    }

    @Override
    public ApplicationDeploymentResponse getDeploymentById(Long id) {
        return null; // implement fetching logic
    }

    @Override
    public ApplicationDeploymentResponse updateDeployment(Long id, ApplicationDeploymentRequest request) {
        sanitizeDeploymentRequest(request);
        return null; // implement update logic
    }

    @Override
    public void deleteDeployment(Long id) {
        // implement delete logic
    }
    private final InputSanitizer sanitizer;
    private void sanitizeDeploymentRequest(ApplicationDeploymentRequest request) {
        if (request.getEnvironment() != null) {
            request.setEnvironment(sanitizer.sanitize(request.getEnvironment()));
        }
        if (request.getDeployedBy() != null) {
            request.setDeployedBy(sanitizer.sanitize(request.getDeployedBy()));
        }
    }
}
