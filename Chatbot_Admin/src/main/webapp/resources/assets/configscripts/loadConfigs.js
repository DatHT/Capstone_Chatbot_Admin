var regObjCfg, regObjPage;
var xmlDOMCfg, xmlDOMPage;
parser = new DOMParser();
var valueButton;

function getVal() {
	valueButton = document.getElementById("confirmation").value;
	return valueButton;
}
function checkUrl() {
	var url = document.getElementById("input-01").value;
	var rst = "";
	var xmlhttp;
	var x;
	var str_array = url.split("/");
	for (var i = 0; i < 3; i++) {
		rst = rst + str_array[i] + "/";
	}
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var result = xmlhttp.responseText;
			if (result.indexOf("not available") > -1) {
				alert("Inputted Url is not available, please check your input or try with another url");
			}
			if (result.indexOf("Valid") > -1) {
				if (getVal() == "Set List Page") {
					if (regObjPage.search(rst) != -1) {
						if (confirm("This configuration existed. Do you want to config again?") == true) {
							x == 3;
						} else {
							return false;
						}
					}
				}
				return true;
			}
		}
	}
	var param = document.getElementById("paramName").value;
	var token = document.getElementById("token").value;
	xmlhttp.open("POST", "/chatbot_admin/checkUrl", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("url=" + url + "&" + param + "=" + token);
	return false;
}

function loadProcess(selectName) {
	xmlDOMCfg = parser.parseFromString(regObjCfg, "text/xml");
	xmlDOMPage = parser.parseFromString(regObjPage, "text/xml");
	if ((!regObjCfg) || (!regObjPage)) {
		return false;
	}
	if ((regObjCfg) && (regObjPage)) {
		xmlDOMCfg.async = false;
		xmlDOMPage.async = false;
		loadNode(xmlDOMCfg, xmlDOMPage, selectName);
	}
}

function loadNode(nodeCfg, nodePage, selectName) {
	if ((nodeCfg == null) || (nodePage == null)) {
		return;
	}
	var resultCfg = nodeCfg.documentElement.childNodes;
	var resultPage = nodePage.documentElement.childNodes;
	for (var i = 0; i < resultCfg.length; i++) {
		for (var j = 0; j < resultPage.length; j++) {
			if (resultCfg[i].getAttribute('site') == resultPage[j]
					.getAttribute('site')) {
				var nextPageNode = resultPage[j]
						.getElementsByTagName('nextPage')[0];
				var y = nextPageNode.childNodes[0];
				var z = y.nodeValue;
				if (z != 'N/A') {
					var x = document.getElementById('selectSite');
					var opt = document.createElement('option');
					opt.value = resultPage[j].getAttribute('site');
					opt.innerHTML = resultPage[j].getAttribute('site');
					// c.text = result[i].getAttribute('site');
					x.appendChild(opt);
				}
				if (z == 'N/A') {
					var x = document.getElementById('selectPage');
					var opt = document.createElement('option');
					opt.value = resultPage[j].getAttribute('site');
					opt.innerHTML = resultPage[j].getAttribute('site');
					// c.text = result[i].getAttribute('site');
					x.appendChild(opt);
				}

			}
		}
	}
}

function checkInputConfig(form) {
	var flagRedirect = 0;
	var str = document.getElementById("input-01").value;
	var result = "";
	var x;
	var str_array = str.split("/");
	for (var i = 0; i < 3; i++) {
		result = result + str_array[i] + "/";
	}
	if (getVal() == "Set List Page") {
		if (regObjPage.search(result) != -1) {
			swal(	
					{
						title : "EXISTED CONFIG",
						text : "Config for this page is existed! Do you want to update?",
						type : "success",
						showCancelButton : true,
						confirmButtonColor : "#DD6B55",
						confirmButtonText : "Ok!",
						closeOnConfirm : false
					},
					  function(){
						form.submit();
					});
		}else {
			return false;
		}
	}
	return false;

}