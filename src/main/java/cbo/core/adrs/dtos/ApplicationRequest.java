package cbo.core.adrs.dtos;

import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.models.Server;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class ApplicationRequest {

    @NotBlank(message = "Application name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Version is required")
    private String version;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @NotBlank(message = "Owner Name is required")
    private String ownerName;
    @NotBlank(message = "Responsible Name is required")
    private String responsibleName;

    @NotEmpty(message = "At least one category must be selected")
    private List<Long> categoryIds;

    private Integer port;
    private List<ApplicationModuleRequest> modules;

    private List<ApplicationDeploymentRequest> deployments;
}
