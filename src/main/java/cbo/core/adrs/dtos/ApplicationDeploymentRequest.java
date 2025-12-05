package cbo.core.adrs.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ApplicationDeploymentRequest {

    @NotNull(message = "Server ID is required")
    private Long serverId;

    private String deploymentPath;

    private LocalDate deploymentDate;

    private String remarks;
}
