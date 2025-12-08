package cbo.core.adrs.services.implementation;

import cbo.core.adrs.dtos.*;
import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.exceptions.ResourceNotFoundException;
import cbo.core.adrs.models.*;
import cbo.core.adrs.repositories.*;
import cbo.core.adrs.services.ApplicationService;
import cbo.core.adrs.utils.SanitizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepository;
    private final ApplicationCategoryRepo categoryRepository;
    private final ApplicationModuleRepo moduleRepository;
    private final ApplicationDeploymentRepo deploymentRepository;
    private final ServerRepo serverRepository;

    // ---------------- Application CRUD ----------------

    @Override
    public ApplicationResponse createApplication(ApplicationRequest request) {
        // sanitize inputs
        String name = SanitizationUtil.cleanText(request.getName());
        String version = SanitizationUtil.cleanText(request.getVersion());
        String description = SanitizationUtil.cleanHtml(request.getDescription());
        String ownerName = SanitizationUtil.cleanText(request.getOwnerName());
        String responsibleName = SanitizationUtil.cleanText(request.getResponsibleName());



        Application app = Application.builder()
                .name(name)
                .version(version)
                .description(description)
                .ownerName(ownerName)
                .responsibleName((responsibleName))
                .status(request.getStatus() == null ? ApplicationStatus.ACTIVE : request.getStatus())
                .port(request.getPort())

                .build();

        // categories
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            List<ApplicationCategory> cats = categoryRepository.findAllById(request.getCategoryIds());
            app.setCategories(new ArrayList<>(cats));
        } else {
            app.setCategories(new ArrayList<>());
        }

        // save app (cascade for modules/deployments will be handled separately after persisting IDs)
        Application saved = applicationRepository.save(app);

        // modules - map request modules, set application, save
        if (request.getModules() != null && !request.getModules().isEmpty()) {
            Application finalSaved = saved;
            List<ApplicationModule> modules = request.getModules().stream()
                    .map(mr -> {
                        ApplicationModule m = ApplicationModule.builder()
                                .moduleName(SanitizationUtil.cleanText(mr.getModuleName()))
                                .description(SanitizationUtil.cleanHtml(mr.getDescription()))
                                .responsibleId(SanitizationUtil.cleanText(mr.getResponsibleId()))
                                .application(finalSaved)
                                .build();
                        return moduleRepository.save(m);
                    }).collect(Collectors.toList());
            saved.setModules(modules);
        }

        // deployments
        if (request.getDeployments() != null && !request.getDeployments().isEmpty()) {
            List<ApplicationDeployment> deploys = new ArrayList<>();
            for (ApplicationDeploymentRequest dr : request.getDeployments()) {
                Long serverId = dr.getServerId();
                Server server = serverRepository.findById(serverId)
                        .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + serverId));

                ApplicationDeployment d = ApplicationDeployment.builder()
                        .application(saved)
                        .server(server)
                        .deploymentPath(SanitizationUtil.cleanText(dr.getDeploymentPath()))
                        .deploymentDate(dr.getDeploymentDate())
                        .remarks(SanitizationUtil.cleanHtml(dr.getRemarks()))
                        .build();
                deploys.add(deploymentRepository.save(d));
            }
            saved.setDeployments(deploys);
        }

        // final save to attach modules/deployments lists
        saved = applicationRepository.save(saved);
        return toApplicationResponse(saved);
    }

    @Override
    public ApplicationResponse updateApplication(Long id, ApplicationRequest request) {
        Application db = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        // sanitize and assign
        if (request.getName() != null) db.setName(SanitizationUtil.cleanText(request.getName()));
        if (request.getVersion() != null) db.setVersion(SanitizationUtil.cleanText(request.getVersion()));
        if (request.getDescription() != null) db.setDescription(SanitizationUtil.cleanHtml(request.getDescription()));
        if (request.getOwnerName() != null) db.setOwnerName(SanitizationUtil.cleanText(request.getOwnerName()));
        if (request.getResponsibleName() != null) db.setResponsibleName(SanitizationUtil.cleanText(request.getResponsibleName()));
        if (request.getStatus() != null) db.setStatus(request.getStatus());

        // categories replace
        if (request.getCategoryIds() != null) {
            List<ApplicationCategory> cats = categoryRepository.findAllById(request.getCategoryIds());
            db.setCategories(new ArrayList<>(cats));
        }

        // modules: for update approach we'll apply an upsert-like logic
        if (request.getModules() != null) {
            // Delete modules that are not present in request (optional policy)
            // For simplicity: clear existing and recreate from request
            // If you want to preserve ids, implement matching logic.
            // Remove existing
            if (db.getModules() != null) {
                moduleRepository.deleteAll(db.getModules());
                db.getModules().clear();
            }

            List<ApplicationModule> newModules = request.getModules().stream()
                    .map(mr -> {
                        ApplicationModule m = ApplicationModule.builder()
                                .moduleName(SanitizationUtil.cleanText(mr.getModuleName()))
                                .description(SanitizationUtil.cleanHtml(mr.getDescription()))
                                .responsibleId(SanitizationUtil.cleanText(mr.getResponsibleId()))
                                .application(db)
                                .build();
                        return moduleRepository.save(m);
                    }).collect(Collectors.toList());
            db.setModules(newModules);
        }

        // deployments update: same approach - remove all and recreate (change policy as needed)
        if (request.getDeployments() != null) {
            if (db.getDeployments() != null) {
                deploymentRepository.deleteAll(db.getDeployments());
                db.getDeployments().clear();
            }

            List<ApplicationDeployment> newDeploys = new ArrayList<>();
            for (ApplicationDeploymentRequest dr : request.getDeployments()) {
                Server server = serverRepository.findById(dr.getServerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + dr.getServerId()));

                ApplicationDeployment d = ApplicationDeployment.builder()
                        .application(db)
                        .server(server)
                        .deploymentPath(SanitizationUtil.cleanText(dr.getDeploymentPath()))
                        .deploymentDate(dr.getDeploymentDate())
                        .remarks(SanitizationUtil.cleanHtml(dr.getRemarks()))
                        .build();
                newDeploys.add(deploymentRepository.save(d));
            }
            db.setDeployments(newDeploys);
        }

        Application saved = applicationRepository.save(db);
        return toApplicationResponse(saved);
    }

    @Override
    public void deleteApplication(Long id) {
        Application db = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        // soft-delete behavior: mark as archived
        db.setStatus(ApplicationStatus.ARCHIVED);
        applicationRepository.save(db);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) {
        Application db = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return toApplicationResponse(db);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::toApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getByOwner(String ownerName) {
        return applicationRepository.findByOwnerName(ownerName).stream()
                .map(this::toApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getByCategory(Long categoryId) {
        return applicationRepository.findByCategories_Id(categoryId).stream()
                .map(this::toApplicationResponse)
                .collect(Collectors.toList());
    }

    // ---------------- Mapping helpers ----------------

    private ApplicationResponse toApplicationResponse(Application app) {
        List<ApplicationCategoryResponse> categories = (app.getCategories() == null)
                ? Collections.emptyList()
                : app.getCategories().stream()
                .map(c -> ApplicationCategoryResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .build())
                .collect(Collectors.toList());

        List<ApplicationModuleResponse> modules = (app.getModules() == null)
                ? Collections.emptyList()
                : app.getModules().stream()
                .map(m -> ApplicationModuleResponse.builder()
                        .id(m.getId())
                        .moduleName(m.getModuleName())
                        .description(m.getDescription())
                        .responsibleId(m.getResponsibleId())
                        .build())
                .collect(Collectors.toList());

        List<ApplicationDeploymentResponse> deployments = (app.getDeployments() == null)
                ? Collections.emptyList()
                : app.getDeployments().stream()
                .map(d -> ApplicationDeploymentResponse.builder()
                        .id(d.getId())
                        .server(toServerResponse(d.getServer()))
                        .deploymentDate(d.getDeploymentDate())
                        .deploymentPath(d.getDeploymentPath())
                        .remarks(d.getRemarks())
                        .build())
                .collect(Collectors.toList());

        return ApplicationResponse.builder()
                .id(app.getId())
                .name(app.getName())
                .version(app.getVersion())
                .description(app.getDescription())
                .status(app.getStatus())
                .ownerName(app.getOwnerName())
                .categories(categories)
                .modules(modules)
                .deployments(deployments)
                .build();
    }

    private ServerResponse toServerResponse(Server server) {
        if (server == null) return null;
        return ServerResponse.builder()
                .id(server.getId())
                .hostname(server.getHostname())
                .ipAddress(server.getIpAddress())
                .url(server.getUrl())
                .port(server.getPort())
                .operatingSystem(server.getOperatingSystem())
                .osVersion(server.getOsVersion())
                .environment(server.getEnvironment())
                .cpu(server.getCpu())
                .memory(server.getMemory())
                .diskSpace(server.getDiskSpace())
                .build();
    }
}
