/**
 * 
 */

var listPhrase = {};

var xmlhttp;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
xmlhttp.onreadystatechange = function() {
	if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
		var data = JSON.parse(xmlhttp.responseText);
		var listContent = data.contents;

		for (var int = 0; int < listContent.length; int++) {
			if (listContent[int].errCode == '300') {
				createRowNoEntry("no-entry-table-body",
						listContent[int]);
			} else if (listContent[int].errCode == '404') {
				createRowNotFound("not-found-table-body",
						listContent[int]);
			} else if (listContent[int].errCode == '400') {
				createReportedProduct("reported-product-table-body", listContent[int]);
			}
		}
		$('#no-entry-data-table').bootgrid({
			rowCount: 5,
            css: {
                icon: 'zmdi icon',
                iconColumns: 'zmdi-view-module',
                iconDown: 'zmdi-expand-more',
                iconRefresh: 'zmdi-refresh',
                iconUp: 'zmdi-expand-less'
            },
            formatters: {
                "commands": function (column, row) {
                    return "<button data-row-id='" + row.usersay + "' data-toggle='modal' data-target='#myModal' class='btn palette-Cyan btn-icon bg waves-effect waves-circle waves-float action-add'><i class='zmdi zmdi-plus-circle-o zmdi-hc-fw'></i></button>"+
                    "   <button class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float action-delete'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
                }
            }
        }).on("loaded.rs.jquery.bootgrid", function() {
            /* Executes after data is loaded and rendered */
            $('#no-entry-data-table').find(".action-add").on("click", function(e) {
            	$("#myModal").modal();
            	var listPhraseContent = document.getElementById('list-phrase');
            	// Get the modal
        		var modal = document.getElementById('myModal');
        		var pContainer = document.getElementById('user-say-container');
        		var p = document.getElementById('user-say-in-modal').innerHTML = $(this).data("row-id");
        		modal.style.display = "block";
        		// Get the <span> element that closes the modal
        		var span = document.getElementsByClassName("close")[0];

        		pContainer.addEventListener('mouseup', function() {
        			var text = getTextSelection().trim();
        			if (text && listPhrase[text] === undefined) {
        				listPhraseContent.appendChild(createPhraseElement(text));
        			}
        		});
        		
        		var saveButton = document.getElementById('save-button');
        		checkSaveButtonState();
        		
        		span.onclick = function() {
        			closeModalDialog();
        		}
        		// When the user clicks anywhere outside of the modal,
        		// close it
        		window.onclick = function(event) {
        			if (event.target == modal) {
        				closeModalDialog();
        			}
        		}
            }).end().find(".action-delete").on("click", function(e)
            {
                alert("You pressed delete on row: ");
            });
        });
		
		$('#not-found-data-table').bootgrid({
			rowCount: 5,
            css: {
                icon: 'zmdi icon',
                iconColumns: 'zmdi-view-module',
                iconDown: 'zmdi-expand-more',
                iconRefresh: 'zmdi-refresh',
                iconUp: 'zmdi-expand-less'
            },
            formatters: {
                "addProduct": function (column, row) {
                    return "<button data-row-food='" + row.food + "' data-row-location='" + row.location + "' data-toggle='modal' data-target='#myModal' class='btn palette-Cyan btn-icon bg waves-effect waves-circle waves-float action-add-product'><i class='zmdi zmdi-plus-circle-o zmdi-hc-fw'></i></button>";
                }
            }
        }).on("loaded.rs.jquery.bootgrid", function() {
        	/* Executes after data is loaded and rendered */
            $('#not-found-data-table').find(".action-add-product").on("click", function(e) {
            	window.location.href= 'viewAddProduct?txtDistrict=' + $(this).data("row-location") + "&txtFood=" + $(this).data("row-food");
            });
        });
		
		$('#reported-data-table').bootgrid({
			rowCount: 5,
            css: {
                icon: 'zmdi icon',
                iconColumns: 'zmdi-view-module',
                iconDown: 'zmdi-expand-more',
                iconRefresh: 'zmdi-refresh',
                iconUp: 'zmdi-expand-less'
            },
            formatters: {
                "updateProduct": function (column, row) {
                    return "<button data-row-product-id='" + row.productId + "' class='btn btn-warning btn-icon waves-effect waves-circle waves-float update-product'><i class='zmdi zmdi-edit zmdi-hc-fw'></i></button>";
                }
            }
        }).on("loaded.rs.jquery.bootgrid", function() {
        	/* Executes after data is loaded and rendered */
            $('#reported-data-table').find(".update-product").on("click", function(e) {
            	window.location.href= 'viewUpdateProduct?productId=' + $(this).data("row-product-id");
            });
        });
	}
};
xmlhttp.open('GET', '/chatbot_admin/getLog', true);
xmlhttp.send(null);

function updateLog(param, token) {
	$('#loadingModal').modal('show');
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText == 'true') {
				setTimeout(function() {
					location.reload();
					$('#loadingModal').modal('hide');
				}, 500);
			} else {
				swal('Error occurs. Please try again later!');
			}
		}
	}

	xmlhttp.open('GET', '/chatbot_admin/updateLog', false);
	xmlhttp.send(param + "=" + token);
}

function createRowNoEntry(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	
	var totalCount = data.totalCount;
	var userSay = data.userSay;

	var tdUserSay = document.createElement('td');
	var textUserSay = document.createTextNode(userSay);
	tdUserSay.appendChild(textUserSay);
	tr.appendChild(tdUserSay);
	
	var tdCount = document.createElement('td');
	var numcount = document.createTextNode(totalCount);
	tdCount.appendChild(numcount);
	tr.appendChild(tdCount);

	tableBody.appendChild(tr);
}

function closeModalDialog() {
	listPhrase = {};
	$('#myModal').modal('hide');
	var listPhraseContent = document.getElementById('list-phrase');
	while (listPhraseContent.firstChild) {
		listPhraseContent.removeChild(listPhraseContent.firstChild);
	}
}

function requestAddPhrase(param, token) {
	$('#loadingModal').modal('show');
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText) {
				$('#loadingModal').modal('hide');
				swal("Good job!", xmlhttp.responseText, "success");
				closeModalDialog();
			}
		}
	}

	xmlhttp.open("POST", "/chatbot_admin/addPhrase", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("listPhrase=" + JSON.stringify(listPhrase) + "&" + param + "=" + token);
}

function createRowNotFound(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	var tdFood = document.createElement('td');
	var tdFoodText = document.createTextNode(data.contexts.Food);
	tdFood.appendChild(tdFoodText);
	var tdLocation = document.createElement('td');
	var tdLocationText = document.createTextNode(data.contexts.Location);
	
	var tdAction = document.createElement('td');
	var textAction = document.createTextNode(data.action);
	
	var tdIntent = document.createElement('td');
	var textIntent = document.createTextNode(data.intentName);

	tdLocation.appendChild(tdLocationText);

	tr.appendChild(tdFood);
	tr.appendChild(tdLocation);

	tableBody.appendChild(tr);
}

function createReportedProduct(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	
	var tdProductId = document.createElement('td');
	var txtProductId = document.createTextNode(data.productId);
	tdProductId.appendChild(txtProductId);
	tr.appendChild(tdProductId);
	
	var tdAddressId = document.createElement('td');
	var txtAddressId = document.createTextNode(data.addressId);
	tdAddressId.appendChild(txtAddressId);
	tr.appendChild(tdAddressId);
	
	var tdProductName = document.createElement('td');
	var txtProductName = document.createTextNode(data.productName);
	tdProductName.appendChild(txtProductName);
	tr.appendChild(tdProductName);
	
	var tdDistrictName = document.createElement('td');
	var txtDistrictName = document.createTextNode(data.districtName);
	tdDistrictName.appendChild(txtDistrictName);
	tr.appendChild(tdDistrictName);
	
	var tdRestaurantName = document.createElement('td');
	var txtRestaurantName = document.createTextNode(data.restaurantName);
	tdRestaurantName.appendChild(txtRestaurantName);
	tr.appendChild(tdRestaurantName);
	
	tableBody.appendChild(tr);
}

function getTextSelection() {
	var text = "";
	if (window.getSelection) {
		text = window.getSelection().toString();
		if (window.getSelection().empty) { // Chrome
			window.getSelection().empty();
		} else if (window.getSelection().removeAllRanges) { // Firefox
			window.getSelection().removeAllRanges();
		}
	} else if (document.selection // IE
			&& document.selection.type != "Control") {
		text = document.selection.createRange().text;
		document.selection.empty();
	}
	return text;
}

function createPhraseElement(text) {
	var element = document.createElement('div');
	element.className = 'row';

	var hr = document.createElement('hr');
	element.appendChild(hr);

	var phrase = document.createElement('div');
	phrase.className = 'col-sm-4 m-b-25';
	var phraseContent = document.createElement('p');
	var pText = document.createTextNode(text);
	listPhrase[text] = "";
	checkSaveButtonState();

	phraseContent.appendChild(pText);
	phrase.appendChild(phraseContent);
	element.appendChild(phrase);

	var lexicalCategory = document.createElement('div');
	lexicalCategory.className = 'col-sm-4 m-b-25';
	var divSelect = document.createElement('div');
	divSelect.className = 'select';
	var lexicalList = document.getElementsByClassName('listLexical')[0]
			.cloneNode(true);
	lexicalList.onchange = function(event) {
		listPhrase[text] = event.target.value;
		checkSaveButtonState();
	}
	lexicalList.style.display = 'block';

	divSelect.appendChild(lexicalList);
	lexicalCategory.appendChild(divSelect);
	element.appendChild(lexicalCategory);

	var span = document.createElement('span');
	span.className = 'remove col-md-1';
	span.innerHTML = 'Cancel';
	span.addEventListener('click', function() {
		element.parentElement.removeChild(element);
		delete listPhrase[text];
		checkSaveButtonState();
	});

	element.appendChild(span);

	return element;
}

function checkSaveButtonState() {
	var saveButton = document.getElementById('save-button');
	var guide = document.getElementById('select_phrase_guide');
	var phrases = Object.keys(listPhrase);
	if (phrases.length == 0) {
		saveButton.disabled = true;
		guide.style.display = "block";
		return;
	} else {
		guide.style.diplay = "none";
	}
	for(var i = 0; i < phrases.length; i++) {
		if (listPhrase[phrases[i]] == "") {
			saveButton.disabled = true;
			return;
		}
	}
	saveButton.disabled = false;
}