package com.itheima.service.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.UserMapper;
import com.itheima.model.domain.User;
import com.itheima.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public int addUser(User user) {
        user.setCreated(new Date());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()) );
        int authority_id = 2;
        if("ROLE_admin".equals(user.getAuthority())) authority_id =1;
        user.setValid(1);
        userMapper.addUser(user);
        return  userMapper.addAuthority(user, authority_id);
    }

    @Override
    public PageInfo<User> queryUser(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<User> userList = userMapper.queryUser();
        PageInfo<User> pageInfo=new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        int authority_id = 2;
        if("ROLE_admin".equals(user.getAuthority())) authority_id =1;
        return userMapper.updateUser(user,authority_id);
    }
    
}