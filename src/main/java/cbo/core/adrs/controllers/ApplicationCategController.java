package cbo.core.adrs.controllers;

import cbo.core.adrs.dtos.ApplicationCategoryResponse;
import cbo.core.adrs.dtos.ApplicationResponse;
import cbo.core.adrs.repositories.ApplicationCategoryRepo;
import cbo.core.adrs.services.implementation.ApplicationCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ApplicationCategController {

    private final ApplicationCategoryService applicationCategoryService;
    @GetMapping
    public ResponseEntity<List<ApplicationCategoryResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationCategoryService.getAllCategories());
    }
}
