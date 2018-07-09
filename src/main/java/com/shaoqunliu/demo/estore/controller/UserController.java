package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.service.UserService;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/token")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public RestfulResult login(@RequestBody @Validated RBACUser user) {
//
//    }
}
