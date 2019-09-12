package com.example.intech.controller;

import com.example.intech.model.Client;
import com.example.intech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {
    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getClients(){
        List<Client> clients = clientService.getAllClients();

        List<Map<String, Object>> response = new ArrayList<>(clients.size());
        clients.forEach(client -> {
            Map<String, Object> oneClient = new HashMap<>();
            oneClient.put("id", client.getId());
            oneClient.put("name", client.getName());
            response.add(oneClient);
        });
        return response;

    }
}
