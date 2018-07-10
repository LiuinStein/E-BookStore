package com.shaoqunliu.demo.estore.controller;

import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.demo.estore.po.PersonalInfo;
import com.shaoqunliu.demo.estore.po.RBACRole;
import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.service.UserService;
import com.shaoqunliu.demo.estore.validation.groups.user.AddUser;
import com.shaoqunliu.demo.estore.validation.groups.user.ModifyUser;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final Validator validator;

    @Autowired
    public UserController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addUser(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws IOException {
        RBACUser user = jsonObject.toJavaObject(RBACUser.class);
        PersonalInfo info = jsonObject.toJavaObject(PersonalInfo.class);
        RBACRole role = jsonObject.toJavaObject(RBACRole.class);
        Set<ConstraintViolation<Object>> violationSet = validator.validate(user, AddUser.class);
        violationSet.addAll(validator.validate(info, AddUser.class));
        violationSet.addAll(validator.validate(role, AddUser.class));
        if (!violationSet.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), violationSet.toString());
            return null;
        }
        Long id = userService.addUser(user, info, role);
        if (id != null) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id", id);
            return new RestfulResult(0, "User info add success", result);
        }
        return new RestfulResult(1, "", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyUser(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws IOException {
        RBACUser user = jsonObject.toJavaObject(RBACUser.class);
        Set<ConstraintViolation<Object>> violationSet = validator.validate(user, ModifyUser.class);
        if (!violationSet.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), violationSet.toString());
            return null;
        }
        if (userService.modifyUser(user, jsonObject.getString("old_password"))) {
            return new RestfulResult(0, "User password has been changed successfully", new HashMap<>());
        }
        return new RestfulResult(1, "Unable to change user's password due to username not found or old password error", new HashMap<>());
    }
}
