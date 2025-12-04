package cbo.core.adrs.services.implementation;



import cbo.core.adrs.models.*;
import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.repositories.*;
import cbo.core.adrs.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepository;
    private final ApplicationModuleRepo moduleRepository;
    private final ApplicationDeploymentRepo deploymentRepository;
    private final ApplicationCategoryRepo categoryRepository;
    private final ServerRepo serverRepository;

    // ------------------- APPLICATION CRUD -------------------
    @Override
    public Application create(Application app) {
        return applicationRepository.save(app);
    }

    @Override
    public Application update(Long id, Application req) {
        Application db = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        db.setName(req.getName());
        db.setVersion(req.getVersion());
        db.setDescription(req.getDescription());
        db.setOwnerId(req.getOwnerId());
        db.setStatus(req.getStatus());
        db.setCategories(req.getCategories());

        return applicationRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        Application app = getById(id);
        app.setStatus(ApplicationStatus.ARCHIVED); // soft delete
        applicationRepository.save(app);
    }

    @Override
    public Application getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    @Override
    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    @Override
    public List<Application> getByOwner(String ownerId) {
        return applicationRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Application> getByCategory(Long categoryId) {
        return applicationRepository.findByCategories_Id(categoryId);
    }

    // ------------------- MODULE MANAGEMENT -------------------
    @Override
    public ApplicationModule addModule(Long appId, ApplicationModule module) {
        Application app = getById(appId);
        module.setApplication(app);
        return moduleRepository.save(module);
    }

    @Override
    public ApplicationModule updateModule(Long appId, Long moduleId, ApplicationModule req) {
        Application app = getById(appId);
        ApplicationModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        if (!module.getApplication().getId().equals(app.getId()))
            throw new RuntimeException("Module does not belong to this application");

        module.setModuleName(req.getModuleName());
        module.setDescription(req.getDescription());
        module.setResponsibleId(req.getResponsibleId());

        return moduleRepository.save(module);
    }

    @Override
    public void removeModule(Long appId, Long moduleId) {
        ApplicationModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        if (!module.getApplication().getId().equals(appId))
            throw new RuntimeException("Module mismatch");

        moduleRepository.delete(module);
    }

    // ------------------- DEPLOYMENT MANAGEMENT -------------------
    @Override
    public ApplicationDeployment addDeployment(Long appId, ApplicationDeployment deployment) {
        Application app = getById(appId);
        deployment.setApplication(app);

        // Ensure server exists
        if (deployment.getServer() != null) {
            serverRepository.findById(deployment.getServer().getId())
                    .orElseThrow(() -> new RuntimeException("Server not found"));
        }

        return deploymentRepository.save(deployment);
    }

    @Override
    public ApplicationDeployment updateDeployment(Long appId, Long deploymentId, ApplicationDeployment req) {
        ApplicationDeployment db = deploymentRepository.findById(deploymentId)
                .orElseThrow(() -> new RuntimeException("Deployment not found"));

        db.setDeploymentDate(req.getDeploymentDate());
        db.setDeploymentPath(req.getDeploymentPath());
        db.setRemarks(req.getRemarks());
        db.setServer(req.getServer());

        return deploymentRepository.save(db);
    }

    @Override
    public void removeDeployment(Long appId, Long deploymentId) {
        deploymentRepository.deleteById(deploymentId);
    }

    // ------------------- CATEGORY MANAGEMENT -------------------
    @Override
    public Application assignCategory(Long appId, Long categoryId) {
        Application app = getById(appId);
        ApplicationCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        app.getCategories().add(category);
        return applicationRepository.save(app);
    }

    @Override
    public Application removeCategory(Long appId, Long categoryId) {
        Application app = getById(appId);
        ApplicationCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        app.getCategories().remove(category);
        return applicationRepository.save(app);
    }
}
