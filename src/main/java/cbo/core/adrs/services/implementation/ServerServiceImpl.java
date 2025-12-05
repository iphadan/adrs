package cbo.core.adrs.services.implementation;

import cbo.core.adrs.dtos.ServerRequest;
import cbo.core.adrs.dtos.ServerResponse;
import cbo.core.adrs.exceptions.ResourceNotFoundException;
import cbo.core.adrs.models.Server;
import cbo.core.adrs.repositories.ServerRepo;
import cbo.core.adrs.services.ServerService;
import cbo.core.adrs.utils.SanitizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepository;

    @Override
    public ServerResponse createServer(ServerRequest request) {
        Server server = Server.builder()
                .hostname(SanitizationUtil.cleanText(request.getHostname()))
                .ipAddress(SanitizationUtil.cleanText(request.getIpAddress()))
                .url(SanitizationUtil.cleanText(request.getUrl()))
                .port(request.getPort())
                .operatingSystem(SanitizationUtil.cleanText(request.getOperatingSystem()))
                .osVersion(SanitizationUtil.cleanText(request.getOsVersion()))
                .environment(SanitizationUtil.cleanText(request.getEnvironment()))
                .cpu(SanitizationUtil.cleanText(request.getCpu()))
                .memory(SanitizationUtil.cleanText(request.getMemory()))
                .diskSpace(SanitizationUtil.cleanText(request.getDiskSpace()))
                .build();

        Server saved = serverRepository.save(server);
        return toServerResponse(saved);
    }

    @Override
    public ServerResponse updateServer(Long id, ServerRequest request) {
        Server db = serverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + id));

        if (request.getHostname() != null) db.setHostname(SanitizationUtil.cleanText(request.getHostname()));
        if (request.getIpAddress() != null) db.setIpAddress(SanitizationUtil.cleanText(request.getIpAddress()));
        if (request.getUrl() != null) db.setUrl(SanitizationUtil.cleanText(request.getUrl()));
        if (request.getPort() != null) db.setPort(request.getPort());
        if (request.getOperatingSystem() != null) db.setOperatingSystem(SanitizationUtil.cleanText(request.getOperatingSystem()));
        if (request.getOsVersion() != null) db.setOsVersion(SanitizationUtil.cleanText(request.getOsVersion()));
        if (request.getEnvironment() != null) db.setEnvironment(SanitizationUtil.cleanText(request.getEnvironment()));
        if (request.getCpu() != null) db.setCpu(SanitizationUtil.cleanText(request.getCpu()));
        if (request.getMemory() != null) db.setMemory(SanitizationUtil.cleanText(request.getMemory()));
        if (request.getDiskSpace() != null) db.setDiskSpace(SanitizationUtil.cleanText(request.getDiskSpace()));

        Server saved = serverRepository.save(db);
        return toServerResponse(saved);
    }

    @Override
    public void deleteServer(Long id) {
        Server db = serverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + id));
        serverRepository.delete(db);
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse getServerById(Long id) {
        Server db = serverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + id));
        return toServerResponse(db);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServerResponse> getAllServers() {
        return serverRepository.findAll().stream()
                .map(this::toServerResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServerResponse> getByEnvironment(String environment) {
        return serverRepository.findByEnvironment(environment).stream()
                .map(this::toServerResponse)
                .collect(Collectors.toList());
    }

    private ServerResponse toServerResponse(Server s) {
        return ServerResponse.builder()
                .id(s.getId())
                .hostname(s.getHostname())
                .ipAddress(s.getIpAddress())
                .url(s.getUrl())
                .port(s.getPort())
                .operatingSystem(s.getOperatingSystem())
                .osVersion(s.getOsVersion())
                .environment(s.getEnvironment())
                .cpu(s.getCpu())
                .memory(s.getMemory())
                .diskSpace(s.getDiskSpace())
                .build();
    }
}
