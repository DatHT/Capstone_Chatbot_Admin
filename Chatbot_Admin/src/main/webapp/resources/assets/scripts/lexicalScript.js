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
			+ "</div>" + "</div>";

	cells[2] = "<button onclick='addNewPhrase()' class='btn palette-Cyan btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-plus-circle-o zmdi-hc-fw'></i></button>";
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
		cells[2] = "<button onclick='showDeleteModal(`" + jsonData.entries[i].value + "`)' class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
		addRow(tableElem, cells);
	}

}

function loadPharse(id) {
	$('#loadingModal').modal('show');
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
			// var table = document.getElementById('phraseTable');
			// table.bootgrid();

			$('#phraseTable')
					.bootgrid(
							{
								css : {
									icon : 'zmdi icon',
									iconColumns : 'zmdi-view-module',
									iconDown : 'zmdi-expand-more',
									iconRefresh : 'zmdi-refresh',
									iconUp : 'zmdi-expand-less'
								},
								formatters : {
									"commandsDelete" : function(column, row) {
										return "<button onclick='showDeleteModal(`"
												+ row.name
												+ "`)' class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
									}
								}
							});
			$('#loadingModal').modal('hide');
		}

	}
	xmlhttp.open("GET", "/chatbot_admin/lexical/" + id.value, true);
	xmlhttp.send();

}

function deletePharse() {
	$('#deleteModal').modal('hide');
	var name = document.getElementById("deletePhraseName").innerHTML;
	var cate = document.getElementById("selectCategory");
	var categoryName = cate.options[cate.selectedIndex].text;
	console.info(categoryName);
	if (categoryName != "") {
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
		xmlhttp.open("POST", "/chatbot_admin/lexical/delete", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded;charset=utf-8");
		xmlhttp.send("name=" + name + "&lexicalName=" + categoryName + "&" + param + "="
				+ token);
		// action here

	} else {
		notify("Please select Lexical Category", "info");
	}

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
			xmlhttp.send("name=" + name + "&id=" + categoryId + "&" + param
					+ "=" + token);
			// action here

		} else {
			notify("Please select Lexical Category", "info");
		}
	} else {
		notify("Please input a phrase", "info");
	}
}

function showDeleteModal(name) {
    $('#deletePhraseName').text(name);
    $('#deleteModal').modal('show');
}

