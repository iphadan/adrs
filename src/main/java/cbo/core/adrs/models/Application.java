package cbo.core.adrs.models;

import cbo.core.adrs.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
// how to store the license
@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor      // <--- Required for Hibernate
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
@ManyToMany
    private List<ApplicationCategory> categories; // internal, financial, open-source, customized

    private String version;

    private Integer port;
    private String description;


    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    // Application owner (main person)
private String ownerName;

    private String responsibleName;

    // Modules
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationModule> modules = new ArrayList<>();

    // Deployments (servers where the app runs)
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationDeployment> deployments = new ArrayList<>();
}

