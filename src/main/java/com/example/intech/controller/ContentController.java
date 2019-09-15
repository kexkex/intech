package com.example.intech.controller;

import com.example.intech.model.Client;
import com.example.intech.model.Content;
import com.example.intech.service.ClientService;
import com.example.intech.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ContentController {
    public static final String STATUS_CODE = "status";
    public static final String DATA = "data";
    @Autowired
    private ContentService contentService;
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/contents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getContents(){
        List<Content> contents = contentService.getAllContents();
        List<Map<String, Object>> response = new ArrayList<>(contents.size());

        contents.forEach( content -> {
            Map<String, Object> oneContent = new HashMap<>();
            oneContent.put("id", content.getId());
            oneContent.put("name", content.getName());
            response.add(oneContent);
        });
        return response;
    }

    @RequestMapping(value = "/contents/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getContent(@PathVariable Integer page){
        List<Content> contents = contentService.getAllContents();
        Map<String, Object> response = new HashMap<>();
        if (contents.isEmpty()) {
            return badRequest("Contents are empty!");
        }
        if (page < 0){
            return badRequest("Page can not be under zero!");
        }
        response.put(STATUS_CODE, 200);
        if (contents.size() > page) {
            Map<String, Object> content = new HashMap<>();
            content.put("id", contents.get(page).getId());
            content.put("name", contents.get(page).getName());
            response.put(DATA, content);
            return response;
        } else {
            response.put(DATA, null);
            return response;
        }
    }

    @RequestMapping(value = "/contents/{page}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addContent(@PathVariable Integer page, @RequestBody String uid){
        Map<String, Object> basicResponse = getContent(page);
        Integer statusCode = (Integer) basicResponse.get(STATUS_CODE);
        if (statusCode == 200 && basicResponse.get(DATA) != null){
            Integer contentId = parseContentId(basicResponse);
            Client client = clientService.getClientByUID(uid);
            Set<Content> clientContents = client.getContents();
            Content content = contentService.getContentById(contentId);
            clientContents.add(content);
            client.setContents(clientContents);
            clientService.updateClient(client);
        }
        return basicResponse;
    }

    private Integer parseContentId(Map<String, Object> basicResponse) {
        Map<String, Object> data = (Map<String, Object>) basicResponse.get(DATA);
        return (Integer) data.get("id");
    }

    private Map<String, Object> badRequest(String message){
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_CODE, 400);
        response.put(DATA, message);
        return response;
    }
}
