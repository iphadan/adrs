package cbo.core.adrs.services;

import cbo.core.adrs.dtos.ServerRequest;
import cbo.core.adrs.dtos.ServerResponse;

import java.util.List;

public interface ServerService {
    ServerResponse createServer(ServerRequest request);
    ServerResponse updateServer(Long id, ServerRequest request);
    void deleteServer(Long id);
    ServerResponse getServerById(Long id);
    List<ServerResponse> getAllServers();
    List<ServerResponse> getByEnvironment(String environment);
}
