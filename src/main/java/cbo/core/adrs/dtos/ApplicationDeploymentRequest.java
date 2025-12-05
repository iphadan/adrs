// ApplicationDeploymentRequest.java
package cbo.core.adrs.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ApplicationDeploymentRequest {
    // ApplicationDeploymentRequest.java
    @NotNull
    private Long applicationId;

    @NotBlank
    private String deployedBy;

    @NotNull
    private Long serverId;

    @NotBlank
    private String deploymentPath;

    private LocalDate deploymentDate;

    private String remarks;

    @NotBlank
    private String environment; // optional

}
