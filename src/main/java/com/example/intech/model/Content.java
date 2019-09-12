package com.example.intech.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    public Content(){}

    public Content(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return id == content.id &&
                Objects.equals(name, content.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
