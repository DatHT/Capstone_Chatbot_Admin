/**
 * 
 */

function createRowNoEntry(id, data) {
	var tableBody = document.getElementById(id);
	var tr = document.createElement('tr');
	var tdNo = document.createElement('td');
	var tdText = document.createTextNode(data.userSay);
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