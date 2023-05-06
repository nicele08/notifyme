package com.nicele08.notifyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findByApiKey(String apiKey) {
        return clientRepository.findByApiKey(apiKey);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }
}
