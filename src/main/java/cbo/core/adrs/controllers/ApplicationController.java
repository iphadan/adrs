package cbo.core.adrs.controllers;

import cbo.core.adrs.dtos.ApplicationRequest;
import cbo.core.adrs.dtos.ApplicationResponse;
import cbo.core.adrs.utils.InputSanitizer;
import cbo.core.adrs.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final InputSanitizer sanitizer;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody ApplicationRequest request
    ) {
        sanitize(request);
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequest request
    ) {
        sanitize(request);
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully.");
    }

    // -----------------------------------
    // XSS Sanitization for DTO input
    // -----------------------------------
    private void sanitize(ApplicationRequest request) {
        request.setName(sanitizer.sanitize(request.getName()));
        request.setDescription(sanitizer.sanitize(request.getDescription()));
        request.setVersion(sanitizer.sanitize(request.getVersion()));
        request.setOwnerName(sanitizer.sanitize(request.getOwnerName()));
        request.setResponsibleName(sanitizer.sanitize(request.getResponsibleName()));

    }
}
