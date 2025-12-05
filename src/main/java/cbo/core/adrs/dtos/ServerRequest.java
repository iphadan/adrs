package cbo.core.adrs.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ServerRequest {

    @NotBlank(message = "Hostname is required")
    private String hostname;

    @NotBlank(message = "IP Address is required")
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Invalid IP address format")
    private String ipAddress;

    private String url;

    @NotNull(message = "Port is required")
    @Min(1)
    @Max(65535)
    private Integer port;

    private String operatingSystem;
    private String osVersion;
    private String environment;
    private String cpu;
    private String memory;
    private String diskSpace;
}
