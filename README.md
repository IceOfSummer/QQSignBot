# QQSignBot

## 分支说明

此页面仅显示master分支没有的内容，master分支有的内容这个分支

为什么要开这个分支?

> 因为有些功能不具有通用性(只针对特定的人群，比如我自己的学习才能使用)，因此放在master分支中不太好，因为该分支的功能对你来说可能是多余的

# 该分支独有特性

因为master分支有的东西该分支都有，因此其它功能请前往master分支查看

### 1. 自动打卡功能

***此功能非通用，仅针对我们自己学校***

**Warn: 使用此功能前须知!**

​	由于学校打卡系统使用了企业微信，虽然企业微信的防范措施做的很好，但是奈何学校的打卡系统拖后腿，因此，要想自动打卡，必须获取你的**Token**! 经过验证，该Token从生成至少有3天的有效期(目测有效期为7天，本来应该是几小时就过期的东西被学校整成了三天都没过期就离谱)

​	暴露了你的Token有什么坏处?

> ​        如果你是我们学校的人，你将Token给我，**我是可以获取你的付款码等功能的**! Token就相当于钥匙，你把你家钥匙给我了，我就可以偷偷去你家干任何事! (**亲测无法打开学校的付款码界面!!! **两者使用的是不一样的验证方式)

​	如果我的Token暴露给了别人，我有办法补救吗?

> Token全称 Json Web Token(JWT)，是一串**无状态**的字符串，一旦服务器分发给用户，除非到达设定的时间，无法让其失效！因此你的Token一旦暴露，建议迅速修改密码，因为Token中一般附带了密码信息(**但是原Token没有失效**！只是里面的信息不正确，可能会被服务器拒绝)。在一般情况下Token有效期一般很短，基本只有几个小时！

​	因此如果你想使用此功能，希望你能知晓如上风险！

相关指令:

*所有指令都是需要私聊机器人才可执行(临时通话也可以)*

- 注册自动打卡 token 学号 电话 家长姓名 家长联系电话: 注册一个自动打卡登记，可以多次执行
- 修改token token: 修改保存在数据库的token
- 手动打卡: 让机器人主动帮你打卡
- 开启自动打卡: 开启每天的自动打卡
- 关闭自动打卡: 关闭每天的自动打卡
- 查看提交信息: 查看你提交到数据库的信息

关于如何获取token，详见下方[获取token](#获取token)

# 其它说明

## 获取token

### 1. 下载HttpCanary

### 2. 打开HttpCanary，点击右下角小飞机开始记录

### 3. 打开企业微信，进入纺大畅行码

### 4.回到HttpCanary，获取Token

点击右上角三个点 -> 搜索'authorization'(不要带引号) -> 随便进入一个企业微信的请求 -> 点击中间的请求 -> 在里面可以找到一个 authorization: xxxxxx，将后面的值复制，提交给机器人(这个东西本质就是Token)

在提交给机器人前，希望你已经充分了解暴露你Token的后果
