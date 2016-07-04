var xmlhttp;
var count = 0;
var resultLexical = "";

function insertRowToAddNewPhrase(tableId) {
	var tableElem = document.getElementById(tableId);
	var cells = [];
	cells[0] = "*";
	
	
	

	cells[1] = "<div class='form-group fg-float'>"	
					+ "<div class='fg-line'>"
						+ "<input type='text' class='form-control fg-input' id='txtPhraseName' placeholer='Enter Text' >"
					+ "</div>"
				+ "</div>";
	
	cells[2] = "<button onclick='addNewPhrase()' class='btn palette-Cyan btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-plus-circle-o zmdi-hc-fw'></i></button>";
	cells[3] = "<button class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
	addFirstRow(tableElem, cells);
}



function addFirstRow(table, cells) {

	var newRow = table.insertRow(0);
	var newCell;
	for (var i = 0; i < cells.length; i++) {
		newCell = newRow.insertCell(newRow.cells.length);
		newCell.innerHTML = cells[i];
	}
}

function addRows(tableId, data) {
	var jsonData = JSON.parse(data);
	var tableElem = document.getElementById(tableId);
	deleteRows(tableElem);
	for (var i = 0; i < jsonData.entries.length; i++) {
		var cells = [];
		cells[0] = i + 1;
		cells[1] = jsonData.entries[i].value;
		cells[2] = "<button class='btn btn-warning btn-icon waves-effect waves-circle waves-float'><i class='zmdi zmdi-edit zmdi-hc-fw'></i></button>";
		cells[3] = "<button class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
		addRow(tableElem, cells);
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
			$('#phraseTable').bootgrid("destroy");
			addRows("lexicalTable", resultLexical);
//			var table = document.getElementById('phraseTable');
//			table.bootgrid();
			
			$('#phraseTable').bootgrid({
                css: {
                    icon: 'zmdi icon',
                    iconColumns: 'zmdi-view-module',
                    iconDown: 'zmdi-expand-more',
                    iconRefresh: 'zmdi-refresh',
                    iconUp: 'zmdi-expand-less'
                },
                formatters: {
                    "commandsUpdate": function (column, row) {
                        return "<button class='btn btn-warning btn-icon waves-effect waves-circle waves-float'><i class='zmdi zmdi-edit zmdi-hc-fw'></i></button>";
                    },
                    "commandsDelete": function (column, row) {
                        return "<button class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
                    }
                }
            });
			
		}

	}
	xmlhttp.open("GET", "/chatbot_admin/lexical/" + id.value, true);
	xmlhttp.send();

}

function addNewPhrase() {
	var name = document.getElementById("txtPhraseName").value;
	var cate = document.getElementById("selectCategory");
	var categoryId = cate.options[cate.selectedIndex].value;
	if (name != "") {
		if (categoryId != "") {
			// action here
			if (window.XMLHttpRequest) {
				xmlhttp = new XMLHttpRequest();
			} else
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var result = xmlhttp.responseText;
					if (result.indexOf("success") > -1) {
						swal("Good job!", xmlhttp.responseText, "success");
					}
					if (result.indexOf("wrong") > -1) {
						swal("Error!", xmlhttp.responseText, "error");
					}
					if (result.indexOf("Sorry") > -1) {
						swal("Dupplicate!", xmlhttp.responseText, "info");
					}
					
					loadPharse(cate);
				}

			}
			var param = document.getElementById("paramName").value;
			var token = document.getElementById("token").value;
			xmlhttp.open("POST", "/chatbot_admin/lexical/add", true);
			xmlhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded;charset=utf-8");
			xmlhttp.send("name=" + name + "&id=" + categoryId + "&" + param + "=" + token);
			// action here

		} else {
			alert("Please select Lexical Category");
		}
	} else {
		alert("Please input a phrase");
	}
}





