package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.PersonalInfo;
import com.shaoqunliu.demo.estore.service.UserInfoService;
import com.shaoqunliu.demo.estore.validation.groups.user.AddUserInfo;
import com.shaoqunliu.demo.estore.validation.groups.user.ModifyUserInfo;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/user/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /*
     * Add user info operation should be completed immediately after a new user was created
     * So, creating a specific HTTP interface to add user info is meaningless.
     */
//    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public RestfulResult addUserInfo(@RequestBody @Validated({AddUserInfo.class}) PersonalInfo info) {
//        if (userInfoService.addUserInfo(info)) {
//            return new RestfulResult(0, "User info created", new HashMap<>());
//        }
//        return new RestfulResult(1, "Cannot create user info, may caused by database error!", new HashMap<>());
//    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyUserInfo(@RequestBody @Validated({ModifyUserInfo.class}) PersonalInfo info) {
        if (userInfoService.modifyUserInfo(info)) {
            return new RestfulResult(0, "User info has been changed successfully", new HashMap<>());
        }
        return new RestfulResult(1, "Cannot modify user info due to database error.", new HashMap<>());
    }

}
