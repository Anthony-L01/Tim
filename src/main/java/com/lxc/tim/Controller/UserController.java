package com.lxc.tim.Controller;

import com.lxc.tim.Service.UserService;
import com.lxc.tim.entity.ResponseResult;
import com.lxc.tim.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/25
 */
@RestController
public class UserController {


    @Autowired
    UserService userService;

    /**
    * @Description: 获取到account对应的用户的所有好友
     * 查询用户的在线情况可以用这个
    * @Param: [java.lang.String]
    * @return: java.util.List<com.lxc.tim.entity.User>
    * @Author: Anthony
    * @Date: 2022/2/25
    */

    @GetMapping("/Tim/getFriends")
    public List<User> getFriends(@RequestParam("account")String account)
    {
        List<User> list = new ArrayList<>();
        List<String> accountList = userService.getFriend(account);
        while(!accountList.isEmpty())
        {
            User user = userService.getForUser(accountList.get(0));
            list.add(user);
            accountList.remove(0);
        }
        return list;
    }


    /**
    * @Description: 删除好友
    * @Param: [java.lang.String, java.lang.String]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @GetMapping("/Tim/deleteFriend")
    public ResponseResult deleteFriend(@RequestParam("UserAccount")String UserAccount,@RequestParam("FriendAccount")String FriendAccount)
    {
        return userService.deleteFriend(UserAccount,FriendAccount);
    }


    /**
    * @Description: 添加好友
    * @Param: [java.lang.String, java.lang.String]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @GetMapping("/Tim/addFriend")
    public ResponseResult addFriend(@RequestParam("UserAccount")String UserAccount,@RequestParam("FriendAccount")String FriendAccount)
    {
        return userService.addFriend(UserAccount,FriendAccount);
    }


    /** 
    * @Description: 加入群聊
    * @Param: [java.lang.String, int]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @PostMapping("/Tim/joinGroup")
    public ResponseResult joinGroup(@RequestParam("UserAccount")String account, @RequestParam("GroupID") int GroupID)
    {
        int a =userService.JoinGroup(account,GroupID);
        if(a!=1)return new ResponseResult(500,"加入群聊错误");
        else return  new ResponseResult(200,"加入群聊成功");
    }



    /**
    * @Description: 退群
    * @Param: [java.lang.String, int]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @PostMapping("/Tim/leaveGroup")
    public ResponseResult leaveGroup(@RequestParam("UserAccount")String account, @RequestParam("GroupID") int GroupID)
    {
        int a =userService.LeaveGroup(account,GroupID);
        if(a!=1)return new ResponseResult(500,"退出群聊错误");
        else return  new ResponseResult(200,"退出群聊成功");
    }


    /**
    * @Description: 用户登录
    * @Param: [com.lxc.tim.entity.User]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @PostMapping("/Tim/UserLogin")
    public ResponseResult UserLogin(@RequestBody User user)
    {
        return userService.UserLogin(user);
    }


    /**
    * @Description: 用户注册
    * @Param: [com.lxc.tim.entity.User]
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/25
    */
    @PostMapping("/Tim/UserRegister")
    public ResponseResult UserRegister(@RequestBody User user)
    {
        return userService.RegisterUser(user);
    }


    /** 
    * @Description: 用户退出
    * @Param: []
    * @return: com.lxc.tim.entity.ResponseResult
    * @Author: Anthony
    * @Date: 2022/2/26
    */
    @GetMapping("Tim/UserLogout")
    public ResponseResult UserLogout()
    {
          return userService.logout();
    }
}
