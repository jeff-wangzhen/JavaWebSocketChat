<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="domain.User"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	User userObj = (User) session.getAttribute("userObj");
	if (userObj == null || userObj.getName().equals("")) {
		userObj = new User();
		userObj.setName("匿名用户" + System.currentTimeMillis());
		//       userObj.setPassword(password);
	}
	request.getSession().setAttribute("userObj", userObj);
	String userInfo = JSONObject.fromObject(session.getAttribute("userObj")).toString();
%>
<!DOCTYPE html>
<html lang="en">
<head>


<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta name="referrer" content="unsafe-url" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="theme-color" content="rgb(204,232,207)" />
<meta name="author" content="王振" />
<meta name="keywords" content="聊天室,JavaWeb,websocket" />
<meta name="description"
	content="简单的Java+Websocket实现的在线聊天室，借用了教科书、前端模板部分代码。" />
<title>修改信息</title>
<link rel="icon" type="image/png" href="images/favicon.ico">
<link rel="stylesheet" href="assets/css/bootstrap3.3.2.min.css">
<link rel="stylesheet" href="assets/css/cropper.css">
<link rel="stylesheet" media="screen" href="css/login.css">
<link rel="stylesheet" media="screen" href="css/register.css">
<link rel="stylesheet" type="text/css" href="css/reset.css" />

<link rel="stylesheet" type="text/css" href="css/modifyInfo.css" />

<script>
var userObj = JSON.parse(
		<%="'" + userInfo + "'"%>
			);
</script>
</head>
<body>
	<noscript>请启用JavaScript脚本后刷新页面</noscript>
	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modalLabel">裁剪头像</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="img-container">
						<img id="image" src="images/yellow_button.png">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="crop">裁剪</button>
				</div>
			</div>
		</div>
	</div>
	<div id="particles-js">
		<div class="aui-register-popup">
			<div class="aui-register-link fr" id="aui-register-link">

				<a href="index.jsp" target="_self"><span>返回聊天室</span></a>
			</div>
			<div class="aui-register-box">

				<div class="aui-register-form" id="verifyCheck">
					<ul id="myTab" class="nav nav-tabs">

						<li class="active nav-item"><a href="#nicknamediv"
							data-toggle="tab"> 修改昵称 </a></li>
						<li class="nav-item"><a href="#emaildiv" data-toggle="tab">修改邮箱</a></li>
						<li class="nav-item"><a href="#passworddiv" data-toggle="tab">修改密码</a></li>
						<li class="nav-item"><a href="#portraitdiv" data-toggle="tab">修改头像</a></li>
						<li class="nav-item"><a href="#canceldiv" data-toggle="tab">注销账号</a></li>

					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade active in" id="nicknamediv">
							<div class="aui-register-form-item">
								<input type="text" name="username" maxlength="20"
									placeholder="昵称" class="txt03 f-r3 required" tabindex="1"
									data-valid="isNonEmpty||between:3-20||isUname"
									data-error="<i class='icon-tips'></i>您还没有输入昵称||<i class='icon-tips'></i>昵称长度3-20位||<i class='icon-tips'></i>只能输入字母、数字、且以中文或字母开头"
									id="name"> <label class="focus valid"></label>
							</div>
							<div class="aui-register-form-item">

								<input id="nameSubmit" class="aui-btn-reg" placeholder=""
									tabindex="2" readonly="readonly" value="提交">
							</div>
						</div>
						<div class="tab-pane fade " id="emaildiv">

							<div class="aui-register-form-item">
								<input type="email" name="email" placeholder="电子邮箱"
									autocomplete="mail" class="txt01 f-r3 required" keycodes="tel"
									tabindex="3" data-valid="isNonEmpty||isEmail||hasEmail"
									data-error="<i class='icon-tips'></i>请输入邮箱||<i class='icon-tips'></i>电子邮箱格式不正确||<i class='icon-tips'></i>该邮箱已注册"
									id="email"> <label class="focus valid">
									<div class="msg" style="display: none">
										<i class="icon-tips"></i>您还未输入电子邮箱号
									</div>
								</label> <span class="aui-get-code btn btn-gray f-r3 f-ml5 f-size13 "
									id="time_box" disabled="" style="display: none;"></span> <span
									class="aui-get-code btn btn-gray f-r3 f-ml5 f-size13"
									id="verifyYz">获取验证码</span>
							</div>
							<div class="aui-register-form-item">
								<input type="text" placeholder="验证码" maxlength="6"
									id="verification" name="authCode"
									class="txt02 f-r3 f-fl required" tabindex="4"
									data-valid="isNonEmpty||between:6-6"
									data-error="<i class='icon-tips'></i>请填写正确的邮箱验证码||<i class='icon-tips'></i>请填写6位邮箱验证码">
								<label class="focus valid"></label>
							</div>
							<div class="aui-register-form-item">

								<input id="emailSubmit" class="aui-btn-reg" placeholder=""
									tabindex="5" readonly="readonly" value="提交">
							</div>

						</div>

						<div class="tab-pane fade" id="passworddiv">
							<form action="">
								<div class="aui-register-form-item">
									<input type="password" name="oldPassword" placeholder="原密码"
										id="oldPassword" maxlength="20" class="txt04 f-r3 required"
										tabindex="6" style="ime-mode: disabled;"
										onpaste="return  false" autocomplete="off"
										data-valid="isNonEmpty||between:6-16||isOldPassword"
										data-error="<i class='icon-tips'></i>原密码太短，最少6位||<i class='icon-tips'></i>原密码长度6-16位||<i class='icon-tips'></i>原密码错误">
									<label class="focus valid"></label>
								</div>
								<div class="aui-register-form-item">
									<input type="password" name="password" placeholder="设置密码"
										id="password" maxlength="20" class="txt04 f-r3 required"
										tabindex="7" style="ime-mode: disabled;"
										onpaste="return  false" autocomplete="off"
										data-valid="isNonEmpty||between:6-16||level:2"
										data-error="<i class='icon-tips'></i>密码太短，最少6位||<i class='icon-tips'></i>密码长度6-16位||<i class='icon-tips'></i>密码太简单，有被盗风险，建议字母+数字的组合">
									<label class="focus valid"></label>
								</div>
								<div class="aui-register-form-item">
									<input type="password" name="rePassword" id="rePassword" placeholder="确认密码"
										maxlength="20" class="txt05 f-r3 required" tabindex="8"
										style="ime-mode: disabled;" onpaste="return  false"
										autocomplete="off"
										data-valid="isRepeat:password"
										data-error="<i class='icon-tips'></i>两次密码输入不一致"
										> <label class="focus valid"></label>
								</div>

								<div class="aui-register-form-item">

									<input id="passwordSubmit" class="aui-btn-reg" placeholder=""
										tabindex="9" readonly="readonly" value="提交">
								</div>
							</form>
						</div>
						<div class="tab-pane fade" id="portraitdiv">
							<div>
								<!--                <h1>Upload cropped image to server</h1>-->
								<label class="label" data-toggle="tooltip" title="上传头像">
									<img class="rounded" id="avatar"
									src="<%=userObj.getPortrait()%>" alt="avatar"> <input
									type="file" class="sr-only" id="portrait" name="image"
									accept="image/*">
								</label>
								<div class="progress">
									<div
										class="progress-bar progress-bar-striped progress-bar-animated"
										role="progressbar" aria-valuenow="0" aria-valuemin="0"
										aria-valuemax="100">0%</div>
								</div>
								<div class="alert" role="alert"></div>

							</div>
							<div class="aui-register-form-item">

								<input id="portraitSubmit" class="aui-btn-reg" placeholder=""
									tabindex="10" readonly="readonly" value="提交">
							</div>

						</div>
						<div class="tab-pane fade" id="canceldiv">
							<div class="aui-register-form-item">
								<input tabindex="11"  type="checkbox" id="cancelInput" name="cancelInput" />确认注销
								<input id="cancelSubmit" class="aui-btn-reg" placeholder=""
									tabindex="12" readonly="readonly" value="提交">
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="assets/js/jquery3.3.1.min.js"></script>
	<script src="js/modifyInfo.js"></script>
	<script src="js/uploadimage.js"></script>
	<script src="assets/js/particles.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/bootstrap3.3.7.min.js"></script>
	<script src="js/cropper.js"></script>

</body>
</html>
