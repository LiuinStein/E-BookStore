package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.RBACPermission;
import com.shaoqunliu.demo.estore.repository.PermissionRepository;
import com.shaoqunliu.demo.estore.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myPermissionService")
public class MyPermissionService implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public MyPermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RBACPermission> findAllPermissions() {
        return permissionRepository.findAll();
    }
}
