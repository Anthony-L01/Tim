package com.lxc.tim.Service.impl;

import com.alibaba.fastjson.JSONArray;
import com.lxc.tim.Service.UserService;
import com.lxc.tim.entity.LoginUser;
import com.lxc.tim.entity.ResponseResult;
import com.lxc.tim.entity.User;
import com.lxc.tim.mapper.UserMapper;
import com.lxc.tim.util.JwtUtil;
import com.lxc.tim.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */
@Service
public class UserServiceImpl implements UserService {
   @Autowired
    UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    PasswordEncoder passwordEncoder;

   @Override
    public List<String> getFriend(String account) {
        return userMapper.getFriend(account);
    }

    @Override
    public User getForUser(String account) {
        return userMapper.getForUser(account);
    }

    @Override
    public ResponseResult deleteFriend(String account1, String account2) {
        User user=null;
        if((user=userMapper.getForUser(account1))==null||(user=userMapper.getForUser(account2))==null)
            return new ResponseResult(500,"删除用户失败，查看用户账号是否正确");
        else userMapper.deleteFriend(account1,account2);
        return new ResponseResult(200,"删除好友成功");
    }

    @Override
    public int updateUserOnlineStatus(String account, int status) {
        return userMapper.updateUserOnlineStatus(account,status);
    }

    @Override
    public ResponseResult addFriend(String account1, String account2) {
       User user=null;
       if((user=userMapper.getForUser(account1))==null||(user=userMapper.getForUser(account2))==null)
           return new ResponseResult(500,"添加用户失败，查看用户账号是否正确");
       else userMapper.addFriend(account1,account2);
        return new ResponseResult(200,"添加好友成功");
    }

    @Override
    public int JoinGroup(String account, int GroupID) {
        return userMapper.JoinGroup(account,GroupID);
    }

    @Override
    public int LeaveGroup(String account, int GroupID) {
        return userMapper.LeaveGroup(account,GroupID);
    }

    @Override
    public ResponseResult UserLogin(User user) {
        User user1;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String account = loginUser.getUser().getAccount().toString();
        String jwt = JwtUtil.createJWT(account);
        redisCache.setCacheObject("login:" + account, loginUser);
        user1=userMapper.getForUser(user.getAccount());
        user1.setPassword(user.getPassword());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(user1);
        jsonArray.add(new AbstractMap.SimpleEntry<>("token",jwt));
        return new ResponseResult(200,"登录成功",jsonArray);
    }

    @Override
    public ResponseResult RegisterUser(User user) {
        User user1;
        Map<String,String> map = new HashMap<>();
        if((user1=userMapper.getForUser(user.getAccount()))==null)
        {
            String mima=user.getPassword();
            user.setPassword(passwordEncoder.encode(mima));
            userMapper.insertUser(user);
            user1=userMapper.getForUser(user.getAccount());
            user1.setPassword(mima);
            return new ResponseResult(200,"注册成功",user1);
        }
        else
        {
           return new ResponseResult(500,"此账号已存在");
        }
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
       String account = loginUser.getUser().getAccount();
        //删除用户信息
        redisCache.deleteObject("login:"+account);
        return new ResponseResult(200,"退出成功");
    }
}
