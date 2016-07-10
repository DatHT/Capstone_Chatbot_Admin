var xmlhttp;

function applySynchronize() {
	//get time value for scheduler;
	var cateDay = document.getElementById("select-day");
	var day = cateDay.options[cateDay.selectedIndex].value;
	console.info(day);
	var cateHour = document.getElementById("select-hour");
	var hour = cateHour.options[cateHour.selectedIndex].value;
	console.info(hour);
	var cateMinute = document.getElementById("select-minute");
	var minute = cateMinute.options[cateMinute.selectedIndex].value;
	console.info(minute);
	
	
	var api;
	var log;
	var fromAPI = document.getElementById("ts1");
	var fromLog = document.getElementById("ts2");
	if (fromAPI.checked) {
		api = 'yes';
	}
	if (fromLog.checked) {
		log = 'yes';
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
	xmlhttp.send("api=" + api + "&log=" + log + "&day=" + day
			+ "&hour=" + hour + "&minute=" + minute + "&" + param + "=" + token);
}
