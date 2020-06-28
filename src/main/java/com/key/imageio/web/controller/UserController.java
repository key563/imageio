package com.key.imageio.web.controller;

import com.key.imageio.web.entity.User;
import com.key.imageio.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tujia
 * @since 2020/6/22 10:06
 */
@RestController("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/add")
    public User addUser(String name, Integer age) {
        return userService.createUser(name, age);
    }

    @GetMapping("/get")
    public User getUser() {
        return userService.queryUser();
    }
}
