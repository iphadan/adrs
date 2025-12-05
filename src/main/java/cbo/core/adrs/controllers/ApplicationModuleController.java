package cbo.core.adrs.controllers;

import cbo.core.adrs.dtos.ApplicationModuleRequest;
import cbo.core.adrs.dtos.ApplicationModuleResponse;
import cbo.core.adrs.services.ApplicationModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ApplicationModuleController {

    private final ApplicationModuleService moduleService;

    @PostMapping
    public ResponseEntity<ApplicationModuleResponse> createModule(
            @Valid @RequestBody ApplicationModuleRequest request
    ) {
        return ResponseEntity.ok(moduleService.createModule(request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationModuleResponse>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationModuleResponse> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationModuleResponse> updateModule(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationModuleRequest request
    ) {
        return ResponseEntity.ok(moduleService.updateModule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok("Module deleted successfully.");
    }
}
