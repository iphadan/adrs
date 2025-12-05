// ApplicationModuleRequest.java
package cbo.core.adrs.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApplicationModuleRequest {
    // ApplicationModuleRequest.java
    @NotNull
    private Long applicationId;

    @NotBlank
    private String moduleName;

    private String description;

    private String responsibleId; // added to match service

}
