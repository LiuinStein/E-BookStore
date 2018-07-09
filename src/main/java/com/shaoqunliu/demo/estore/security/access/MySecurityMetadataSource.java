package com.shaoqunliu.demo.estore.security.access;

import com.shaoqunliu.demo.estore.po.RBACPermission;
import com.shaoqunliu.demo.estore.service.PermissionService;
import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<BasicHttpRequest, Collection<ConfigAttribute>> resourcePermissionMap;

    private final PermissionService permissionService;

    public MySecurityMetadataSource(PermissionService permissionService) {
        this.permissionService = permissionService;
        refreshPermissions();
    }

    public void refreshPermissions() {
        resourcePermissionMap = new HashMap<>();
        List<RBACPermission> permissions = permissionService.findAllPermissions();
        for (RBACPermission permission : permissions) {
            if (resourcePermissionMap.containsKey(permission.getRequest())) {
                permission.getRoles().forEach(rbacRole -> resourcePermissionMap.get(permission.getRequest()).add(new SecurityConfig(rbacRole.getName())));
            } else {
                Collection<ConfigAttribute> configs = new ArrayList<>();
                permission.getRoles().forEach(rbacRole -> configs.add(new SecurityConfig(rbacRole.getName())));
                resourcePermissionMap.put(permission.getRequest(), configs);
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        try {
            BasicHttpRequest request = new BasicHttpRequest(filterInvocation.getRequest());
            for (BasicHttpRequest iterateRequest : resourcePermissionMap.keySet()) {
                if (request.match(iterateRequest)) {
                    return resourcePermissionMap.get(iterateRequest);
                }
            }
        } catch (SecurityComponentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
