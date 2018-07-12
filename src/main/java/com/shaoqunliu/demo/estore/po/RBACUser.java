package com.shaoqunliu.demo.estore.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.GsonBuilder;
import com.shaoqunliu.demo.estore.validation.groups.user.AddUser;
import com.shaoqunliu.demo.estore.validation.groups.user.ModifyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_rbac_user")
public class RBACUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JSONField(alternateNames = "user_id")
    @NotNull(groups = {
            ModifyUser.class
    })
    private Long id;

    @Size(min = 6, max = 24, groups = {
            AddUser.class,
            ModifyUser.class
    })
    @NotNull(groups = {
            AddUser.class,
            ModifyUser.class
    })
    private String password;

    private Boolean enabled = true;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_rbac_user_role", joinColumns = {
            @JoinColumn(name = "userId", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id")
    })
    private List<RBACRole> authorities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<RBACRole> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new GsonBuilder().enableComplexMapKeySerialization().create().toJson(this);
    }
}