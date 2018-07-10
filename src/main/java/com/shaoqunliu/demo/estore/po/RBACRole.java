package com.shaoqunliu.demo.estore.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.demo.estore.validation.groups.user.AddUser;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_rbac_role")
public class RBACRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {
            AddUser.class
    })
    @JSONField(alternateNames = "role_id")
    private Byte id;

    @Size(max = 20)
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

    @Override
    public String getAuthority() {
        return name;
    }
}