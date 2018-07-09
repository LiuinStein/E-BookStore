package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.repository.UserRepository;
import com.shaoqunliu.demo.estore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("myUserService")
public class MyUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<RBACUser> user = userRepository.findById(Long.parseLong(s));
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(s + " was not found in this server!");
    }
}
