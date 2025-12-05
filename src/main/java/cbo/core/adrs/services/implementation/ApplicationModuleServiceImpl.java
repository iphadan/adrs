package cbo.core.adrs.services.implementation;

import cbo.core.adrs.dtos.ApplicationModuleRequest;
import cbo.core.adrs.dtos.ApplicationModuleResponse;
import cbo.core.adrs.services.ApplicationModuleService;
import cbo.core.adrs.utils.InputSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationModuleServiceImpl implements ApplicationModuleService {

    // Inject repositories here

    @Override
    public ApplicationModuleResponse createModule(ApplicationModuleRequest request) {
        sanitizeModuleRequest(request);
        return null; // implement saving logic
    }

    @Override
    public List<ApplicationModuleResponse> getAllModules() {
        return null;
    }

    @Override
    public ApplicationModuleResponse getModuleById(Long id) {
        return null;
    }

    @Override
    public ApplicationModuleResponse updateModule(Long id, ApplicationModuleRequest request) {
        sanitizeModuleRequest(request);
        return null;
    }

    @Override
    public void deleteModule(Long id) {
        // implement delete logic
    }
    private final InputSanitizer sanitizer;
    private void sanitizeModuleRequest(ApplicationModuleRequest request) {
        if (request.getModuleName() != null) {
            request.setModuleName(sanitizer.sanitize(request.getModuleName()));
        }
        if (request.getDescription() != null) {
            request.setDescription(sanitizer.sanitize(request.getDescription()));
        }
    }
}
