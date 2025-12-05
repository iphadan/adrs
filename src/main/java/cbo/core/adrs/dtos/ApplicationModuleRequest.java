package cbo.core.adrs.dtos;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ApplicationModuleRequest {

    @NotBlank(message = "Module name is required")
    private String moduleName;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Responsible person ID is required")
    private String responsibleId;
}
