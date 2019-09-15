package com.example.intech.bootstrap;

import com.example.intech.model.Client;
import com.example.intech.model.Content;
import com.example.intech.repository.ClientRepo;
import com.example.intech.repository.ContentRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        for (int i = 0; i<=10; i++){
            Client client = new Client("ivan" + i);
            Set<Content> set = new HashSet<>();
            for (int j = 0; j<=i; j++) {
                Content content = new Content("basic" + i + j);
                set.add(content);
                contentRepo.save(content);
            }
            client.setContents(set);
            clientRepo.save(client);

        }

        for (int i = 0; i<=10; i++) {

        }
    }
}
