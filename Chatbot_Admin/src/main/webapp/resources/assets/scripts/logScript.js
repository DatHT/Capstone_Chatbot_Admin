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
				alert('Error occurs. Please try again later!');
			}
		}
	}

	xmlhttp.open('GET', '/chatbot_admin/updateLog', false);
	xmlhttp.send(null);
}

function createRowNoEntry(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	var tdNo = document.createElement('td');
	var tdText = document.createTextNode(data.userSay);

	var listPhraseContent = document.getElementById('list-phrase');

	tdNo.addEventListener('click', function() {
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

	tdNo.appendChild(tdText);
	tr.appendChild(tdNo);
	tableBody.appendChild(tr);
}

function closeModalDialog() {
	listPhrase = {};
	var modal = document.getElementById('myModal');
	modal.style.display = "none";
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
			if (xmlhttp.responseText == 'true') {
				alert('Success!');
				var modal = document.getElementById('myModal');
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
	var tdFoodText = document.createTextNode(data.Food);
	tdFood.appendChild(tdFoodText);
	var tdLocation = document.createElement('td');
	var tdLocationText = document.createTextNode(data.Location);
	tdLocation.appendChild(tdLocationText);

	tr.appendChild(tdFood);
	tr.appendChild(tdLocation);

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
	phrase.className = 'col-md-4';
	var phraseContent = document.createElement('p');
	var pText = document.createTextNode(text);
	listPhrase[text] = "";
	checkSaveButtonState();

	phraseContent.appendChild(pText);
	phrase.appendChild(phraseContent);
	element.appendChild(phrase);

	var lexicalCategory = document.createElement('div');
	lexicalCategory.className = 'col-md-4';
	var lexicalList = document.getElementsByClassName('listLexical')[0]
			.cloneNode(true);
	lexicalList.onchange = function(event) {
		listPhrase[text] = event.target.value;
		checkSaveButtonState();
	}
	lexicalList.style.display = 'block';

	lexicalCategory.appendChild(lexicalList);
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
	var phrases = Object.keys(listPhrase);
	if (phrases.length == 0) {
		saveButton.disabled = true;
		return;
	}
	for(var i = 0; i < phrases.length; i++) {
		if (listPhrase[phrases[i]] == "") {
			saveButton.disabled = true;
			return;
		}
	}
	saveButton.disabled = false;
}