var selector, elems, makeActive;

selector = '.nav-inner';

elems = document.querySelectorAll(selector);

makeActive = function() {
	for (var i = 0; i < elems.length; i++)
		elems[i].classList.remove('selected');

	this.classList.add('selected');

};

for (var i = 0; i < elems.length; i++)
	elems[i].addEventListener('mousedown', makeActive);

// ajax load nav
var xmlhttp;

function abc() {
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
				document.getElementsByClassName("nav-inner").innerHTML = xmlhttp.responseText;
		}
		xmlhttp.open("GET", "/mock/admin/macInfo/enable", true);
		xmlhttp.send();
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
	while (table.rows.length > 0) {
		table.deleteRow(0);
	}
}



function notify(message, type){
    $.growl({
        message: message
    },{
        type: type,
        allow_dismiss: false,
        label: 'Cancel',
        className: 'btn-xs btn-inverse',
        placement: {
            from: 'top',
            align: 'right'
        },
        delay: 2500,
        animate: {
                enter: 'animated fadeInRight',
                exit: 'animated fadeOutRight'
        },
        offset: {
            x: 30,
            y: 30
        }
    });
};

