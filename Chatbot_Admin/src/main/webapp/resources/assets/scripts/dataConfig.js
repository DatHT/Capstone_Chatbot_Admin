var xmlhttp;

function applySynchronize() {
	var api;
	var database;
	var fromAPI = document.getElementById("ts1");
	var fromDatabase = document.getElementById("ts2");
	if (fromAPI.checked) {
		api = 'yes';
	}
	if (fromDatabase.checked) {
		database = 'yes';
	}	
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			alert(xmlhttp.responseText);
			location.reload();
		}

	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	console.info(param);
	console.info(token);
	xmlhttp.open("POST", "/chatbot_admin/config/sync", true);
	xmlhttp.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("api=" + api + "&db=" + database + "&" + param + "=" + token);
}
