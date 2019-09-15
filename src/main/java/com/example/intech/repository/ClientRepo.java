package com.example.intech.repository;

import com.example.intech.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, String> {
}
