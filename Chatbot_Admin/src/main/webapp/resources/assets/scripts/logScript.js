/**
 * 
 */

var listPhrase = {};

function updateLog() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText == 'true') {
				location.reload();
			} else {
				swal('Error occurs. Please try again later!');
			}
		}
	}

	xmlhttp.open('GET', '/chatbot_admin/updateLog', false);
	xmlhttp.send(null);
}

function createRowNoEntry(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	tr.setAttribute("data-toggle", "modal");
	tr.setAttribute("data-target", "#myModal");
	
	var totalCount = data.totalCount;
	var userSay = data.userSay;
	if (totalCount) {
		userSay += " ("+ totalCount +")";
	}
	var tdUserSay = document.createElement('td');
	var textUserSay = document.createTextNode(userSay);
	
	var tdAction = document.createElement('td');
	var textAction = document.createTextNode(data.action);
	
	var tdIntent = document.createElement('td');
	var textIntent = document.createTextNode(data.intentName);

	var listPhraseContent = document.getElementById('list-phrase');

	tr.addEventListener('click', function() {
		// Get the modal
		var modal = document.getElementById('myModal');
		var pContainer = document.getElementById('user-say-container');
		var p = document.getElementById('user-say-in-modal').innerHTML = data.userSay;
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
		saveButton.addEventListener('click', requestAddPhrase);
		
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
	});

	tdUserSay.appendChild(textUserSay);
	tr.appendChild(tdUserSay);
	
	tdAction.appendChild(textAction);
	tr.appendChild(tdAction);
	
	tdIntent.appendChild(textIntent);
	tr.appendChild(tdIntent);
	
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

function requestAddPhrase() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText) {
				swal(xmlhttp.responseText);
				closeModalDialog();
			}
		}
	}

	xmlhttp.open("POST", "/chatbot_admin/addPhrase", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send("listPhrase=" + JSON.stringify(listPhrase));
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
	
	tdAction.appendChild(textAction);
	tr.appendChild(tdAction);
	
	tdIntent.appendChild(textIntent);
	tr.appendChild(tdIntent);

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