package com.lxc.tim.Component;

import com.alibaba.fastjson.JSON;
import com.lxc.tim.entity.Message;
import com.lxc.tim.util.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/20
 */
@ServerEndpoint("/websocket/OneToOne/{account1}/{account2}")
@Component
public class WebsocketOneToOneServer {
    private static final Logger log = LoggerFactory.getLogger(WebsocketOneToOneServer.class);




    private static RabbitTemplate rabbitTemplate;



    @Autowired
    public  void setRabbitTemplate(RabbitTemplate rabbitTemplate)
    {
        WebsocketOneToOneServer.rabbitTemplate=rabbitTemplate;
    }
    /**
     * 连接建立成功调用的方法
     */

    /**
     * 记录当前一对一连接的session
     * String  ：  username1 + to + username2
     */
    public static final Map<String, Session> OneToOneSessionMap = new ConcurrentHashMap<>();

    public static final Map<String, List<Message>> OneToOneMessageMap = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("account1") String account1, @PathParam("account2") String account2) throws IOException {
        Session session1 = WebSocketUserServer.sessionMap.get(account1);

        if(session1==null)
        {
            sendMessage(account1+"用户未上线",session);
            session.close();
        }
        else {
            OneToOneSessionMap.put(account1+"To"+account2,session);
            List<Message> list = OneToOneMessageMap.get(account2+"To"+account1);
            if(list!=null)
            {
                while(!list.isEmpty())
                {
                    sendMessage(JSON.toJSONString(list.get(0)),session);
                    list.remove(0);
                }
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose(Session session, @PathParam("account1") String account1, @PathParam("account2") String account2) throws IOException {
        OneToOneSessionMap.remove(account1 + "To" + account2);
        OneToOneMessageMap.remove(account2+"To"+account1);

    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     * String :
     * {
     *   from
     *   message
     *   time
     *
     * }
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("account1") String account1, @PathParam("account2") String account2) throws IOException {

        LocalDateTime localDate = LocalDateTime.now();
        String time = localDate.toString();
       Session session1 = OneToOneSessionMap.get(account2 + "To" + account1);
        Message message1 = new Message(account1,time,message);

        if(session1==null)
        {
            if(OneToOneMessageMap.get(account1+"To"+account2)==null)
            {
                List<Message> list = new ArrayList<>();
                list.add(message1);
                OneToOneMessageMap.put(account1+"To"+account2,list);
            }
            else
            {
                List list1=OneToOneMessageMap.get(account1+"To"+account2);
                list1.add(message1);
                OneToOneMessageMap.put(account1+"To"+account2,list1);
            }

        }
        else
        {
            sendMessage(JSON.toJSONString(message1),session1);
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
//            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : OneToOneSessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }



}
