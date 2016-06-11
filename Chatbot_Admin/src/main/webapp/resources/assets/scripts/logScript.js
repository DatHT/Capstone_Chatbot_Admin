/**
 * 
 */

function updateLog() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

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
		var p = document.getElementById('user-say-in-modal').innerHTML = data.userSay;
		modal.style.display = "block";
		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks on <span> (x), close the modal
		modal.addEventListener('mouseup', function() {
			var text = "";
			if (window.getSelection) {
				text = window.getSelection().toString();
				if (window.getSelection().empty) {  // Chrome
				    window.getSelection().empty();
				  } else if (window.getSelection().removeAllRanges) {  // Firefox
				    window.getSelection().removeAllRanges();
				  }
			} else if (document.selection //IE
					&& document.selection.type != "Control") {
				text = document.selection.createRange().text;
				document.selection.empty();
			}
			
			if (text) {
				var hr = document.createElement('hr');
				var phrase = document.createElement('div');
				var phraseContent = document.createElement('p');
				var pText = document.createTextNode(text);
				phraseContent.appendChild(pText);
				phrase.appendChild(phraseContent);
				listPhraseContent.appendChild(hr);
				listPhraseContent.appendChild(phrase);
			}
		});
		span.onclick = function() {
			modal.style.display = "none";
			while (listPhraseContent.firstChild) {
				listPhraseContent.removeChild(listPhraseContent.firstChild);
			}
		}
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
				while (listPhraseContent.firstChild) {
					listPhraseContent.removeChild(listPhraseContent.firstChild);
				}
			}
		}
	});
	tdNo.appendChild(tdText);
	tr.appendChild(tdNo);
	tableBody.appendChild(tr);
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

// function creaeModalBox(data) {
// var modalBox = do
// }
