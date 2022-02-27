package com.lxc.tim.mapper;

import com.lxc.tim.entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/21
 */
@Mapper
public interface GroupMapper {



    /**
    * @Description: 查看群成员的account
    * @Param: [int]
    * @return: java.util.List<java.lang.String>
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Select("select account from userofgroup where GroupID=#{arg0}")
    public List<String>  getGroupMember(int GroupID);








    /**
    * @Description: 创建群聊
    * @Param: [java.lang.String, java.lang.String]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Insert("insert into group(GroupName,creator) values (#{arg1},#{arg0})")
    public int createGroup(String CreateUserAccount,String GroupName);



}
