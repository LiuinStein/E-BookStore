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
        permissions.forEach(x -> {
            if (resourcePermissionMap.containsKey(x.getRequest())) {
                x.getRoles().forEach(y -> resourcePermissionMap.get(x.getRequest()).add(new SecurityConfig(y.getName())));
            } else {
                Collection<ConfigAttribute> configs = new ArrayList<>();
                x.getRoles().forEach(y -> configs.add(new SecurityConfig(y.getName())));
                resourcePermissionMap.put(x.getRequest(), configs);
            }
        });
        Collection<ConfigAttribute> empty = new ArrayList<>();
        empty.add(new SecurityConfig("ROLE_NO_USER"));
        resourcePermissionMap.entrySet().stream()
                .filter(x -> x.getValue().isEmpty())
                .forEach(x -> x.setValue(empty));
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
