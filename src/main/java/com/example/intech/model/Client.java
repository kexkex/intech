package com.example.intech.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    private String UID;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "clients_contents", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
    private Set<Content> contents = new HashSet<>();

    public Client(){

    }

    public Client(String name){
        this.name = name;
        this.UID = UUID.randomUUID().toString();
        System.out.println(this.UID);
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return UID == client.UID &&
                Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID, name);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + UID +
                ", name='" + name + '\'' +
                '}';
    }
}
