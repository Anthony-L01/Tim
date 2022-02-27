package com.lxc.tim.Service;

import com.lxc.tim.entity.Group;
import com.lxc.tim.entity.ResponseResult;

import java.util.List;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */

public interface GroupService {
    /**
    * @Description: 获取群的所有成员账号
    * @Param: [int]
    * @return: java.util.List<java.lang.String>
    * @Author: Anthony
    * @Date: 2022/2/25
    */

    public List<String> getMember(int GroupID);

    /**
    * @Description: 删除一个群，并且清空该群聊用户关系
    * @Param: [int]
    * @return: javax.naming.spi.ResolveResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
//    public ResponseResult deleteGroup(int GroupID);

//    public ResponseResult deleteGroup(int groupID);
    public int createGroup(String CreateUserAccount,String GroupName);



}
