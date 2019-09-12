package com.example.intech.repository;

import com.example.intech.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepo extends JpaRepository<Content, Integer> {
}
