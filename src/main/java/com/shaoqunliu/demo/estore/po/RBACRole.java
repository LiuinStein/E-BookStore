package com.shaoqunliu.demo.estore.po;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RBACRole {
    @Id
    private Byte id;

    private String name;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}