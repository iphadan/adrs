package cbo.core.adrs.dtos;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ApplicationDeploymentResponse {

    private Long id;

    private ServerResponse server;

    private String deploymentPath;

    private LocalDate deploymentDate;

    private String remarks;
}
