package cbo.core.adrs.services.implementation;



import cbo.core.adrs.models.Server;
import cbo.core.adrs.repositories.ServerRepo;

import cbo.core.adrs.services.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepository;

    @Override
    public Server create(Server server) {
        return serverRepository.save(server);
    }

    @Override
    public Server update(Long id, Server req) {
        Server db = serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Server not found"));

        db.setHostname(req.getHostname());
        db.setIpAddress(req.getIpAddress());
        db.setUrl(req.getUrl());
        db.setPort(req.getPort());
        db.setOperatingSystem(req.getOperatingSystem());
        db.setOsVersion(req.getOsVersion());
        db.setEnvironment(req.getEnvironment());
        db.setCpu(req.getCpu());
        db.setMemory(req.getMemory());
        db.setDiskSpace(req.getDiskSpace());

        return serverRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        serverRepository.deleteById(id);
    }

    @Override
    public Server getById(Long id) {
        return serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Server not found"));
    }

    @Override
    public List<Server> getAll() {
        return serverRepository.findAll();
    }

    @Override
    public List<Server> getByEnvironment(String environment) {
        return serverRepository.findByEnvironment(environment);
    }
}
