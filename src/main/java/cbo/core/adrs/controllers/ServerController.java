package cbo.core.adrs.controllers;

import cbo.core.adrs.dtos.ServerRequest;
import cbo.core.adrs.dtos.ServerResponse;
import cbo.core.adrs.services.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @PostMapping
    public ResponseEntity<ServerResponse> createServer(
            @Valid @RequestBody ServerRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(serverService.createServer(request));
    }

    @GetMapping
    public ResponseEntity<List<ServerResponse>> getAllServers() {
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerResponse> getServer(@PathVariable Long id) {
        return ResponseEntity.ok(serverService.getServerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse> updateServer(
            @PathVariable Long id,
            @Valid @RequestBody ServerRequest request
    ) {
        return ResponseEntity.ok(serverService.updateServer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return ResponseEntity.ok("Server deleted successfully.");
    }
}
