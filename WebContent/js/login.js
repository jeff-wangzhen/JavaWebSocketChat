(function() {
	"use strict";
	document.getElementById("particles-js").style.display = "block";
	var check = "false";
	function hasClass(elem, cls) {
		cls = cls || "";
		if (cls.replace(/\s/g, "").length === 0)
			return false;
		return new RegExp(" " + cls + " ").test(" " + elem.className + " ");
	}

	function addClass(ele, cls) {
		if (!hasClass(ele, cls)) {
			ele.className = ele.className === "" ? cls : ele.className + " "
					+ cls;
		}
	}

	function removeClass(ele, cls) {
		if (hasClass(ele, cls)) {
			var newClass = " " + ele.className.replace(/[\t\r\n]/g, "") + " ";
			while (newClass.indexOf(" " + cls + " ") >= 0) {
				newClass = newClass.replace(" " + cls + " ", " ");
			}
			ele.className = newClass.replace(/^\s+|\s+$/g, "");
		}
	}

	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(";");
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i].trim();
			if (c.indexOf(name) === 0)
				return c.substring(name.length, c.length);
		}
		return "";
	}

	function setCookie(cname, cvalue, exdays) {
		var d = new Date();
		d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = cname + "=" + cvalue + "; " + expires;
	}

	document.querySelector(".login-button").onclick = submitLogin;
	function submitLogin() {
		if (document.getElementById("autoLogin").checked) {
			var email = document.getElementsByName("email")[0].value;
			var password = document.getElementsByName("password")[0].value;
			setCookie("email", email, 30);
			setCookie("password", password, 30);
		} else {
			document.cookie = "email=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
			document.cookie = "password=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		}
		ajax({
			type : "post",
			url : "CheckLogin",
			data : {
				hadChecked : check,
				email : document.getElementById("email").value,
				password : document.getElementById("password").value
			},
			success : function(data) {
				var JSONData = JSON.parse(data);
				if (JSONData["check"]) {
					if (confirm(JSONData["check"])) {
						check = "true";
						submitLogin();
					}
				} else if (JSONData["error"]) {
					alert(JSONData["error"]);
				} else if (JSONData["success"]) {
					window.location.href = JSONData["url"];
				}
			}
		});
		return false;
	}
	;

	window.onload = function() {
		document.getElementsByName("email")[0].value = getCookie("email");
		document.getElementsByName("password")[0].value = getCookie("password");
	}
})();