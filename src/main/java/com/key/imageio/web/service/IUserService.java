package com.key.imageio.web.service;

import com.key.imageio.web.entity.User;

/**
 * @author tujia
 * @since 2020/6/22 9:19
 */
public interface IUserService {

    User createUser(String name, int age);

    User queryUser();
}
