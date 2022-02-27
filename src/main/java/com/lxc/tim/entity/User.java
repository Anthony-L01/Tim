package com.lxc.tim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/21
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    int id;
    String username;
    String password;
    String account;
    String sex;
    int OnlineStatus;
    int age;
    public User(String username,String password,String account,String sex,int age)
    {
        this.username=username;
        this.password=password;
        this.account=account;
        this.sex=sex;
        this.age=age;
    }

}
