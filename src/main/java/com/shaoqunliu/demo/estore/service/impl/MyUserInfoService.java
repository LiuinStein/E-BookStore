package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.PersonalInfo;
import com.shaoqunliu.demo.estore.repository.UserInfoRepository;
import com.shaoqunliu.demo.estore.service.UserInfoService;
import com.shaoqunliu.jpa.util.ObjectCopyingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("myUserInfoService")
public class MyUserInfoService implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public MyUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    @Transactional
    public boolean addUserInfo(PersonalInfo personalInfo) {
        return userInfoRepository.save(personalInfo).getId() != null;
    }

    @Override
    @Transactional
    public void deleteUserInfo(Long id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean modifyUserInfo(PersonalInfo personalInfo) {
        Optional<PersonalInfo> info = userInfoRepository.findById(personalInfo.getId());
        if (info.isPresent()) {
            ObjectCopyingUtil.copyNullProperties(info.get(), personalInfo);
            userInfoRepository.saveAndFlush(ObjectCopyingUtil.coverDifferentProperties(personalInfo, info.get()));
            return true;
        }
        return false;
    }

    @Override
    public List<PersonalInfo> findUserInfoById(Long id) {
        Optional<PersonalInfo> info = userInfoRepository.findById(id);
        List<PersonalInfo> result = new ArrayList<>();
        info.ifPresent(result::add);
        return result;
    }

    @Override
    public List<PersonalInfo> findUserInfoByName(String name) {
        return userInfoRepository.findByNameEquals(name);
    }
}
