function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text");
	ev.target.appendChild(document.getElementById(data));
	// document.getElementById("here").innerHtml = "Dat";
//	var node = document.createElement("DIV");
//	var textnode = document.createTextNode("Dat");
//	alert(data);
//	node.appendChild(textnode);
//	document.getElementById("box-dragable").appendChild(node);
}

var xmlhttp;
var count = 0;
var resultIntents = "";

function addIntentRows(tableId, data) {
	var jsonData = JSON.parse(data);
	var tableElem = document.getElementById(tableId);
	deleteRows(tableElem);
	for (var i = 0; i < jsonData.templates.length; i++) {
		var cells = [];
		cells[0] = i + 1;
		cells[1] = jsonData.templates[i];
		cells[2] = "<button type='button' class='btn btn-warning btn-circle'><i class='fa fa-pencil'></i></button>";
		cells[3] = "<button type='button' class='btn btn-danger btn-circle'><i class='fa fa-minus'></i></button>";
		addRow(tableElem, cells);
	}

}


function loadIntent(id){

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			resultIntents = xmlhttp.responseText;
			addIntentRows("intentTable", resultIntents);
			
		}

	}
	xmlhttp.open("GET", "/chatbot_admin/example/" + id.value, true);
	xmlhttp.send();
}

function insertPattern(tableId) {
	if(resultIntents != "") {
		var tableElem = document.getElementById(tableId);
		var div = document.getElementById("droptarget");
		var divs = div.getElementsByTagName("div");
		var newPattern = "";
		var anyCount = 0;
		for(var i = 0; i < divs.length; i++) {
			var temp = "";
			if(divs[i].innerHTML == 'any') {
				
				if(anyCount == 0) {
					temp = "@sys.any:" + divs[i].innerHTML;
				}else {
					temp = "@sys.any:" + divs[i].innerHTML + anyCount;
				}
				newPattern += temp + " ";
				anyCount++;
			}else {
				newPattern += "@" + divs[i].innerHTML + ":" + divs[i].innerHTML + " ";
			}
			
		}
		if (newPattern != "") {
			
			
			var jsonData = JSON.parse(resultIntents);
			
			delete jsonData.userSays;
			delete jsonData.priority;
			delete jsonData.webhookUsed;
			delete jsonData.lastUpdate;
			delete jsonData.auto;
			jsonData.templates.push(newPattern);
			console.log(JSON.stringify(jsonData));
			
			
			
		}else {
			alert("Please drag an item to the box");
		}
		
	}else {
		alert("Please select intent category first");
	}
	
	
}