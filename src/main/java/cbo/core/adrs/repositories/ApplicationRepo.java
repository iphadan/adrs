package cbo.core.adrs.repositories;

import cbo.core.adrs.enums.ApplicationStatus;
import cbo.core.adrs.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
    List<Application> findByOwnerName(String ownerId);
    List<Application> findByCategories_Id(Long categoryId);
    List<Application> findByStatus(cbo.core.adrs.enums.ApplicationStatus status);
}
