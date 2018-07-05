package com.shaoqunliu.demo.estore.po;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RBACUserRole {
    @Id
    private Integer id;

    private Long userId;

    private Byte roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }
}