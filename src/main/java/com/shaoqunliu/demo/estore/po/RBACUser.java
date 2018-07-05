package com.shaoqunliu.demo.estore.po;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RBACUser {
    @Id
    private Long id;

    private String password;

    private Byte enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}