package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.RBACRole;
import com.shaoqunliu.demo.estore.repository.RoleRepository;
import com.shaoqunliu.demo.estore.security.access.MySecurityMetadataSource;
import com.shaoqunliu.demo.estore.service.PermissionService;
import com.shaoqunliu.demo.estore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Service("myRoleService")
public class MyRoleService implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    @Autowired
    public MyRoleService(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    private void refreshPermissionTable() {
        new MySecurityMetadataSource(permissionService).refreshPermissions();
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public void addRole(RBACRole role) {
        if (role.getName().toUpperCase().equals("ROLE_NO_USER")) {
            throw new ConstraintViolationException("Role name cannot be ROLE_NO_USER, it's a system reserved role name.", null);
        }
        roleRepository.save(role);
        refreshPermissionTable();
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public void deleteRole(RBACRole role) {
        if (role.getId() != null) {
            roleRepository.deleteById(role.getId());
        } else if (role.getName() != null) {
            roleRepository.deleteByName(role.getName());
        } else {
            throw new ConstraintViolationException("Cannot delete role due to the given condition is not enough.", null);
        }
        refreshPermissionTable();
    }
}
