package com.lxc.tim.Service.impl;



import com.lxc.tim.Service.UserService;
import com.lxc.tim.entity.LoginUser;
import com.lxc.tim.entity.User;
import com.lxc.tim.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {


        User user=userService.getForUser(account);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }

        //把数据封装成UserDetails返回
        return new LoginUser(user);
    }
}
