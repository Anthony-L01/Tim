package com.lxc.tim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @author: Anthony
 * @time: 2022/2/20
 */
@Data
@AllArgsConstructor
public class Message {
    String fromUser;
    String time;
    String message;
}
