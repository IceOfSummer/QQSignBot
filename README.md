# QQBot

## 目前支持功能

- 打卡: 群内用户输入打卡完成每日打卡功能
- 忽略 *QQ号* : 仅群内管理员或群主可用，可以让机器人不再统计该用户的打卡情况
- 取消忽略 *QQ号* :  仅群内管理员或群主可用，可以让机器人重新统计该用户的打卡情况
- 提醒未打卡: 仅群内管理员或群主可用，让机器人at所有没有打卡的人
- 打卡情况: 所有人可用，显示所有没有打卡的人(不会at)
- 代打卡 *QQ号* : 群内管理员或群主手动为某人打卡，一般用于服务器出现bug重启后的补救措施 



## 启动步骤

### 1. 克隆本项目

### 2. 进入目录后安装

```text
maven clean package
```

### 3.配置

在你打包后的jar包所在目录下创建一个config目录，里面必须包含两个文件

- application.yaml (你也可以直接复制项目中config目录下的application.yaml作为参考)

  MySql默认连接路径为: ***jdbc:mysql://localhost:3306/qqrobot***

- device.json

  在github上看不到我放了这个文件，但其实是必须要有的，不然你的机器人QQ是登录不上的!



**获取device.json**

前往该网站下载*MiraiAndroid*并导出device.json

[https://mirai.mamoe.net/topic/223/%E6%97%A0%E6%B3%95%E7%99%BB%E5%BD%95%E7%9A%84%E4%B8%B4%E6%97%B6%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88]()



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

bot:
  sign:
    # 指定哪些群执行签到
    targetGroup: [ "123456" ]

```

