function loadCfs() {
	var config = document.getElementById("pageInfo").value
	var page = document.getElementById("configInfo").value
	var z = document.getElementById("nextPage").value
	if (config==page) {
		if (z != 'N/A') {
			var x = document.getElementById('selectSite');
			var opt = document.createElement('option');
			opt.value = page;
			opt.innerHTML = page;
			// c.text = result[i].getAttribute('site');
			x.appendChild(opt);
		}
		if (z == 'N/A') {
			var x = document.getElementById('selectPage');
			var opt = document.createElement('option');
			opt.value = page;
			opt.innerHTML = page;
			// c.text = result[i].getAttribute('site');
			x.appendChild(opt);
		}

	}
}
function checkReturn(form) {
	var xmlhttp;
	// notify("Product Already Existed!", "warning");
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var result = xmlhttp.responseText;
			if (result.indexOf("success") > -1) {
				notify("Your Parser had been STOP", "info");
				setTimeout(function() {
					window.location.href = "/chatbot_admin/configuration";
				}, 3000);
			}
		}
	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	xmlhttp.open("POST", "/chatbot_admin/closeThread", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("/chatbot_admin/closeThread" + "&" + param + "=" + token);
	return false;
}
function checkSuccess(form) {
	var url = document.getElementById("selectSite").value;
	var numPage = document.getElementById("input-num").value;
	var noPage = document.getElementById("input-no").value;
	var xmlhttp;
	// notify("Product Already Existed!", "warning");
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var result = xmlhttp.responseText;
			if (result.indexOf("success") > -1) {
				notify("Your Parser had been Success parse data", "info");
				setTimeout(function() {
					window.location.href = "/chatbot_admin/configuration";
				}, 3000);
			}
		}
	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	xmlhttp.open("POST", "/chatbot_admin/staticParse", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("/chatbot_admin/staticParse" + "&txtSelectPage=" + numPage
			+ "&txtLinkPage=" + url + "&txtNoPage=" + noPage + "&" + param
			+ "=" + token);
	return false;
}
function checkSuccessDynamic(form) {
	var url = document.getElementById("selectPage").value;
	var time = document.getElementById("times-no").value;
	var xmlhttp;
	// notify("Product Already Existed!", "warning");
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var result = xmlhttp.responseText;
			if (result.indexOf("success") > -1) {
				notify("Your Parser had been Success parse data", "info");
				setTimeout(function() {
					window.location.href = "/chatbot_admin/configuration";
				}, 3000);
			}
		}
	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	xmlhttp.open("POST", "/chatbot_admin/dynamicParse", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("/chatbot_admin/dynamicParse" + "&txtTimes=" + time
			+ "&txtPage=" + url + "&" + param
			+ "=" + token);
	return false;
}