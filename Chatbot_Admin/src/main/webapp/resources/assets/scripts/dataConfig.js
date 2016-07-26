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
	var synonym;
	var fromAPI = document.getElementById("ts1");
	var fromLog = document.getElementById("ts2");
	var fromSynonym = document.getElementById("ts3");
	if (fromAPI.checked) {
		api = 'yes';
	}else {
		api = 'no';
	}
	if (fromLog.checked) {
		log = 'yes';
	}else {
		log = 'no';
	}
	if (fromSynonym.checked) {
		synonym = 'yes';
	}else {
		synonym = 'no';
	}
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			swal("Good job!", xmlhttp.responseText, "success");
		}

	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	console.info(param);
	console.info(token);
	xmlhttp.open("POST", "/chatbot_admin/config/sync", true);
	xmlhttp.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("api=" + api + "&log=" + log + "&synonym=" + synonym + "&day=" + day
			+ "&hour=" + hour + "&minute=" + minute + "&" + param + "=" + token);
}