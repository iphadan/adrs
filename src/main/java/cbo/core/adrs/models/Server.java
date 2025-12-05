package cbo.core.adrs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "servers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;

    private String ipAddress;

    private String url;

    private Integer port;

    private String operatingSystem;

    private String osVersion;

    private String environment; // PROD, UAT, DEV, DR

    private String cpu;

    private String memory;

    private String diskSpace;
}

