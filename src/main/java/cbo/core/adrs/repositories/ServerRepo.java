package cbo.core.adrs.repositories;

import cbo.core.adrs.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepo extends JpaRepository<Server,Long> {
}
