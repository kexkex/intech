package com.example.intech.bootstrap;

import com.example.intech.model.Client;
import com.example.intech.model.Content;
import com.example.intech.repository.ClientRepo;
import com.example.intech.repository.ContentRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private ClientRepo clientRepo;
    private ContentRepo contentRepo;

    public BootStrap(ClientRepo clientRepo, ContentRepo contentRepo) {
        this.clientRepo = clientRepo;
        this.contentRepo = contentRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        Client client = new Client("ivan");
        clientRepo.save(client);

        Content content = new Content("basic");
        contentRepo.save(content);
    }
}
