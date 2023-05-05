package com.nicele08.notifyme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.exception.NotFoundException;
import com.nicele08.notifyme.model.ClientRequestBody;
import com.nicele08.notifyme.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "The Clients API")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new client")
    public ResponseEntity<Client> createClient(@RequestBody @Valid ClientRequestBody clientRequestBody) {
        Client client = new Client();
        client.setName(clientRequestBody.getName());
        client.setEmail(clientRequestBody.getEmail());
        client.setApiKey(clientRequestBody.getApiKey());
        Client newClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping
    @Operation(summary = "Get all clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by id")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> optionalClient = clientService.getClientById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return ResponseEntity.ok(client);
        } else {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client by id")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client by id")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
            @RequestBody @Valid ClientRequestBody clientRequestBody) {
        if (!clientService.existsById(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        Client client = new Client();
        client.setName(clientRequestBody.getName());
        client.setEmail(clientRequestBody.getEmail());
        client.setApiKey(clientRequestBody.getApiKey());
        client.setId(id);
        clientService.updateClient(client);
        return ResponseEntity.ok(client);
    }

}
