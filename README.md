# [JavaWebSocketChat](https://github.com/kill370354/JavaWebSocketChat)
  这是使用JSP+WebSocket制作的在线聊天室。代码本是从网上找到，自己修改了一些。效果仍不算理想，许多功能未曾实现，比如添加好友等。

## 目前只有以下功能
- 登录、注册（注册需要邮箱验证）
- 聊天室聊天，使用了Ueditor
- 修改个人资料
- 显示在线用户

## 计划但尚未实现的功能
- 添加好友
- 私聊
- 过滤某人的信息
- 保存聊天记录

做添加好友的时候，因为websocket里onmessage事件调用send()方法会导致连接断开，就没有继续了，日后有更好的办法会完善。

## 自己所做的努力
1. 聊天界面
    1. 代码来源 https://gitee.com/loopcc/WebSocketChat/ 
    2. 固定了底部输入框所占高度，让页面占满屏幕，聊天记录部分高度为屏幕高度-顶部高度-底部高度
    3. Ctrl+Enter快捷键发送消息，但是一定要先按下Ctrl再按Enter
    
2. 登录页面
    1. 代码来源 http://www.jsdaima.com/webpage/1009.html
    2. 这个页面未改动太多，就连验证登录是否成功的提示也只是一个警告框

3. 注册页面
    1. 代码来源 http://www.jsdaima.com/webpage/1248.html
    2. 把背景换成了登录页面的
    3. 把手机号码验证换成邮箱验证
    4. js逻辑十分复杂，比如验证邮箱已经存在等
    
4. 修改信息页面
    1. 用的就是登录注册页面的模板，只是用ajax提交修改
    2. 使用了Bootstrap的导航菜单
    3. 修改的逻辑基本与注册相似，只是对每一项信息分别进行修改
    
5. JAVA后台
    1. 发送邮件注册
    2. MVC模式增删查改，代码来源于模板，自己完善了一些
    3. 查看在线用户的页面是抽出来的，点击按钮后发送ajax获取并显示，以实时更新
    4. 过滤器，不登录无法修改信息，会跳转到登录页面

    
## 不完善的地方
- 每次发消息，头像文件路径都随着消息传递，这样每次都会从服务器请求头像文件，可能存在性能问题
- 为了方便，只在少数地方使用JS严格模式，并且未完全遵循jslint规范，因此大部分代码并不规范

## 使用须知
- 仅有注册并登录后才能修改信息，但是不登录也能以匿名身份进入聊天室
- 注册有邮箱验证码验证，需要更改src/mail/SendMailText.java授权邮箱
- 数据库sql文件在data文件夹中
- 不保证修改了以上配置之后必定能正常运行
- 就算能运行，也可能会乱码

  代码不是很完善，希望大家发现问题后能够告诉我，邮箱：[kill370354@qq.com](mailto:kill370354@qq.com)。