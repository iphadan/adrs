package cbo.core.adrs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application_deployments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDeployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relations
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    private String deploymentPath;

    private LocalDate deploymentDate;

    private String remarks;
}
