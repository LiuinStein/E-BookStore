package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.PersonalInfo;
import com.shaoqunliu.demo.estore.po.RBACRole;
import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.po.RBACUserRole;
import com.shaoqunliu.demo.estore.repository.UserInfoRepository;
import com.shaoqunliu.demo.estore.repository.UserRepository;
import com.shaoqunliu.demo.estore.repository.UserRoleRepository;
import com.shaoqunliu.demo.estore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("myUserService")
public class MyUserService implements UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository infoRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public MyUserService(UserRepository userRepository, UserInfoRepository infoRepository, UserRoleRepository userRoleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.infoRepository = infoRepository;
        this.userRoleRepository = userRoleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<RBACUser> user = userRepository.findById(Long.parseLong(s));
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(s + " was not found in this server!");
    }

    @Override
    @Transactional
    public Long addUser(RBACUser user, PersonalInfo info, RBACRole role) {
        user.setPassword(encoder.encode(user.getPassword()));
        RBACUser user1 = userRepository.save(user);
        info.setId(user1.getId());
        infoRepository.save(info);
        RBACUserRole userRole = new RBACUserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user1.getId());
        userRoleRepository.save(userRole);
        return user1.getId();
    }

    @Override
    public boolean modifyUser(RBACUser user, String oldPassword) {
        Optional<RBACUser> dummy = userRepository.findById(user.getId());
        if (dummy.isPresent()) {
            if (!encoder.matches(oldPassword, dummy.get().getPassword())) {
                return false;
            }
            user.setPassword(encoder.encode(user.getPassword()));
            user.setEnabled(true);
            return userRepository.save(user).getId() != null;
        }
        return false;
    }
}
