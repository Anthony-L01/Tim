# 模仿Tim的前后端分离网页聊天系统（后端部分）

## 一.介绍

目标：模仿一个类似Tim基本功能的web端聊天系统网页

开发模式：前后端分离

接口文档见目录下tim.md

## 二.目录结构

```markdown
tim
├── Component    --  通信模块
├── config        --  配置模块
├── controller    --  控制器模块
├── entity        --  实体类模块
├── filter        --  自定义拦截器模块
├── handler       --  处理器模块（自定义Security认证失败提示信息）
├── mapper        --  框架核心模块
├── service       --  服务模块
└── util          --  工具类模块

```

## 三.技术介绍

后端：SpringBoot + Mybatis + SpringSecurity + Mysql + Redis + Websocket



## 四.运行环境

服务器：阿里云2核2G Centos7.9

对象存储：Minio



## 五.开发环境

|开发工具|说明|
|:-:|:-:|
|IDEA|Java开发工具IDE|
|DataGrip|MySQL远程连接工具|
|X-shell|Linux远程连接工具|
|FileZilla|Linux文件上传工具|

|开发环境|版本|
|:-:|:-:|
|JDK|1.8|
|MYSQL|5.7.33|
|Redis|6.2.6|


## 六.功能介绍

### 注：除了用户注册和用户登录，访问其他接口皆需要在请求头中带上token

 1.用户登录

用户登录时会生成JWT，用于身份信息存储与登录与否的辨别。

 2.用户注册

 3.用户添加好友

 4.用户删除好友

 5.用户退出

 6.获取用户好友列表

 7.用户加入群聊

 8.用户退出群聊

 9.上传用户头像

 10.聊天时发送图片以及图片下载

 11.聊天时给对方发音乐及下载

 12.聊天时给对方发视频及下载

 13.一对一聊天

 14.群聊



## 七.聊天功能框架的构建

聊天功能是基于websocket协议实现的。

实现了**实时通信**与**离线消息的缓存**。

以**用户登录通信系统**为主线，**一对一聊天**与**群聊**为分支。

一对一聊天与群聊必须以**用户登录了通信系统为前提**。



接下来是通信功能的测试图片

### 1.用户登录通信系统

![用户d登录.png](https://github.com/Anthony-L01/Picture1/raw/master/Tim/用户d登录.png)

### 2.用户一对一通信(可以接收离线消息)

发消息

![一对一通信发消息](https://github.com/Anthony-L01/Picture1/raw/master/Tim/一对一通信发消息.png)

接收消息

![一对一通信接收消息](https://github.com/Anthony-L01/Picture1/raw/master/Tim/一对一通信接收消息.png)

### 3.用户群聊通信（可以接收离线消息）

用户d上线，会提示d上线，展示群当前在线用户列表

![群聊用户d上线](https://github.com/Anthony-L01/Picture1/raw/master/Tim/群聊用户d上线.png)

用户b上线，会提示b上线，展示群当前在线用户列表。

b发送消息，b与d都会接收到。

![群聊用户b上线发消息](https://github.com/Anthony-L01/Picture1/raw/master/Tim/群聊用户b上线发消息.png)

用户c上线，会提示c上线，展示群当前在线用户列表。

c会接受到历史b发送的消息

![群聊用户c上线接收离线消息](https://github.com/Anthony-L01/Picture1/raw/master/Tim/群聊用户c上线接收离线消息.png)

d与b下线，c会接收到他们下线的通知，会展示当前群在线用户

![群聊用户d与b下线](https://github.com/Anthony-L01/Picture1/raw/master/Tim/群聊用户d与b下线.png)





## 八.注意事项

项目拉下来之后，需要自己修改minio的配置、mysql数据库配置、redis配置



## 九.建表sql

```java
-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: localhost    Database: tim
-- ------------------------------------------------------
-- Server version	5.7.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `user` varchar(100) NOT NULL,
  `friend` varchar(100) NOT NULL,
  PRIMARY KEY (`user`,`friend`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES ('a','c'),('a','d'),('c','a'),('c','d'),('d','a'),('d','c');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `GroupName` varchar(50) NOT NULL,
  `GroupID` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`GroupID`),
  UNIQUE KEY `Group_GroupName_uindex` (`GroupName`),
  UNIQUE KEY `group_GroupID_uindex` (`GroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES ('test',1,NULL);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) NOT NULL,
  `password` varchar(500) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `OnlineStatus` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account_uindex` (`account`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'a','a','a',NULL,NULL,1),(2,'b','b','b',NULL,NULL,0),(3,'c','c','c',NULL,NULL,0),(5,'123','$2a$10$Z1bN7FPY36VUhZQsVXjuT.CmQT91v9B.VBjsCgBV3DvPESQF6iq6e','lxc',1,'male',0),(6,'d','$2a$10$uzQz9Clu9nJIfzf/pZqLheKkfGW2xhuQkSqJ9h63gAFk0zsFoNGoW','lxc',1,'male',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userofgroup`
--

DROP TABLE IF EXISTS `userofgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userofgroup` (
  `GroupID` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) NOT NULL,
  PRIMARY KEY (`GroupID`,`account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userofgroup`
--

LOCK TABLES `userofgroup` WRITE;
/*!40000 ALTER TABLE `userofgroup` DISABLE KEYS */;
INSERT INTO `userofgroup` VALUES (1,'b'),(1,'c'),(1,'d');
/*!40000 ALTER TABLE `userofgroup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-26 20:16:44

```

