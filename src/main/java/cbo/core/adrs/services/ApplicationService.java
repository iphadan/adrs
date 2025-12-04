package cbo.core.adrs.services;

import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.models.Application;
import cbo.core.adrs.models.ApplicationDeployment;
import cbo.core.adrs.models.ApplicationModule;

import java.util.List;

public interface ApplicationService {

    Application create(Application application);

    Application update(Long id, Application application);

    void delete(Long id); // soft delete recommended

    Application getById(Long id);

    List<Application> getAll();

    List<Application> getByStatus(ApplicationStatus status);

    List<Application> getByCategory(Long categoryId);

    List<Application> getByOwner(String ownerId);

    // --- Module operations ---
    ApplicationModule addModule(Long appId, ApplicationModule module);

    ApplicationModule updateModule(Long appId, Long moduleId, ApplicationModule module);

    void removeModule(Long appId, Long moduleId);

    // --- Deployment operations ---
    ApplicationDeployment addDeployment(Long appId, ApplicationDeployment deployment);

    ApplicationDeployment updateDeployment(Long appId, Long deploymentId, ApplicationDeployment deployment);

    void removeDeployment(Long appId, Long deploymentId);

    // --- Category operations ---
    Application assignCategory(Long appId, Long categoryId);

    Application removeCategory(Long appId, Long categoryId);
}
