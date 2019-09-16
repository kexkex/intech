package com.example.intech.service;

import com.example.intech.model.Client;
import com.example.intech.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientRepo clientRepo;

    public ClientService(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

    public void createClient(Client client){
        clientRepo.save(client);
    }

    //Not used, only for testing
    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client getClientByUID(String UID){
        return clientRepo.findById(UID).orElse(null);
    }

    public void updateClient(Client client) {
        clientRepo.save(client);
    }
}
