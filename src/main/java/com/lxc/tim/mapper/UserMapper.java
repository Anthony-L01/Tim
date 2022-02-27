package com.lxc.tim.mapper;

import com.lxc.tim.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/21
 */
@Mapper
public interface UserMapper {

    /**
    * @Description: 获取好友账号列表
    * @Param: [java.lang.String]
    * @return: java.util.List<java.lang.String>
    * @Author: Anthony
    * @Date: 2022/2/21
    */
    @Select("select friend from friends where user=#{arg0}")
    public List<String> getFriend(String account);


    /**
    * @Description: 获取用户信息,判断是否存在
    * @Param: [java.lang.String]
    * @return: com.lxc.tim.entity.User
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Select("select * from user where account=#{arg0}")
    User getForUser(String account);


    /**
    * @Description: 删除好友
    * @Param: [java.lang.String, java.lang.String]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Delete("delete from friends where (user=#{arg0} and friend=#{arg1}) or (user=#{arg1} and friend=#{arg0})")
    int deleteFriend(String account1,String account2);


    /**
    * @Description: 添加好友
    * @Param: [java.lang.String, java.lang.String]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Insert("insert into friends(user,friend) values(#{arg0},#{arg1}),(#{arg1},#{arg0})")
    int addFriend(String account1,String account2);

    /**
    * @Description: 更新用户的在线状态
    * @Param: [java.lang.String, int]
     * status  1为在线  0为离线
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Update("update user set OnlineStatus=#{arg1} where account=#{arg0}")
    int updateUserOnlineStatus(String account,int status);


    /**
    * @Description: 用户加群
    * @Param: [java.lang.String, int]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Insert("insert into userofgroup(GroupID,account) values (#{arg1},#{arg0})")
    int JoinGroup(String account,int GroupID);

    /**
    * @Description: 用户退群
    * @Param: [java.lang.String, int]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Delete("delete from userofgroup where GroupID=#{arg1} and account=#{arg0}")
    int LeaveGroup(String account,int GroupID);






    /**
    * @Description: 验证账号密码是否正确
    * @Param: [java.lang.String, java.lang.String]
    * @return: com.lxc.tim.entity.User
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Select("select * from user where account=#{arg0} and password=#{arg1}")
    User checkUser(String account,String password);


    /**
    * @Description: 创建新的用户信息
    * @Param: [com.lxc.tim.entity.User]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Insert("insert into user(account,username,password,sex,age) values(#{account},#{username},#{password},#{sex},#{age})")
    int insertUser(User user);



}
