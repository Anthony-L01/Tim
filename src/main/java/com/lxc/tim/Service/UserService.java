package com.lxc.tim.Service;

import com.lxc.tim.entity.ResponseResult;
import com.lxc.tim.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */
public interface UserService {
    /**
     * @Description: 获取好友账号列表
     * @Param: [java.lang.String]
     * @return: java.util.List<java.lang.String>
     * @Author: Anthony
     * @Date: 2022/2/21
     */

    public List<String> getFriend(String account);

    /**
     * @Description: 获取用户信息
     * @Param: [java.lang.String]
     * @return: com.lxc.tim.entity.User
     * @Author: Anthony
     * @Date: 2022/2/25
     */

    User getForUser(String account);

    ResponseResult deleteFriend(String account1,String account2);

    int updateUserOnlineStatus(String account,int status);

    ResponseResult addFriend(String account1,String account2);


    int JoinGroup(String account,int GroupID);

    int LeaveGroup(String account,int GroupID);

    ResponseResult UserLogin(User user);
    ResponseResult RegisterUser(User user);

    public ResponseResult logout() ;
}
