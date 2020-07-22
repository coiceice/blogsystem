package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.User;

public interface IUserService {
    int addUser(User user);
    PageInfo<User> queryUser(Integer page, Integer count);
    int deleteUser(int id);
    int updateUser(User user);
}