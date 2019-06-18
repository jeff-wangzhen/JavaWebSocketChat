function ajax(options) {
	"use strict";
	var str = changeDataType(options.data);
	var xmlHttpRequest;
	var timer;
	
	if (window.XMLHttpRequest) {
		xmlHttpRequest = new XMLHttpRequest();
	} else {
		xmlHttpRequest = new ActiveXObject('Microsoft.XMLHTTP');
	}
	if (options.type && options.type.toLowerCase() === 'post') {
		xmlHttpRequest.open(options.type, options.url, true);
		// xmlHttpRequest.setRequestHeader('Content-type', 'application/json;charsetset=UTF-8');
		xmlHttpRequest.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xmlHttpRequest.send(str);
	} else {
		xmlHttpRequest.open(options.type, options.url + "?" + str, true);
		xmlHttpRequest.send(null);
	}
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState === 4) {
			clearInterval(timer);
			if (xmlHttpRequest.status >= 200 && xmlHttpRequest.status < 300
					|| xmlHttpRequest.status === 304) {
				// console.log(xmlHttpRequest.responseText);
				options.success(xmlHttpRequest.responseText);
			} else {
				if (options.error) {
					options.error(xmlHttpRequest.status);
				} else {
					console.log('请求失败');
				}
			}
		}
	};
	// 判断是否超时
	if (options.timeout) {
		timer = setInterval(function() {
			alert('网络请求超时');
			xmlHttpRequest.abort();
			clearInterval(timer);
		}, options.timeout);
	}
}

var nextStr = '';

function changeDataType(obj) {
	"use strict";
	var str = '';
	if (typeof obj == 'object') {
		for ( var i in obj) {
			if (typeof obj[i] != 'function' && typeof obj[i] != 'object') {
				str += i + '=' + obj[i] + '&';
			} else if (typeof obj[i] == 'object') {
				nextStr = '';
				str += changeSonType(i, obj[i])
			}
		}
	}
	return str.replace(/&$/g, '');
}

function changeSonType(objName, objValue) {
	"use strict";
	if (typeof objValue == 'object') {
		for ( var i in objValue) {
			if (typeof objValue[i] != 'object') {
				var value = objName + '[' + i + ']=' + objValue[i];
				nextStr += encodeURI(value) + '&';
			} else {
				changeSonType(objName + '[' + i + ']', objValue[i]);
			}
		}
	}
	return nextStr;
}