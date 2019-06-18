var socket;
var lockReconnect;
var flagScroll = true;
var flag;
var p = 0;
var t = 0;
var ele = document.getElementById("ChatBox");
var worker;
$(ele).scroll(
		function(e) {
			p = $(ele).scrollTop();
			if (t <= p) {
				var height = $(ele)[0].scrollTop + $(ele)[0].offsetHeight;
				if (height <= (ele.scrollHeight + 5)
						&& height >= (ele.scrollHeight - 5)) {
					flagScroll = true;
				}
			} else {
				flagScroll = false;
			}
			t = p;
		});

function createWebSocket(url) {
	"use strict";
	$("#publicity").text("正在连接……");
	try {
		socket = new WebSocket(url);
		lockReconnect = true;
		socket.onopen = function() {
			$("#publicity").text("连接正常");
			var obj = JSON.stringify({
				type : "welcome",
				name : userObj.name,
				id : userObj.userId,
				userGender : userObj.userGender
			});
			if (socket.readyState === 1) {
				socket.send(obj);
				// console.log(obj)
			}
		};
		socket.onclose = function() {
			$("#publicity").text("连接关闭");
			lockReconnect = false;
			reconnect(url);
		};
		socket.onerror = function(data) {
			$("#publicity").text("连接发生了错误");
		};
		flag = true;
		socket.onmessage = function(ev) {
			var obj = JSON.parse(ev.data);
			if (obj.type === "message") {
				addMessage(obj);
			} else if (obj.type === "requestFriend") {
				addFriend(obj);
			} else if (obj.type === "confirmFriend") {
				alert("您与" + obj.fromName + "已成为好友");
			} else if (obj.type === "welcome") {
				if (obj.isSelf) {
					$(
							"<div class='welcome-self'>欢迎 <span>您（" + obj.name
									+ "）</span> 加入聊天</div>").appendTo(
							"#chatContent");
				} else {
					$(
							"<div class='welcome-others'>欢迎 <span>" + obj.name
									+ "</span> 加入聊天</div>").appendTo(
							"#chatContent");
				}
			}
		};
	} catch (e) {
		reconnect(url);
	}
}

function reconnect(url) {
	"use strict";
	if (lockReconnect) {
		return;
	}
	setTimeout(function() {
		createWebSocket(url);
	}, 50);
}

if (!userObj.logined) {
	worker = new Worker("js/work.js");
	worker.onmessage = function(event) {
		if (event.data.timeOut) {
			console.log("SessionDestroy")
			$.post("SessionDestroy", {}, function() {
				console.log(0)
			})
			alert("会话超时！");
			location.reload();
		}
	};
}
$(function() {
	"use strict";
	var um = UE
			.getEditor(
					"myEditor",
					{
						initialContent : "",
						autoHeightEnabled : false,
						toolbar : [
								"source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |",
								"insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize",
								"| justifyleft justifycenter justifyright justifyjustify |",
								"link unlink | emotion image video  | map" ]
					});
	var nickname = userObj.name;
	var userId = userObj.id;
	var userGender = userObj.gender;
	$("#send")
			.click(
					function() {
						if (!userObj.logined) {
							worker.postMessage({
								"cmd" : "reset"
							});
						}
						if (!um.hasContents()) {
							um.focus();
							$(".edui-container").addClass("am-animation-shake");
							setTimeout(
									"$('.edui-container').removeClass('am-animation-shake')",
									1000);
						} else {
							var txt = um.getContent();
							var obj = JSON.stringify({
								type : "message",
								"nickname" : nickname,
								id : userId,
								"userGender" : userGender,
								content : txt,
								portrait : userObj.portrait,
								logined : userObj.logined
							});
							socket.send(obj);
							um.setContent("");
							um.focus();
						}
					});
});

function addMessage(msg, userId) {
	"use strict";
	var box = $(".am-comment").eq(0).clone();
	box.find("[ff='nickname']").html(msg.nickname);
	box.find("[ff='logined']").html(msg.logined);
	if (msg.userGender === "男") {
		box.find(".gender").html("♂");
	} else if (msg.userGender === "女") {
		box.find(".gender").html("♀");
	} else {
		box.find(".gender").html("");
	}
	box.find("[ff='id']").html(msg.id);
	box.find("#portrait").attr("src", msg.portrait);
	box.find("[ff='msgdate']").html(msg.date);
	box.find("[ff='content']").html(msg.content);
	box.addClass(msg.isSelf ? "am-comment-flip" : "");
	box.addClass(msg.isSelf ? "am-comment-warning" : "am-comment-success");
	box.css((msg.isSelf ? "margin-left" : "margin-right"), "20%");
	box.show();
	box.appendTo("#chatContent");
	if (flagScroll) {
		$(ele).stop(true).animate({
			scrollTop : ele.scrollHeight
		}, 1000);
	}
}

function addFriend(obj) {
	"use strict";
	if (userObj.id === obj.toId) {
		if (confirm(obj.fromName + "请求添加你为好友，是否允许？")) {
			var confirmMessage = {
				type : "confirmFriend",
				toId : obj.fromId,
				fromId : userObj.id,
				fromName : userObj.name
			};
			socket.send(confirmMessage);
		}
	}
}

function requestFriend(that) {
	"use strict";
	var requestMessage = {
		type : "requestFriend",
		toId : $(that).parents("li").find("[ff=\"usergender\"]").text(),
		fromId : userObj.id,
		fromName : userObj.name
	};
	socket.send(requestMessage);
}

window.onload = load;
var isCollapse = true;

function load() {
	"use strict";
	document.getElementById("box").style.display = "block";
	document.getElementById("editboxdiv").style.display = "block";
	document.getElementsByTagName("header")[0].style.display = "block";
	lockReconnect = false;
	createWebSocket(url);
	var btn = document.getElementById("collapse");
	var collapseDiv = document.getElementById("collapse-head");
	var btnStyle = collapseDiv.currentStyle ? btn.currentStyle : window
			.getComputedStyle(collapseDiv, false);
	btn.addEventListener("click", function(ev) {
		if (btnStyle["display"] === "none") {
			$(collapseDiv).slideDown();
			btn.className = "triangle-up  rotate1";
			isCollapse = true;
		} else {
			$(collapseDiv).slideUp();
			btn.className = "triangle-up rotate2";
			isCollapse = false;
		}
	});
	if (document.getElementById("destroy")) {
		document.getElementById("destroy").addEventListener("click",
				function(e) {
					if (!confirm("确定要注销吗？")) {
						if (e.preventDefault) {
							e.preventDefault();
						} else {
							window.event.returnValue = false;
						}
						return false;
					}
				});
	}
	$(".am-comment-avatar").mouseover(function(e) {
		"use strict";
		var userId = $(that).parents(".am-comment").find("[ff='id']").text();
		$.post("UserServlet?method=query", {
			id : userId
		}, function(data) {
			var JSONData = JSON.parse(data);
			var box = $("#userInfo").clone();
			box.find(".username").text(JSONData.name);
			box.find(".usergender").text(JSONData.gender);
			box.appendTo("body");
			box.show();
		});
	})
}

function getUserList() {
	"use strict";
	$(".am-popup-bd").html("正在查询……");
	$.get("userList.jsp", {}, function(data) {
		$(".am-popup-bd").html(data);
	})
}

var box;
vari = 0;
var li;

function hideUserInfo(that) {
	"use strict";
	$(that).find(".userInfo").css("display", "none");
}

function showUserInfo(that) {
	"use strict";
	if ($(that).find("[ff='logined']").text() !== "")
		$(that).find(".userInfo").css("display", "block");
}

// $(document).ready(function(){
var timeOut = setTimeout(ctrlEnterSubmit, 1000);

function ctrlEnterSubmit() {
	timeOut = setTimeout(ctrlEnterSubmit, 1000);

	var inputBody = document.getElementById("ueditor_0").contentWindow.document
			.getElementsByTagName("body")[0];
	inputBody.addEventListener('keydown', function(e) {
		if (!e.repeat) {
			if (e.key === "Control") {
				controlFlag = true;
			} else if (e.key === "Enter" && controlFlag) {
				document.getElementById("send").click();
			}
		}
	});
	inputBody.addEventListener('keyup', function(e) {
		if (e.key === "Control") {
			controlFlag = false;
		}
	});

	clearTimeout(timeOut);
	timeOut = null;

}