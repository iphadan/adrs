package cbo.core.adrs.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerResponse {

    private Long id;
    private String hostname;
    private String ipAddress;
    private String url;
    private Integer port;
    private String operatingSystem;
    private String osVersion;
    private String environment;
    private String cpu;
    private String memory;
    private String diskSpace;
}
