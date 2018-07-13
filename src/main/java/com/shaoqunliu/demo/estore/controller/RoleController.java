package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.RBACRole;
import com.shaoqunliu.demo.estore.service.RoleService;
import com.shaoqunliu.demo.estore.validation.groups.role.AddRole;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/user/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addRole(@RequestBody @Validated({AddRole.class}) RBACRole role) {
        roleService.addRole(role);
        return new RestfulResult(0, "New role has been added.", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@RequestBody @Validated RBACRole role) {
        roleService.deleteRole(role);
    }
}
