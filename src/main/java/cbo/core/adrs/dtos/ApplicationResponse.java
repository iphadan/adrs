package cbo.core.adrs.dtos;

import cbo.core.adrs.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ApplicationResponse {

    private Long id;
    private String name;
    private String version;
    private String description;
    private ApplicationStatus status;
    private String ownerId;

    private Integer port;
private ServerResponse server;
    private List<ApplicationCategoryResponse> categories;
    private List<ApplicationModuleResponse> modules;
    private List<ApplicationDeploymentResponse> deployments;
}
