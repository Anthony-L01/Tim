package com.lxc.tim.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */

@Mapper
public interface UserOfGroupMapper {
    
    
    /** 
    * @Description: 删除和某个群有关的群聊用户关系信息
    * @Param: [java.lang.String]
    * @return: int
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @Delete("delete from userofgroup where GroupID=#{arg0}")
    int deleteUserOfGroupInformation(int GroupID);
}
