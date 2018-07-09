package com.shaoqunliu.demo.estore.po;

import com.shaoqunliu.security.util.BasicHttpRequest;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "t_rbac_permission")
public class RBACPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    private String name;

    @Size(max = 75)
    private String uri;

    @Min(0) @Max(7)
    private Byte method;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_rbac_role_permission", joinColumns = {
            @JoinColumn(name = "permissionId", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id")
    })
    private List<RBACRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public Byte getMethod() {
        return method;
    }

    public void setMethod(Byte method) {
        this.method = method;
    }

    public BasicHttpRequest getRequest() {
        return new BasicHttpRequest(uri, method.intValue());
    }

    public List<RBACRole> getRoles() {
        return roles;
    }

    public void setRoles(List<RBACRole> roles) {
        this.roles = roles;
    }
}