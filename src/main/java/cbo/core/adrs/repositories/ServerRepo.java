package cbo.core.adrs.repositories;

import cbo.core.adrs.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepo extends JpaRepository<Server,Long> {
    List<Server> findByEnvironment(String environment);
}
