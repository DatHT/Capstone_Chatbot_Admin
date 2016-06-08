var xmlhttp;
var count = 0;
var resultLexical = "";

function insertRowToAddNewPhrase(tableId) {
	var tableElem = document.getElementById(tableId);
	var cells = [];
	cells[0] = "*";
	cells[1] = "<div class='form-group'>"
			+ "<input class='form-control' placeholder='Enter text' id='txtPhraseName'>"
			+ "</div>";
	cells[2] = "<button onclick='addNewPhrase()' type='button' class='btn btn-primary btn-circle'><i class='fa fa-plus'></i></button>";
	cells[3] = "<button type='button' class='btn btn-danger btn-circle'><i class='fa fa-minus'></i></button>";
	addFirstRow(tableElem, cells);
}

function addRows(tableId, data) {
	var jsonData = JSON.parse(data);
	var tableElem = document.getElementById(tableId);
	deleteRows(tableElem);
	for (var i = 0; i < jsonData.entries.length; i++) {
		var cells = [];
		cells[0] = i + 1;
		cells[1] = jsonData.entries[i].value;
		cells[2] = "<button type='button' class='btn btn-warning btn-circle'><i class='fa fa-pencil'></i></button>";
		cells[3] = "<button type='button' class='btn btn-danger btn-circle'><i class='fa fa-minus'></i></button>";
		addRow(tableElem, cells);
	}

}

function addRow(table, cells) {

	var newRow = table.insertRow(table.rows.length);
	var newCell;
	for (var i = 0; i < cells.length; i++) {
		newCell = newRow.insertCell(newRow.cells.length);
		newCell.innerHTML = cells[i];
	}
}

function addFirstRow(table, cells) {

	var newRow = table.insertRow(0);
	var newCell;
	for (var i = 0; i < cells.length; i++) {
		newCell = newRow.insertCell(newRow.cells.length);
		newCell.innerHTML = cells[i];
	}
}

function deleteRows(table) {
	while (table.rows.length > 0) {
		table.deleteRow(0);
	}
}

function loadPharse(id) {

	document.getElementById("tableHeader").innerHTML = id.options[id.selectedIndex].text;

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			resultLexical = xmlhttp.responseText;
			addRows("lexicalTable", resultLexical);
		}

	}
	xmlhttp.open("GET", "/chatbot_admin/lexical/" + id.value, true);
	xmlhttp.send();

}

function addNewPhrase() {
	var name = document.getElementById("txtPhraseName").value;
	var cate = document.getElementById("selectCategory");
	var categoryId = cate.options[cate.selectedIndex].value;
	if(name != "") {
		if(categoryId != "") {
			//action here
			if (window.XMLHttpRequest) {
				xmlhttp = new XMLHttpRequest();
			} else
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					alert(xmlhttp.responseText);
					loadPharse(cate);
				}

			}
			xmlhttp.open("POST", "/chatbot_admin/lexical/add" , true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			xmlhttp.send("name=" + name + "&id=" + categoryId);
			//action here
			
		}else {
			alert("Please select Lexical Category");
		}
	}else {
		alert("Please input a phrase");
	}


}