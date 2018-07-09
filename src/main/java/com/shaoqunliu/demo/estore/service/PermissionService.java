package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.RBACPermission;

import java.util.List;

public interface PermissionService {

    List<RBACPermission> findAllPermissions();
}
