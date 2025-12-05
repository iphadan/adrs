package cbo.core.adrs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "application_modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moduleName;

    private String description;

    // Module owner / responsible person

    @JoinColumn(name = "responsible_person_id")
    private String responsibleId;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}
