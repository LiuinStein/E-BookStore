package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.PersonalInfo;

import java.util.List;

public interface UserInfoService {

    boolean addUserInfo(PersonalInfo personalInfo);

    void deleteUserInfo(Long id);

    boolean modifyUserInfo(PersonalInfo personalInfo);

    List<PersonalInfo> findUserInfoById(Long id);

    List<PersonalInfo> findUserInfoByName(String name);
}
