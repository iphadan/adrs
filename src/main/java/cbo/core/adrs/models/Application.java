package cbo.core.adrs.models;

import cbo.core.adrs.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
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

    private String description;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    // Application owner (main person)

    @JoinColumn(name = "owner_id")
    private String owner;

    // Modules
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationModule> modules = new ArrayList<>();

    // Deployments (servers where the app runs)
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationDeployment> deployments = new ArrayList<>();
}

