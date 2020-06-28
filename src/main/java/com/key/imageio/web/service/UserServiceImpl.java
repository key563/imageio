package com.key.imageio.web.service;

import com.key.imageio.web.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author tujia
 * @since 2020/6/22 9:19
 */
@Service
public class UserServiceImpl implements IUserService {

    private User user;

    @Override
    public User createUser(String name, int age) {
        user = new User(name, age);
        return user;
    }

    @Override
    public User queryUser() {
        return new User("test", 30);
    }
}
