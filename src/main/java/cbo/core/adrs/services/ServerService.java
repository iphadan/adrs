package cbo.core.adrs.services;


import cbo.core.adrs.models.Server;

import java.util.List;

public interface ServerService {

    Server create(Server server);
    Server update(Long id, Server server);
    void delete(Long id);
    Server getById(Long id);
    List<Server> getAll();
    List<Server> getByEnvironment(String environment); // PROD, UAT, DEV, DR
}

