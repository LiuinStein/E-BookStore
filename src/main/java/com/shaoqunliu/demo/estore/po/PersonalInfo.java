package com.shaoqunliu.demo.estore.po;

import com.shaoqunliu.demo.estore.validation.groups.user.AddUserInfo;
import com.shaoqunliu.demo.estore.validation.groups.user.ModifyUserInfo;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "t_personal_info")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {
            ModifyUserInfo.class
    })
    private Long id;

    @Size(max = 30)
    @NotNull(groups = {
            AddUserInfo.class
    })
    private String name;

    @Min(0) @Max(1)
    @NotNull(groups = {
            AddUserInfo.class
    })
    private Byte gender;

    @Email
    @Size(max = 30)
    @NotNull(groups = {
            AddUserInfo.class
    })
    private String email;

    @Size(max = 20)
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{4}[\\s.-]?\\d{4}$")
    @NotNull(groups = {
            AddUserInfo.class
    })
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}