package cbo.core.adrs.services;

import cbo.core.adrs.dtos.ApplicationModuleRequest;
import cbo.core.adrs.dtos.ApplicationModuleResponse;

import java.util.List;

public interface ApplicationModuleService {
    ApplicationModuleResponse createModule(ApplicationModuleRequest request);
    List<ApplicationModuleResponse> getAllModules();
    ApplicationModuleResponse getModuleById(Long id);
    ApplicationModuleResponse updateModule(Long id, ApplicationModuleRequest request);
    void deleteModule(Long id);
}
