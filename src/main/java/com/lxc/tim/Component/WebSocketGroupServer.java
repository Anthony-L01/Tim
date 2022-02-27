package com.lxc.tim.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxc.tim.Service.GroupService;

import com.lxc.tim.Service.UserService;
import com.lxc.tim.entity.Message;
import com.lxc.tim.entity.User;
import com.lxc.tim.mapper.GroupMapper;
import com.lxc.tim.util.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/21
 */
@ServerEndpoint(value = "/websocket/{GroupID}/{account}")
@Component
public class WebSocketGroupServer {
    private static GroupService groupService;

    @Autowired
    public  void setGroupService(GroupService groupService)
    {
        WebSocketGroupServer.groupService=groupService;
    }

    private static RedisCache redisCache;

    @Autowired
    public void setRedisCache (RedisCache redisCache)
    {
        WebSocketGroupServer.redisCache=redisCache;
    }

    private static final Logger log = LoggerFactory.getLogger(WebSocketGroupServer.class);

    /**
     * 记录当前群在线用户的session
     */
    public static final Map<String, Session> GroupSessionMap = new ConcurrentHashMap<>();

    /**
     * 记录当前群在线用户的名字
     */
    public static final Map<String, String> GroupUserNameMap = new ConcurrentHashMap<>();


    /**
     * 记录当前在线连接数
     */

     public static final int onLineMember=GroupUserNameMap.size();

     /*
     当前离线用户的消息记录
      */
    public static final Map<String, List<Message>> GroupUserMessageMap = new ConcurrentHashMap<>();



    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService)
    {
        WebSocketGroupServer.userService=userService;
    }



    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account,@PathParam("GroupID") String GroupID) throws IOException {



        Session session1 = WebSocketUserServer.sessionMap.get(account);
        //判断用户是否上线
        if(session1==null)
        {
            sendMessage("用户未上线",session);
            session.close();
            return;
        }

        //向群聊池中添加用户
        GroupSessionMap.put(account+"OF"+GroupID,session);
        GroupUserNameMap.put(account+"OF"+GroupID,account);
        //正则表达式
        String patten=".*OF"+GroupID+"$";


        //用户在线列表
        List<String> OnlineNow=new ArrayList();

        //构建群在线列表
        for (String sessionName:GroupSessionMap.keySet()
        ) {
            if(sessionName.matches(patten))
            {
                String account1=GroupUserNameMap.get(sessionName);
                        String name=userService.getForUser(account1).getUsername();
                OnlineNow.add(name);
            }
        }

        //在群聊池中寻找此群聊的用户session
        for (String account1:GroupSessionMap.keySet()
             ) {
            if(account1.matches(patten))
            {
                Session session2 = GroupSessionMap.get(account1);
                String name=userService.getForUser(account).getUsername();
                sendMessage("用户"+name+"上线",session2);

                sendMessage("当前群在线用户列表："+OnlineNow.toString(),session2);
            }
        }


        List list =GroupUserMessageMap.get(GroupID+"To"+account);
        if(list!=null)
        {
            while(!list.isEmpty())
            {
                sendMessage(JSON.toJSONString(list.get(0)),session);
                list.remove(0);
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session,  @PathParam("account") String account,@PathParam("GroupID") String GroupID) {

        //用户池中删除此用户
        GroupSessionMap.remove(account+"OF"+GroupID);
        //正则表达式
        String patten=".*OF"+GroupID+"$";
        GroupUserNameMap.remove(account+"OF"+GroupID);
        GroupUserMessageMap.remove(GroupID+"To"+account);
        List<String> OnlineNow=new ArrayList();

        //构建群在线列表
        for (String sessionName:GroupSessionMap.keySet()
        ) {
            if(sessionName.matches(patten))
            {
                String account1=GroupUserNameMap.get(sessionName);
                String name=userService.getForUser(account1).getUsername();
                OnlineNow.add(name);
            }
        }
        //在群聊池中寻找此群聊的用户session
        for (String account1:GroupSessionMap.keySet()
        ) {
            if(account1.matches(patten))
            {
                Session session2 = GroupSessionMap.get(account1);
                String name=userService.getForUser(account).getUsername();
                sendMessage("用户"+name+"下线",session2);
                sendMessage("当前群聊在线用户"+OnlineNow.toString(),session2);
            }
        }
    }


    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("account") String account,@PathParam("GroupID") int GroupID) throws IOException {
        LocalDateTime localDate = LocalDateTime.now();
        String time = localDate.toString();
        Message message1 = new Message(account,time,message);
        String message2 = JSON.toJSONString(message1);
        //正则表达式
        String patten=".*OF"+GroupID+"$";

        /*
        在群聊池中寻找此群聊的用户session
        在每个用户的离线消息列表存储消息
         */
        List<String> member = groupService.getMember(GroupID);



        for (String account1:member
        ) {
            //如果用户在线
            if(GroupUserNameMap.values().contains(account1))
            {
                Session session2 = GroupSessionMap.get(account1+"OF"+GroupID);
                sendMessage(message2,session2);
                //GroupID+"To"+account

            }
            //如果用户不在线
            else
            {
                //如果用户离线表不存在
                if(GroupUserMessageMap.get(GroupID+"To"+account1)==null)
                {
                    List<Message> list = new ArrayList<>();
                    list.add(message1);
                   GroupUserMessageMap.put(GroupID+"To"+account1,list);
                }
                //如果用户离线表存在
                else
                {
                    List list1= GroupUserMessageMap.get(GroupID+"To"+account1);
                    list1.add(message1);
                    GroupUserMessageMap.put(GroupID+"To"+account1,list1);
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

//    /**
//     * 服务端发送消息给所有客户端
//     */
//    private void sendAllMessage(String message) {
////        try {
////            for (Session session : sessionMap.values()) {
////                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
////                session.getBasicRemote().sendText(message);
////            }
////        } catch (Exception e) {
////            log.error("服务端发送消息给客户端失败", e);
////        }
//    }
}
