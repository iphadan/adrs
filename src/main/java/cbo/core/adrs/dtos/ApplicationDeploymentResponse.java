// ApplicationDeploymentResponse.java
package cbo.core.adrs.dtos;

import cbo.core.adrs.models.Server;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicationDeploymentResponse {
    private Long id;
    private Long applicationId;
    private String environment;
    private String deployedBy;
    private String deployedAt; // e.g., ISO date string
    private ServerResponse server;
    private LocalDate deploymentDate;
    private String deploymentPath;
    private String remarks;
}

