# 登录接口
-   登录用户



## 请求URL
- http://localhost:8081/Tim/UserLogin



## HTTP请求方式
-  POST 



## 支持格式
-   application/json



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :------: | :--: | :----: | :-----: |
| account | √ | String | 账号 |
| password | √ | String | 密码 |



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |




## 接口示例
### URL

```markdown
localhost:8081/Tim/UserLogin
```

### Request

```markdown
{
    "account":"123",
    "password":"123456"
}
```

### Response

```markdown
{
    "code": 200,
    "message": "登录成功",
    "data": [
        {
            "id": 5,
            "username": "lxc",
            "password": "123456",
            "account": "123",
            "sex": "male",
            "age": 1,
            "onlineStatus": 0
        },
        {
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjY2RhYWQ2MTIzNmY0MWUxOWRiODhkMmNjYTk0ODUzNyIsInN1YiI6IjEyMyIsImlzcyI6InNnIiwiaWF0IjoxNjQ1ODA1NTY4LCJleHAiOjE2NDU4MDkxNjh9.GGsAS8VOzLk0f59UlLIPEH2Hx7SF1U5KQkFAFVspqa8"
        }
    ]
}
```








# 注册接口
-  用户注册



## 请求URL
- http://localhost:8081/Tim/UserRegister



## HTTP请求方式
-  POST



## 支持格式
-   application/json



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :------: | :--: | :----: | :-----: |
| account | √ | String | 账号 |
| password | √ | String | 密码 |
| username | √ | String | 用户名 |
| age | √ | int | 年龄 |
| sex | √ | String | 性别(male/female) |

​	

## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
localhost:8081/Tim/UserRegister
```

### Request
```markdown
{
    "account":"123",
    "password":"123456",
    "username":"lxc",
    "age":1,
    "sex":"male"
}
```
### Response
```markdown
{
    "code": 200,
    "msg": "注册成功",
    "data": {
        "id": 5,
        "username": "lxc",
        "password": "123456",
        "account": "123",
        "sex": "male",
        "age": 1,
        "onlineStatus": 0
    }
}
```






# 添加好友接口
-   添加好友



## 请求URL
- localhost:8081/Tim/addFriend?UserAccount={UserAccount}&FriendAccount={FriendAccount}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| UserAccount | √ | String | 当前用户账号 |
| FriendAccount | √ | String | 好友的用户账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||




## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |


## 接口示例
### URL

```markdown
localhost:8081/Tim/addFriend?UserAccount=a&FriendAccount=b
```

### Request

```markdown
需要在Headers中添加用户登录时返回的token
```

### Response

```markdown
{
    "code": 200,
    "message": "添加好友成功"
}
```






# 删除好友接口
-   删除好友



## 请求URL
- localhost:8081/Tim/deleteFriend?UserAccount={UserAccount}&FriendAccount={FriendAccount}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| UserAccount | √ | String | 当前用户账号 |
| FriendAccount | √ | String | 好友账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例

### URL

```markdown
localhost:8081/Tim/deleteFriend?UserAccount=a&FriendAccount=b
```

### Request

```markdown
headers中需要添加token
```

### Response

```markdown
{
    "code": 200,
    "message": "删除好友成功"
}
```






# 用户退出接口
-   用户退出



## 请求URL
- localhost:8081/Tim/UserLogout



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |


## 接口示例
### URL

```markdown
localhost:8081/Tim/UserLogout
```

### Request

```markdown
```

### Response

```markdown
{
    "code": 200,
    "message": "退出成功"
}
```






# 获取好友列表接口
-   获取好友列表



## 请求URL
- localhost:8081/Tim/getFriends?account={account}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| account | √ | String | 用户账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
localhost:8081/Tim/getFriends?account=a
```

### Request

```markdown
headers中加token
```

### Response

```markdown
[
    {
        "id": 3,
        "username": "c",
        "password": "c",
        "account": "c",
        "sex": null,
        "age": 0,
        "onlineStatus": 0
    },
    {
        "id": 6,
        "username": "lxc",
        "password": "$2a$10$uzQz9Clu9nJIfzf/pZqLheKkfGW2xhuQkSqJ9h63gAFk0zsFoNGoW",
        "account": "d",
        "sex": "male",
        "age": 1,
        "onlineStatus": 0
    }
]
```






# 用户加入群聊接口
-   用户加群



## 请求URL
- localhost:8081/Tim/joinGroup?UserAccount={UserAccount}&GroupID={GroupID}



## HTTP请求方式
-  POST



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| UserAccount | √ | String | 用户账号 |
| GroupID | √ | String | 群聊ID |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
localhost:8081/Tim/joinGroup?UserAccount=a&GroupID=1
```

### Request

```markdown
headers中加token
```

### Response

```markdown
{
    "code": 200,
    "message": "加入群聊成功"
}
```




# 用户退出群聊接口
-   用户退出群聊



## 请求URL
- localhost:8081/Tim/leaveGroup?UserAccount={UserAccount}&GroupID={GroupID}



## HTTP请求方式
-  POST



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| UserAccount |  |  |  |
| GroupID | int |  |  |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
localhost:8081/Tim/leaveGroup?UserAccount=b&GroupID=1
```

### Request

```markdown
headers中加token
```

### Response

```markdown
{
    "code": 200,
    "message": "退出群聊成功"
}
```




# 上传头像

- localhost:8081/uploadheadpic?file= &username=张三(当前用户的名字)

## 请求方式

- GET

## 参数

| 参数名   | 必选 | 类型           | 说明       |
| :------- | :--- | :------------- | ---------- |
| file     | 是   | Multipart File | 上传的文件 |
| username | 是   | string         | 用户名     |

## 返回类型

上传成功--上传到服务器

上传图片类型jpg/png/bmp--上传的格式不是图片

 上传失败--异常

**备注**：

前端Tymeleaf上传模板

```
<form th:action="@{/uploadheadpic}" method="post" enctype="multipart/form-data">
    <!--    用户名： <input type="text"name="username">-->
    <input  type="file" id="uploadmp4" name="file" />
    <input type="text" name="username">
    <input type="submit"th:value="上传头像" >

</form>
```

enctype="multipart/form-data **不加上传图片的时候回报错**





# 聊天时给对方发图片及下载

- localhost:8081/uploadchatpic?file=

## 请求方式

- GET

## 参数

| 参数名 | 必选 | 类型           | 说明       |
| :----- | :--- | :------------- | ---------- |
| file   | 是   | Multipart File | 上传的文件 |

## 返回类型

上传成功返回上传成功的时间(这个时间就是存储的名字)

<font color=red>比如</font>：2022-02-26:01:34:05

然后对面下载聊天图片

```
//下载聊天图片的接口

localhost:8081/downloadchatpic?filename=2022-02-26:01:34:05
```

上传图片类型jpg/png/bmp--上传的格式不是图片

 上传失败--异常

**备注**：

enctype="multipart/form-data **不加上传图片的时候回报错**





# 聊天时给对方发音乐及下载

- localhost:8081/uploadmp3?file=

## 请求方式

- GET

## 参数

| 参数名 | 必选 | 类型           | 说明       |
| :----- | :--- | :------------- | ---------- |
| file   | 是   | Multipart File | 上传的文件 |

## 返回类型

上传成功返回上传成功的时间(这个时间就是存储的名字)

<font color=red>比如</font>：2022-02-26:01:34:05

然后对面下载音乐

```
//下载聊天图片的接口

localhost:8081/downloadmp3?filename=2022-02-26:01:57:36
```



上传文件类型mp3--上传的格式不是MP3

 上传失败--异常

**备注**：

enctype="multipart/form-data **不加上传图片的时候回报错**





# 聊天时给对方发视频及下载

- localhost:8081/uploadmp4?file=

## 请求方式

- GET

## 参数

| 参数名 | 必选 | 类型           | 说明       |
| :----- | :--- | :------------- | ---------- |
| file   | 是   | Multipart File | 上传的文件 |

## 返回类型

上传成功返回上传成功的时间(这个时间就是存储的名字)

<font color=red>比如</font>：2022-02-26:01:34:05

然后对面下载聊天图片

```
//下载聊天图片的接口

localhost:8081/downloadmp4?filename=2022-02-26:02:00:59
```

上传文件类型mp4--上传的格式不是视频

 上传失败--异常

**备注**：

enctype="multipart/form-data **不加上传的时候会报错**







# 通信用户登录接口
-   通信框架登录



## 请求URL
- ws://localhost:8081/websocket/{useraccount}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| useraccount | √ | String | 用户账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
ws://localhost:8081/websocket/a
```

### Request

```markdown
headers中带token
```

### Response

```markdown
{
    "code": 200,
    "msg": "连接成功"
}
{
    "users": [
        {
            "username": "a"
        }
    ]
}
```








# 一对一通信接口
-   需要在通信useraccount用户登录接口登录后方可用
-   useraccount向friendaccount发消息
-   支持离线消息



## 请求URL
- ws://localhost:8081/websocket/OneToOne/{useraccount}/{friendaccount}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| useraccount | √ | String | 当前用户账号 |
| friendaccount | √ | String | 目标聊天用户账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
ws://localhost:8081/websocket/OneToOne/a/b
```

### Request

```markdown
headers中需要带token
```

### Response

```markdown
```






# 群聊通信接口
-   需要在通信useraccount用户登录接口登录后方可用
-   useraccount向friendaccount发消息
-   支持离线消息



## 请求URL
- ws://localhost:8081/websocket/{GroupID}/{UserAccount}



## HTTP请求方式
-  GET 



## 支持格式
-   



## 参数
| 参数名   | 必选 | 类型   | 说明   |
| :-: | :-: | :-: | :-: |
| GroupID | √ | int | 群ID |
| UserAccount | √ | String | 用户账号 |



## 请求头

|Headers|必选|说明|
| :-:    | :-:    | :-:    |
|token|√||



## 返回字段
| 参数名    | 类型   | 说明   |
| :------: | :----: | :-----: |
| code | int | 状态码，200成功，500失败，401身份未认证 |
| message | String | 响应消息 |
| data | Object | 回传的数据 |



## 接口示例
### URL

```markdown
ws://localhost:8081/websocket/1/a
```

### Request

```markdown
headers中需要加token
```

### Response

```markdown
用户a上线
当前群在线用户列表：[a]
```

















