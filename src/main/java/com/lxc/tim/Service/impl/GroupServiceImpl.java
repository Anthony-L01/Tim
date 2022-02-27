package com.lxc.tim.Service.impl;

import com.lxc.tim.Service.GroupService;
import com.lxc.tim.entity.ResponseResult;
import com.lxc.tim.mapper.GroupMapper;
import com.lxc.tim.mapper.UserOfGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */
@Service
public class GroupServiceImpl implements GroupService {
   @Autowired
    GroupMapper groupMapper;

   @Autowired
    UserOfGroupMapper userOfGroupMapper;
    @Override
    public List<String> getMember(int GroupID) {
        return groupMapper.getGroupMember(GroupID);
    }

//    @Override
//    public ResponseResult deleteGroup(int groupID) {
//        return null;
//    }


//    @Override
//    public ResponseResult deleteGroup(int GroupID) {
//        int a =groupMapper.deleteGroup(GroupID);
//        int b =userOfGroupMapper.deleteUserOfGroupInformation(GroupID);
//        if(a==1)return new ResponseResult(200,"删除群聊成功");
//        else return new ResponseResult(500,"删除群聊失败");
//    }

    @Override
    public int createGroup(String CreateUserAccount, String GroupName) {
        return  groupMapper.createGroup(CreateUserAccount,GroupName);
    }


}
