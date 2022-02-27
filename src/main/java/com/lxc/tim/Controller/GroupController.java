package com.lxc.tim.Controller;

//import com.lxc.tim.Service.GroupService;
import com.lxc.tim.Service.GroupService;

import com.lxc.tim.entity.Group;
import com.lxc.tim.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */
@RestController
public class GroupController {

    @Autowired
    GroupService groupService;




    /**
     * @Description: 创建群聊
     * @Param: [java.lang.String, java.lang.String]
     * @return: com.lxc.tim.entity.ResponseResult
     * @Author: Anthony
     * @Date: 2022/2/25
     */
    @PostMapping("/Tim/createGroup")
    public ResponseResult createGroup(@RequestParam("CreateUserAccount") String CreateUserAccount, @RequestParam("GroupName") String GroupName) {
        int result = groupService.createGroup(CreateUserAccount, GroupName);
        if (result == 1) return new ResponseResult(200, "创建成功");
        else return new ResponseResult(500, "创建失败");
    }
}

