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
