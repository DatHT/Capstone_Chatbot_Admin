/**
 * 
 */
function loadFullConversation(date) {
	var panelGroup = document.getElementById('panel-group');
	panelGroup.innerHTML = '';
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText != 'failed') {
				var res = JSON.parse(xmlhttp.responseText);
				for (var i = 0; i < res.length; i++) {
					var obj = res[i];
					
					var panelGroup = document.getElementById('panel-group');
					
					var panelCollapse = document.createElement('div');
					panelCollapse.className = "panel panel-collapse";
					
					var panelHeading = document.createElement('div');
					panelHeading.setAttribute('id', "heading"+obj.sessionId);
					panelHeading.className = "panel-heading";
					panelHeading.setAttribute("role", "tab");
					var panelTitle = document.createElement('h4');
					panelTitle.className = "panel-title";
					var ahref = document.createElement('a');
					ahref.setAttribute('data-toggle', "collapse");
					ahref.setAttribute('data-parent', "#accordion");
					ahref.setAttribute('href', "#" + obj.sessionId);
					ahref.setAttribute('aria-expanded', "true");
					ahref.setAttribute('aria-controls', "heading"+obj.sessionId);
					
					var text = document.createTextNode(obj.contents[0].userSay);
					ahref.appendChild(text);
					
					panelTitle.appendChild(ahref);
					panelHeading.appendChild(panelTitle);
					
					panelCollapse.appendChild(panelHeading);
					
					var wrapperPanelBody = document.createElement('div');
					wrapperPanelBody.className = "collapse";
					wrapperPanelBody.setAttribute('id', obj.sessionId);
					wrapperPanelBody.setAttribute('role', "tabpanel");
					wrapperPanelBody.setAttribute('aria-labelledby', "heading"+obj.sessionId);
					var panelBody = document.createElement('div');
					panelBody.className = "panel-body";
					
					var table = document.createElement('table');
					table.className="table";
					var tableBody = document.createElement('tbody');
					for(var j = 0; j < obj.contents.length; j++) {
						var tr = document.createElement('tr');
						var td = document.createElement('td');
						var textNode = document.createTextNode(obj.contents[j].userSay);
						td.appendChild(textNode);
						tr.appendChild(td);
						tableBody.appendChild(tr);
					}
					table.appendChild(tableBody);
					panelBody.appendChild(table);
					wrapperPanelBody.appendChild(panelBody);
					panelCollapse.appendChild(wrapperPanelBody);
					
					panelGroup.appendChild(panelCollapse);
					panelCollapse.removeAttribute('hidden');
				}
			} else {
				swal('Error occurs. Please try again later!');
			}
		}
	}

	xmlhttp.open('GET', '/chatbot_admin/fullConversation?date=' + date.value, true);
	xmlhttp.send(null);
}