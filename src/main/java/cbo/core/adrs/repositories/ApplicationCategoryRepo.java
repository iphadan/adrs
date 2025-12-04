package cbo.core.adrs.repositories;

import cbo.core.adrs.models.ApplicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationCategoryRepo extends JpaRepository<ApplicationCategory,Long> {
}
