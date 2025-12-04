package cbo.core.adrs.repositories;

import cbo.core.adrs.models.ApplicationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationModuleRepo extends JpaRepository<ApplicationModule,Long> {
}
