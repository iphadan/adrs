package cbo.core.adrs.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationModuleResponse {

    private Long id;
    private String moduleName;
    private String description;
    private String responsibleId;
}
