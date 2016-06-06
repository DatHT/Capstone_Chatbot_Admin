var xmlhttp;
var count = 0;
var resultLexical = "";

	function addRows(tableId, data) {
		var jsonData = JSON.parse(data);
		var tableElem = document.getElementById(tableId);
		deleteRows(tableElem);
		for(var i = 0; i < jsonData.entries.length; i++) {
			var cells = [];
			cells[0] = i + 1;
			cells[1] = jsonData.entries[i].value;
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
    
    function deleteRows(table) {
    	while(table.rows.length > 0) {
    		  table.deleteRow(0);
    		}
    }

 	function loadPharse(id) {
 		
 		document.getElementById("tableHeader").innerHTML = id.options[id.selectedIndex].text;
 		
 		if (window.XMLHttpRequest) {
 			xmlhttp = new XMLHttpRequest();
 		} else xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
 			xmlhttp.onreadystatechange = function() {
 				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
 					resultLexical = xmlhttp.responseText;
 					addRows("lexicalTable", resultLexical);
 				}
 						
 			}
 			xmlhttp.open("GET", "/chatbot_admin/lexical/" + id.value, true);
 			xmlhttp.send();
 		
 	}