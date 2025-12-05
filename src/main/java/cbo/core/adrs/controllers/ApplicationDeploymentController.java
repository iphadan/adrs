package cbo.core.adrs.controllers;

import cbo.core.adrs.dtos.ApplicationDeploymentRequest;
import cbo.core.adrs.dtos.ApplicationDeploymentResponse;
import cbo.core.adrs.services.ApplicationDeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/deployments")
@RequiredArgsConstructor
public class ApplicationDeploymentController {

    private final ApplicationDeploymentService deploymentService;

    @PostMapping
    public ResponseEntity<ApplicationDeploymentResponse> createDeployment(
            @Valid @RequestBody ApplicationDeploymentRequest request
    ) {
        return ResponseEntity.ok(deploymentService.createDeployment(request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDeploymentResponse>> getAllDeployments() {
        return ResponseEntity.ok(deploymentService.getAllDeployments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDeploymentResponse> getDeploymentById(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.getDeploymentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDeploymentResponse> updateDeployment(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationDeploymentRequest request
    ) {
        return ResponseEntity.ok(deploymentService.updateDeployment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeployment(@PathVariable Long id) {
        deploymentService.deleteDeployment(id);
        return ResponseEntity.ok("Deployment deleted successfully.");
    }
}
