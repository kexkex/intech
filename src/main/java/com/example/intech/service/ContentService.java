package com.example.intech.service;

import com.example.intech.model.Content;
import com.example.intech.repository.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    public final ContentRepo contentRepo;

    public ContentService(ContentRepo contentRepo){
        this.contentRepo = contentRepo;
    }

    public void createContent(Content content){
        contentRepo.save(content);
    }

    public List<Content> getAllContents() {
        return contentRepo.findAll();
    }

    public Content getContentById(Integer id) {
        return contentRepo.findById(id).get();
    }
}
