package cbo.core.adrs.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationCategoryResponse {
    private Long id;
    private String title;
}
