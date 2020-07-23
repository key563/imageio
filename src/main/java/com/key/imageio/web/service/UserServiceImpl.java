package com.key.imageio.web.service;

import com.key.imageio.web.dao.UserMapper;
import com.key.imageio.web.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tujia
 * @since 2020/6/22 9:19
 */
@Service
public class UserServiceImpl implements IUserService {

    private User user;
//    @Resource
    private UserMapper userMapper;

    @Override
    public User createUser(String name, int age) {
        user = new User(name, age);
        return user;
    }

    @Override
    public User queryUser() {
        userMapper.deleteAll();
        return new User("test", 30);
    }
}
