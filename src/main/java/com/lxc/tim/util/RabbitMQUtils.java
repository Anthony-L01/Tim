package com.lxc.tim.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.util.Properties;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/24
 */
public class RabbitMQUtils{

    private  static ConnectionFactory connectionFactory;
    private   static Properties properties;
    static{
//重量级资源类加载执行之执行一次
        connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("101.132.45.41");
        connectionFactory.setPort(5672);
        //virtualHost要以“/”开头
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("Anthony");
        connectionFactory.setPassword("lxc20010716");

    }

    //定义提供连接对象的方法
    public  static  Connection   getConnection(){
        try{
            return  connectionFactory.newConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    //关闭通道和关闭连接工具方法
    public static  void    closeConnectionAndChanel(Channel channel, Connection conn){
        try{
            if(channel!=null)channel.close();
            if(conn!=null)conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}