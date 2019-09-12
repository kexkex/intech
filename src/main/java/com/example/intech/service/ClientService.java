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

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

}
