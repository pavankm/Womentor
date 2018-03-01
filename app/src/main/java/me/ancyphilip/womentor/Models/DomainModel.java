package me.ancyphilip.womentor.Models;

import java.io.Serializable;

public class DomainModel implements Serializable{

    private String id;
    private String name;

    public DomainModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DomainModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
