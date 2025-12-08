package cbo.core.adrs.services.implementation;

import cbo.core.adrs.dtos.ApplicationCategoryResponse;
import cbo.core.adrs.dtos.ApplicationDeploymentResponse;
import cbo.core.adrs.dtos.ApplicationModuleResponse;
import cbo.core.adrs.dtos.ApplicationResponse;
import cbo.core.adrs.models.Application;
import cbo.core.adrs.models.ApplicationCategory;
import cbo.core.adrs.repositories.ApplicationCategoryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationCategoryService {
  private final ApplicationCategoryRepo applicationCategoryRepository;
    @Transactional(readOnly = true)
    public List<ApplicationCategoryResponse> getAllCategories() {
        return applicationCategoryRepository.findAll().stream()
                .map(this::toApplicationCategoryResponse)
                .collect(Collectors.toList());
    }


  private ApplicationCategoryResponse toApplicationCategoryResponse(ApplicationCategory app) {
    if (app == null) return null;

    return ApplicationCategoryResponse.builder()
            .id(app.getId())
            .title(app.getTitle()).build();


  }

}
