package com.example.intech.controller;

import com.example.intech.model.Client;
import com.example.intech.model.Content;
import com.example.intech.service.ClientService;
import com.example.intech.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    public static final String STATUS_CODE = "status";
    public static final String DATA = "data";
    @Autowired
    private ClientService clientService;
    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/clients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> newClient(@RequestBody String name){
        Map<String, Object> response = new HashMap<>();
        Client client = new Client(name);
        clientService.createClient(client);
        response.put(STATUS_CODE, 200);
        response.put("uid", client.getUID());
        return response;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getClients(){
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_CODE, 200);
        List<Client> clients = clientService.getAllClients();
        List<Map<String, Object>> clientsOut = new ArrayList<>(clients.size());
        clients.forEach(client -> {
            Map<String, Object> oneClient = new HashMap<>();
            oneClient.put("uid", client.getUID());
            oneClient.put("name", client.getName());
            List<Content> contents = new ArrayList<>(client.getContents());
            List<Map<String, Object>> contentsOut = new ArrayList<>(client.getContents().size());
            contents.forEach(content -> {
                Map<String, Object> oneContent = new HashMap<>();
                oneContent.put("id", content.getId());
                oneContent.put("name", content.getName());
                contentsOut.add(oneContent);
            });
            oneClient.put("contents", contentsOut);
            clientsOut.add(oneClient);
        });
        response.put(DATA, clientsOut);

        return response;
    }

    @RequestMapping(value = "/clients/{uid}/content/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getContent(@PathVariable String uid, @PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        if (validateClient(uid)){
            if (page < 0) {
                return badRequest("Page can not be under zero!");
            }
            List<Content> contents = new ArrayList<>(clientService.getClientByUID(uid).getContents());
            if (!contents.isEmpty() && contents.size() > page) {
                response.put(STATUS_CODE, 200);
                Map<String, Object> contentsOut = new HashMap<>();
                contentsOut.put("id", contents.get(page).getId());
                contentsOut.put("name", contents.get(page).getName());
                response.put(DATA, contentsOut);
                return response;
            } else {
                response.put(DATA, null);
                return response;
            }
        } else {
            return badRequest("Not such user found!");
        }
    }

    @RequestMapping(value = "/clients/{uid}/content/{page}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteContent (@PathVariable String uid, @PathVariable Integer page){
        Map<String, Object> basicResponse = getContent(uid, page);
        Integer statusCode = (Integer) basicResponse.get(STATUS_CODE);
        if (statusCode == 200 && basicResponse.get(DATA) != null){
            Integer contentId = parseContentId(basicResponse);
            Client client = clientService.getClientByUID(uid);
            Set<Content> clientContents = client.getContents();
            Content content = contentService.getContentById(contentId);
            clientContents.remove(content);
            client.setContents(clientContents);
        }
        return basicResponse;
    }

    private Integer parseContentId(Map<String, Object> basicResponse) {
        Map<String, Object> data = (Map<String, Object>) basicResponse.get(DATA);
        return (Integer) data.get("id");
    }

    private Boolean validateClient(String uid) {
        Client client = clientService.getClientByUID(uid);
        if (client != null) {
            return true;
        } else {
            return false;
        }
    }

    private Map<String, Object> badRequest(String message){
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_CODE, 400);
        response.put(DATA, message);
        return response;
    }

}


