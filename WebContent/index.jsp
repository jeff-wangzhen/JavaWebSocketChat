<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page language="java" import="java.time.LocalDateTime"%>
<%@ page language="java" import="java.time.ZoneOffset"%>
<%@ page language="java" import="util.OnlineUser"%>
<%@ page language="java" import="web.createAnonymousUser"%>
<%@ page import="domain.User"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	request.getSession(true);
	User userObj = (User) session.getAttribute("userObj");

	if (userObj == null || (userObj.getUpdate() != null && userObj.getUpdate().toString().equals("delete"))) {
		createAnonymousUser.create(application, session);
	}
	userObj = (User) session.getAttribute("userObj");
	OnlineUser.insertUser(application, userObj);
	String userInfo = JSONObject.fromObject(userObj).toString();
	System.out.println("index  " + userObj.getName());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta name="referrer" content="unsafe-url" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="theme-color" content="rgb(204,232,207)" />
<meta name="author" content="王振" />
<meta name="keywords" content="聊天室,JavaWeb,websocket" />
<meta name="description" content="简单的Java+Websocket实现的在线聊天室，借用了教科书、前端模板网站的部分代码。" />
<title>JavaWebSocketChat</title>
<link rel="icon" type="image/png" href="images/favicon.ico">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="assets/ueditor/themes/default/css/ueditor.css">

<script>
	var userObj = JSON.parse(
<%="'" + userInfo + "'"%>
	);

	if (userObj === null) {
		userObj = {
			name : "匿名用户" + Date.now() + Math.floor((Math.random() * 1000)),
			portrait : "portraits/client.jpg"
		}
	}
</script>
</head>
<body id="body">
	<noscript>请启用JavaScript脚本后刷新页面</noscript>

	<header class="am-topbar am-topbar-fixed-top">
	<div class="am-container">
		<h1 class="am-topbar-brand">
			<a href="#">聊天室</a>
		</h1>
		<div id="publicity">准备连接……</div>
		<a id="collapse" class="triangle-up"></a>
		<div class="am-collapse am-topbar-collapse" id="collapse-head">
			<c:choose>
				<c:when test="${ !empty userObj  && userObj.logined }">
					<div class="am-topbar-right">
						<a id="destroy" href="SessionDestroy">
							<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
								<span class="am-icon-sign-out"></span> 注销
							</button>
						</a>
					</div>
					<div class="am-topbar-right">
						<a href="modifyInfo.jsp">
							<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
								<span class="am-icon-pencil"></span> 修改信息
							</button>
						</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="am-topbar-right">
						<a href="register.html">
							<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
								<span class="am-icon-pencil"></span> 注册
							</button>
						</a>
					</div>
					<div class="am-topbar-right">
						<a href="login.html">
							<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
								<span class="am-icon-sign-in"></span> 登录
							</button>
						</a>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="am-topbar-right">

				<a href="#">
					<button id="userListBtn" onclick="getUserList()" type="button"
						class="am-btn am-btn-secondary am-topbar-btn am-btn-sm"
						data-am-modal="{target: '#my-popup'}">
						<span class="am-icon-users"></span> 在线用户
					</button>
				</a>
			</div>
			<div class="am-topbar-right">

				<a target="_blank"
					href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=kill370354@qq.com">
					<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
						<span class="am-icon-pencil-square-o"></span> 反馈
					</button>
				</a>
			</div>
			<div class="am-topbar-right">

				<a target="_blank"
					href="https://github.com/kill370354/JavaWebSocketChat">
					<button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm">
						<span class="am-icon-github"></span> GitHub
					</button>
				</a>
			</div>

		</div>
	</div>
	</header>
	<!-- 聊天内容展示区域 -->

	<div id="box">
		<div id="ChatBox" class="am-g am-g-fixed">
			<div class="am-u-lg-12">
				<ul id="chatContent" class="am-comments-list am-comments-list-flip">
					<li class="am-comment" onmouseenter="showUserInfo(this)"
						onmouseleave="hideUserInfo(this)" style="display: none;"><a
						href="#"> <img class="am-comment-avatar" id="portrait"
							src="portraits/client.jpg" alt="" />
							<div class="gender">
								<c:choose>
									<c:when test='${userObj.getGender().equals("男")}'>
                                ♂
                            </c:when>
									<c:when test=' ${userObj.getGender().equals("女")}'>
                                ♀
                            </c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</div>
					</a> <%--							<div id="edui301" class="edui-message edui-default" style="display: block;">sdfsd</div>--%>
						<div class="userInfo">
							<div ff="username"></div>
							<div ff="usergender"></div>
							<div ff="logined"></div>
							<div ff="user-add-friend" onclick="requestFriend(this)">添加好友</div>
						</div>
						<div class="am-comment-main">
							<header class="am-comment-hd">
							<div class="am-comment-meta">
								<div ff="id"></div>
								<a ff="nickname" href="#link-to-user" class="am-comment-author">某人</a>
								<time ff="msgdate" datetime="" title="">2014-7-12 15:30</time>
							</div>
							</header>
							<div ff="content" class="am-comment-bd">此处是消息内容</div>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 聊天内容发送区域 -->
	<div id="editboxdiv">
		<div id="EditBox" class="am-g am-g-fixed">
			<!--style给定宽度可以影响编辑器的最终宽度-->
			<script type="text/plain" id="myEditor"
				style="width:100%;height:140px;"></script>
			<button id="send" type="button"
				class="am-btn am-btn-secondary am-btn-block">发送</button>
		</div>
	</div>
	<div class="am-popup" id="my-popup">
		<div class="am-popup-inner">
			<div class="am-popup-hd">
				<h4 class="am-popup-title">在线用户列表</h4>
				<span data-am-modal-close class="am-close">&times;</span>
			</div>
			<div class="am-popup-bd"></div>
		</div>
	</div>
<script src="assets/js/jquery2.2.0.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="assets/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="assets/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		var url;
		url = "ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/websocket";

		//url="ws://chat.colynlu.cn/websocket";//反向代理麻烦，最后发布到公网上用了这个，优先使用上面的
	</script>
	<script src="js/index.js"></script>
</body>
</html>
