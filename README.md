# QQSignBot

## 目前功能

### 目前可用交互功能

- 打卡: 群内用户输入打卡完成每日打卡功能
- 忽略 *QQ号* : 仅群内管理员或群主可用，可以让机器人不再统计该用户的打卡情况
- 取消忽略 *QQ号* :  仅群内管理员或群主可用，可以让机器人重新统计该用户的打卡情况
- 提醒未打卡: 仅群内管理员或群主可用，让机器人at所有没有打卡的人
- 打卡情况: 所有人可用，显示所有没有打卡的人(不会at)
- 代打卡 *QQ号* : 群内管理员或群主手动为某人打卡，一般用于服务器出现bug重启后的补救措施 

### 其它定时任务

- 在每天10点,11点,12点会提醒所有没有打卡的人进行打卡
- 在每天0点,提醒所有人开始打卡,并发送当天天气预报(需要配置)



## 启动步骤

### 1. 克隆本项目

### 2. 进入目录后安装

```text
maven clean package
```

### 3.配置

在你打包后的jar包所在目录下创建一个config目录，里面必须包含两个文件

- application.yaml ~~(你也可以直接复制项目中config目录下的application.yaml作为参考)~~

  MySql默认连接路径为: ***jdbc:mysql://localhost:3306/qqrobot***(当然也可以自己配置)

- device.json

  在github上看不到我放了这个文件，但其实是必须要有的，不然你的机器人QQ是登录不上的!



**获取device.json**

 1. [点我前往该网站下载*MiraiAndroid*](https://mirai.mamoe.net/topic/223/%E6%97%A0%E6%B3%95%E7%99%BB%E5%BD%95%E7%9A%84%E4%B8%B4%E6%97%B6%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88)
 2. 安装并打开
 3. 点击右上角三个点 -> 设置自动登录 -> 登录你的机器人
 4. 打开左拉菜单 -> 工具 -> 选择一个机器人 -> 导出DEVICE.JSON
 5. 将DEVICE.JSON放到config目录下





**application.yaml样例**

```yaml
spring:
  # 数据库连接的用户名和密码
  datasource:
    username: "root"
    password: "abc123"
  profiles:
    active: pro

simbot:
  core:
    # bot的用户名和密码
    bots: 123:password
    
# 天气api,详见: http://tianqiapi.com/index/doc?version=v6
weather:
  url: https://tianqiapi.com/api
  params: {
    appid: 123456,
    appsecret: secretKey,
    version: v6,
    cityid: 101200101
  }

# 已经废弃,现在机器人加入所有群聊都有效
# bot:
#   sign:
#     # 指定哪些群执行签到
#     targetGroup: [ "123456" ]

```

