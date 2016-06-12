function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text");
	ev.target.appendChild(document.getElementById(data));
	// document.getElementById("here").innerHtml = "Dat";
//	var node = document.createElement("DIV");
//	var textnode = document.createTextNode("Dat");
//	alert(data);
//	node.appendChild(textnode);
//	document.getElementById("box-dragable").appendChild(node);
}