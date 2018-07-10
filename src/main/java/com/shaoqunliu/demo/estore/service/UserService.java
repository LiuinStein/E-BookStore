package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.PersonalInfo;
import com.shaoqunliu.demo.estore.po.RBACRole;
import com.shaoqunliu.demo.estore.po.RBACUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Long addUser(RBACUser user, PersonalInfo info, RBACRole role);

    boolean modifyUser(RBACUser user, String oldPassword);
}
