package cbo.core.adrs.repositories;

import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByCategories_Id(Long categoryId);

    List<Application> findByOwnerId(String ownerId);
}
