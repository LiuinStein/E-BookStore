package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<PersonalInfo, Long> {

    List<PersonalInfo> findByNameEquals(String name);
}
