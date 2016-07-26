var urlXPath = null;
var col = 0, count = 0, flagBack = 0, flagDeleteName = 0, flagClick = 0, flagEditXPath = 0, flagWrap = 0, flagClickNew = 0;
var currentPosition = 0;
var step = 3;
var str1, str2, content, value, oldWrap;
var preview = ["","","",""];
var xpath =["","","",""];
var indexComplete = [ "", "", "", "" ];
$(document).ready(function() {
	var iframeDoc = document.getElementById('myframe').contentWindow;
	document.getElementById("btnBack").disabled = true;
	document.getElementById("btnNext").disabled = true;
	$(iframeDoc).click(function(event) {
		event.preventDefault();
		if (count < 4) {
			urlXPath = createXPathFromElement(event.target);
			content = event.target;
			document.getElementById("btnNext").disabled = false;

			if (flagClick == 1) {
				deleteRow('tbItems', 1);
			}
			if (flagEditXPath == 1) {
				document.getElementById("showXPath").innerHTML = "";
				flagEditXPath = 0;
				deleteRow('tbItems', 1);
			}
			
			flagClick = 1;
			value = urlXPath;
			if (count >= 4) {
				value = commonXpath(urlXPath);
			} else {
				if (count == 2) {
					value = imgXPath(event.target);
				}
			}
			if(count==0){
				showCart(content.href + "'\'", 'tbItems', 1);
				content = content.href;
			}
			if(count==1){
				showCart(content.innerText + "'\'", 'tbItems', 1);
				content = content.innerText;
			}
			if(count==2){
				showCart(content.src + "'\'", 'tbItems', 3);
				content = content.src;
			}
			if(count==3){
				showCart(content.innerText + "'\'", 'tbItems', 1);
				content = content.innerText;
			}
			flagClickNew = 1;
			try {
				$("#myframe")
						.contents()
						.find(oldWrap)
						.removeAttr("style",
								"background-color: #69c2fe;");

				$("#myframe")
						.contents()
						.find(getEleCss(value))
						.attr("style",
								"background-color: #69c2fe;");
				oldWrap = getEleCss(value);
			} catch (e) {
			}
		}
	});
});

function getEleCss(value) {
	var str_array = value.split("/");
	var l = str_array.length;
	var result;
	var rs_arr = new Array();
	if (l > 3) {
		var count = -1;

		if (str_array[l - 1].search("strong") == -1) {
			for (var j = 3; j < l; j++) {
				count++;
				rs_arr[count] = str_array[j];
			}
			result = " " + str_array[l - 1];
		} else {
			for (var j = l - 2; j > 2; j--) {
				count++;
				rs_arr[count] = str_array[j];
			}
			result = " " + str_array[l - 2];
		}

		// get nth
		var nthResult = new Array();
		for (var j = 0; j <= count; j++) {
			var start = rs_arr[j].indexOf('[');
			var end = rs_arr[j].indexOf(']');
			var portion = rs_arr[j].substring(start, end + 1);
			nthResult[j] = rs_arr[j].substring(start + 1, end);
			rs_arr[j] = rs_arr[j].replace(portion, '');
		}
		var pos, i;
		for (i = l - 1; i > 0; i--) {
			if (str_array[i].search("class") != -1) {
				pos = i;
				break
			}
		}
		var rs = str_array[pos];
		start = rs.indexOf('[');
		end = rs.indexOf(']');
		portion = rs.substring(start + 1, end);
		var nthRs = rs.substring(start, end - 1);
		var str = portion.split(" ");
		if (str.length > 1) {
			rs = str[0];
			start = rs.indexOf("'");
			rs = rs.substring(start + 1, rs.length);
		} else {
			rs = str[0];
			start = rs.indexOf("'");
			rs = rs.substring(start + 1, rs.length - 1);
		}
		var final = "." + rs;
		for (var j = 0; j <= count; j++) {
			if (nthResult[j] != "") {
				final = final + " " + rs_arr[j] + ":nth-of-type("
						+ nthResult[j] + ")";
			} else {
				final = final + " " + rs_arr[j];
			}
		}

		// if (nthResult != "") {
		// return "." + rs + result + ":nth-of-type(" + nthResult + ") ";
		// }
		// else {
		// return "." + rs + result;
		// }
		return final;
	} else {
		if (str_array[l - 1].search("strong") == -1) {
			result = " " + str_array[l - 1];
		} else {
			result = " " + str_array[l - 2];
		}
		var start = result.indexOf('[');
		var end = result.indexOf(']');
		var portion = result.substring(start, end + 1);
		// alert(portion);
		result = result.replace(portion, '');
		var pos, i;
		for (i = l - 1; i > 0; i--) {
			if (str_array[i].search("class") != -1) {
				pos = i;
				break
			}
		}
		var rs = str_array[pos];
		start = rs.indexOf('[');
		end = rs.indexOf(']');
		portion = rs.substring(start + 1, end);
		var str = portion.split(" ");
		if (str.length > 1) {
			rs = str[0];
			start = rs.indexOf("'");
			rs = rs.substring(start + 1, rs.length);
		} else {
			rs = str[0];
			start = rs.indexOf("'");
			rs = rs.substring(start + 1, rs.length - 1);
		}
		return "." + rs;
	}
}

function standardContent(content) {
	var start = -1, end = 0;
	start = content.indexOf('<');
	while (start != -1) {
		end = content.indexOf('>');
		var portion = content.substring(start, end + 1);
		content = content.replace(portion, '');
		start = content.indexOf('<');
	}
	return content;
}

function next() {
	var haveData = 0;
	if ((count <= 4)) {
		document.getElementById("btnBack").disabled = false;
		if ((flagClick != 0) || (indexComplete[currentPosition] != "")
				|| (count == 3)) {

			if (flagClick == 1) {
				indexComplete[count] = value;
				preview[currentPosition]=content;
			}
			if (flagClick == 0 && count == 3) {
				indexComplete[count] = "";
			}
			currentPosition++;
			if (currentPosition == 3) {
				swal({
					title : "Warning",
					text : "If this page has next page, please choose next page. If not, please click Next",
					type : "warning",
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "Ok!",
					closeOnConfirm : false
				})
			}
			$("#myframe").contents().find(oldWrap).removeAttr("style",
					"background-color: #69c2fe;");
			deleteRow('tbItems', 1);
			flagClick = 0;
			if (flagEditXPath == 1) {
				value = document.getElementsByName('txtXPath')[0].value;
				flagEditXPath = 0;
			}
			document.getElementById("showXPath").innerHTML = "";
			count++;
			if ((count == 4) && (flagDeleteName == 1)) {
				//deleteRow('tbItems', 1);
				//flagDeleteName = 0;
			}
			progressBar();
			showCart(indexComplete[currentPosition - 1] + "'\'", 'tbMain', 2);
			addToCart(indexComplete[currentPosition - 1]);
			if (count > 1) {
				if (flagBack != 1) {
					deleteRow('tbItems', 1);
				} else {
					flagBack = 0;
				}
			}
			if (count == 4) {
				document.getElementById("btnNext").disabled = true;
				document.getElementById("btnPreview").disabled = false;
				document.getElementById("btnAdd").disabled = false;
			}
		} else {
			swal({
				title : "PLEASE CHOOSE ONE ELEMENT",
				type : "warning",
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "Ok!",
				closeOnConfirm : false
			});
		}
	}
}

function back() {
	if (count >= 1) {
		if (flagClick == 1) {
			deleteRow('tbItems', 1);
			flagClick = 0;
		}
		if(count==1){
			document.getElementById("btnBack").disabled = true;
		}
		currentPosition--;
		document.getElementById("showXPath").innerHTML = "";
		col--;
		deleteRow('tbMain', col);
		if (flagBack != 1) {
			deleteRow('tbItems', 1);
		}
		var newX = indexComplete[currentPosition];
		flagClick = 1;
		value = newX;
		var a;
		var b;
		var alertText;
		if(currentPosition==0){
			content = preview[currentPosition];
			a = window.frames[0].document.evaluate(newX+'/@href'
	                , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
			b = a.iterateNext();
			alertText = b.textContent;
			showCart(alertText + "'\'", 'tbItems', 1);
		}
		if(currentPosition==1){
			content = preview[currentPosition];
			a = window.frames[0].document.evaluate(newX
	                , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
			b = a.iterateNext();
			alertText = b.textContent;
			showCart(alertText + "'\'", 'tbItems', 1);
		}
		if(currentPosition==2){
			content = preview[currentPosition];
			a = window.frames[0].document.evaluate(newX+'/@src'
	                , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
			b = a.iterateNext();
			alertText = b.textContent;
			showCart(alertText + "'\'", 'tbItems', 3);
		}
        
        flagClick = 1;
		try {
			$("#myframe").contents().find(oldWrap).removeAttr("style",
					"background-color: #69c2fe;");

			$("#myframe").contents().find(getEleCss(newX)).attr("style",
					"background-color: #69c2fe;");
			oldWrap = getEleCss(newX);
		} catch (e) {
		}
		// end show

		if (count == 4) {
			document.getElementById("btnNext").disabled = false;
		}
		flagBack = 1;
		backProgressBar();
		count--;
		removeFromCart();
		showCart("", 'tbItems');
		showCart("", 'tbMain');
	}
}

function commonXpath(xpath1) {
	var pos = 0;
	var str_array = xpath1.split("/");
	var l = str_array.length;
	for (i = l - 1; i > 0; i--) {
		if (str_array[i].search("class") != -1) {
			pos = i;
			break
		}
	}
	var rs = "/";
	for (i = pos; i < l; i++) {
		if ((i == l - 2) || (i == l - 1)) {
			var tmp = str_array[i];
			if (tmp.search("@") == -1) {
				var start = tmp.indexOf('[');
				var end = tmp.indexOf(']');
				var portion = tmp.substring(start, end + 1);
				tmp = tmp.replace(portion, '');
			}
			rs = rs + '/' + tmp;
		} else {
			rs = rs + '/' + str_array[i];
		}
	}
	return rs;
}

function backProgressBar() {
	step--;
	$('.progressRecipe .circle:nth-of-type(' + step + ')')
			.removeClass('active');
	$('.progressRecipe .circle:nth-of-type(' + (step - 1) + ') .labelRecipe')
			.html(step - 2);
	$('.progressRecipe .circle:nth-of-type(' + (step - 1) + ')').addClass(
			'active').removeClass('done');
	if (indexComplete[step - 2] != "") {
		$('.progressRecipe .circle:nth-of-type(' + step + ')').addClass('done');
	}
}

function progressBar() {
	$('.progressRecipe .circle:nth-of-type(' + step + ')').addClass('active');
	$('.progressRecipe .circle:nth-of-type(' + (step - 1) + ')').removeClass(
			'active').addClass('done');
	$('.progressRecipe .circle:nth-of-type(' + (step - 1) + ') .labelRecipe')
			.html('&#10003;');
	// $('.progressRecipe .bar:nth-of-type(' + (step - 1) +
	// ')').addClass('active');
	// $('.progressRecipe .bar:nth-of-type(' + (step - 2) +
	// ')').removeClass('active').addClass('done');
	step++;
};

// Get XPath from selected Element
function createXPathFromElement(elm) {
	var allNodes = document.getElementsByTagName('*');
	// alert(elm.firstChild.nodeValue);
	for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
		if (elm.hasAttribute('id')) {
			var uniqueIdCount = 0;
			for (var n = 0; n < allNodes.length; n++) {
				if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id)
					uniqueIdCount++;
				if (uniqueIdCount > 1)
					break;
			}
			;
			if (uniqueIdCount == 1) {
				segs.unshift("id('" + elm.getAttribute('id') + "')");
				return segs.join('/');
			} else {
				segs.unshift(elm.localName.toLowerCase() + "[@id='"
						+ elm.getAttribute('id') + "']");
			}
		} else if (elm.hasAttribute('class')) {
			segs.unshift(elm.localName.toLowerCase() + "[@class='"
					+ elm.getAttribute('class') + "']");
		} else {
			for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
				if (sib.localName == elm.localName)
					i++;
			}
			;
			segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
		}
		;
	}
	;
	var pos = 0;
	var string = segs.length ? '/' + segs.join('/') : null;
	string = string.replace("")
	var str_array = string.split("/");
	var l = str_array.length;
	for (i = l - 1; i > 0; i--) {
		if (str_array[i].search("class") != -1) {
			pos = i;
			break
		}
	}
	var rs = "/";
	for (i = pos; i < l; i++) {
		rs = rs + '/' + str_array[i]
	}
	return rs;
};

// get XPath of image
function imgXPath(elm) {
	var allNodes = document.getElementsByTagName('*');
	// alert(elm.firstChild.nodeValue);
	for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
		if (elm.hasAttribute('id')) {
			var uniqueIdCount = 0;
			for (var n = 0; n < allNodes.length; n++) {
				if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id)
					uniqueIdCount++;
				if (uniqueIdCount > 1)
					break;
			}
			;
			if (uniqueIdCount == 1) {
				segs.unshift("id('" + elm.getAttribute('id') + "')");
				return segs.join('/');
			} else {
				segs.unshift(elm.localName.toLowerCase() + "[@id='"
						+ elm.getAttribute('id') + "']");
			}
		} else if (elm.hasAttribute('class')) {
			segs.unshift(elm.localName.toLowerCase() + "[@class='"
					+ elm.getAttribute('class') + "']");
		} else {
			for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
				if (sib.localName == elm.localName)
					i++;
			}
			;
			segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
		}
		;
	}
	;
	var pos = 0;
	var string = segs.length ? '/' + segs.join('/') : null;
	string = string.replace("")
	var str_array = string.split("/");
	var l = str_array.length;
	for (i = l - 1; i > 0; i--) {
		if (str_array[i].search("div") != -1
				&& str_array[i].search("class") != -1) {
			pos = i;
			break
		}
	}
	var rs = "/";
	for (i = pos; i < l; i++) {
		if (str_array[i].search("img") == 0) {
			str_array[i] = "img";
		}
		rs = rs + '/' + str_array[i]
	}
	return rs;
};

// Preview
function addToCart(selectedItem) {
	if (typeof (sessionStorage) !== "undefined") {
		if (sessionStorage.cart == null) {
			sessionStorage.cart = '';
		}
		if (count == 1) {
			sessionStorage.cart = '';
		}
		sessionStorage.cart = sessionStorage.cart + selectedItem + "'\'"
	} else {
		swal("browser is not supported storage!!!");
	}
}

// remove last element of cart
function removeFromCart() {
	var item = sessionStorage.cart.split("'\'");
	sessionStorage.cart = '';

	for (var i = 0; i < item.length - 2; i++) {
		sessionStorage.cart = sessionStorage.cart + item[i] + "'\'";
	}
}

function editXPath() {
	flagEditXPath = 1;
	deleteRow('tbItems', 1);
	var newX = document.getElementsByName('txtXPath')[0].value;
	value = newX;
	try {
		var a = window.frames[0].document.evaluate(newX,
				window.frames[0].document, null, XPathResult.ANY_TYPE, null);
		var b = a.iterateNext();
		flagClick = 1;
	} catch (e) {
		flagClick = 0;
		showCart("INVALID XPATH" + "'\'", 'tbItems', 4);
		document.getElementById("btnNext").disabled = true;
	}
	if(b==null){
		flagClick = 0;
		showCart("INVALID XPATH" + "'\'", 'tbItems', 4);
		document.getElementById("btnNext").disabled = true;
	}
	if(b!=null){
		var alertText = b.textContent;
		// alert(alertText);
		showCart(alertText + "'\'", 'tbItems', 1);
		document.getElementById("btnNext").disabled = false;
	}
	$("#myframe").contents().find(oldWrap).removeAttr("style",
			"background-color: #69c2fe;");

	$("#myframe").contents().find(getEleCss(newX)).attr("style",
			"background-color: #69c2fe;");
	oldWrap = getEleCss(newX);
}

function addRow(tableId, cells, type) {
	var tableElem = document.getElementById(tableId);
	var newCell;
	var newRow;
	for (var i = 0; i < cells.length - 1; i++) {
		newRow = tableElem.insertRow(tableElem.rows.length);
		newCell = newRow.insertCell(newRow.cells.length);
		if (type == 1) {
			newCell.innerHTML = '<input type="text" class="form-control" name="txtTemp" value="'
					+ cells[i]
					+ '" size="76"/>'
					+ '<input type="button" class="btn btn-info m-b-less" value="Edit" onclick="showXPath()"/>';
		} else if (type == 2) {
			col++;
			switch (count) {
			case 1:
				newCell.innerHTML = '<input type="hidden" name="PAGE" value="'
						+ cells[i]
						+ '" size="78"/><input type="hidden" name="txtDescriptionLink" value="'
						+ preview[0] + '"/>';
				break;
			case 2:
				newCell.innerHTML = '<input type="hidden" name="PRODUCTNAME" value="'
						+ cells[i] + '" size="78"/>';
				break;
			case 3:
				newCell.innerHTML = '<input type="hidden" name="IMAGE" value="'
						+ cells[i] + '" size="78"/>';
				break;
			case 4:
				newCell.innerHTML = '<input type="hidden" name="NEXTPAGE" value="'
						+ cells[i] + '" size="78"/>';
				break;
			}

		} else if (type == 3) {
			newCell.innerHTML = '<img style="width:100px;height:100px" src="'
					+ cells[i]
					+ '"/>'
					+ '</br>'
					+ '</br>'
					+ '<input type="button" class="btn btn-info m-b-less" value="Edit" onclick="showXPath()"/>';
		} else if (type == 4) {
			newCell.innerHTML = '<input type="text" style="color:red;" name="txtTemp" class="form-control" value="'
					+ cells[i]
					+ '" size="76"/>'
					+ '<input type="button" class="btn btn-info m-b-less" value="Edit" onclick="showXPath()"/>';
		}
		// newCell.width = 200;
	}
	return newRow;
}
// 0:item 1:Main
function deleteRow(tableId, rowNumber) {
	var tableElem = document.getElementById(tableId);
	if (rowNumber > 0 && rowNumber < tableElem.rows.length
			&& tableId != 'tbMain') {
		tableElem.deleteRow(rowNumber);
	}
	if (tableId == 'tbMain') {
		tableElem.deleteRow(rowNumber);
		flagDeleteName = 1;
	}
}

function showCart(items, tableId, type) {
	var item = items.split("'\'");
	addRow(tableId, item, type);
}

function showXPath() {
	flagEditXPath = 1;
	document.getElementById("showXPath").innerHTML = '<input type="text" name="txtXPath"'
			+ 'id="txtXPath" class="form-control" value="'
			+ value
			+ '" size="90" onchange="editXPath()">';
}

function addNew() {
	while (currentPosition < 4) {
		count++;
		progressBar();
		showCart(indexComplete[currentPosition] + "'\'", 'tbMain', 4);
		addToCart(indexComplete[currentPosition]);
		currentPosition++;
	}
	document.myForm.submit();
}

function openpopup(id) {
	// Append content first
	appendcontents(indexComplete);
	// Calculate Page width and height
	var pageWidth = window.innerWidth;
	var pageHeight = window.innerHeight;
	if (typeof pageWidth !== "number") {
		if (document.compatMode === "CSS1Compat") {
			pageWidth = document.documentElement.clientWidth;
			pageHeight = document.documentElement.clientHeight;
		} else {
			pageWidth = document.body.clientWidth;
			pageHeight = document.body.clientHeight;
		}
	}
	var divbg = document.getElementById('bg');
	divbg.style.visibility = "visible";
	divbg.style.height = document.documentElement.scrollHeight + 'px';

	var divobj = document.getElementById(id);
	divobj.style.visibility = "visible";
	if (navigator.appName === "Microsoft Internet Explorer")
		computedStyle = divobj.currentStyle;
	else
		computedStyle = document.defaultView.getComputedStyle(divobj, null);
	var divWidth = computedStyle.width.replace('px', '');
	var divHeight = computedStyle.height.replace('px', '');
	var divLeft = (pageWidth - divWidth) / 2;
	var divTop = (pageHeight - divHeight) / 2;
	divobj.style.left = divLeft + "px";
	divobj.style.top = divTop + "px";
	if (divobj.innerHTML.indexOf("closepopup('" + id + "')") < 0)
		divobj.innerHTML = "<div class=\"panel panel-primary\" style=\"position:fixed\"><div class=\"panel-heading\" style=\"position:relative;height:42px;width:510px;margin:-5px\">"
				+ "<p style=\"font-size:14px;float:left;padding:10px\">Preview Your Selected Field</p>"
				+ "<a style=\"float:right;padding:5px;margin-right:20px\" href=\"#\" onclick=\"closepopup('"
				+ id
				+ "')\"><span class=\"btn btn-danger m-b-less\" style=\"float:right;position:relative\">Close</span></a>"
				+ "<button style=\"float:right;margin:5px\" type=\"submit\" class=\"btn btn-info m-b-10\" id=\"btnAdd\" name =\"btnAction\" value=\"AddNewPageList\" onclick=\"addNew()\" >AddNewPageList</button>"
				+ "</div></div>" + divobj.innerHTML;
}
function closepopup(id) {
	var divbg = document.getElementById('bg');
	divbg.style.visibility = "hidden";
	var divobj = document.getElementById(id);
	divobj.style.visibility = "hidden";
}
function appendcontents(item) {
	// var item = items.split("'\'");
	var content = '</br></br></br><table border="1" style="width: 485px" class="table"><thead></thead><tr><th style="width: 10%">Type</th><th style="width: 90%">Content</th></tr><tbody>';

	content = content
			+ '<tr><td><strong>Product Description Link</strong></td><td>';
	// var a = window.frames[0].document.evaluate(item[0] + '/@href',
	// window.frames[0].document, null, XPathResult.ANY_TYPE, null);
	// var b = a.iterateNext();
	var alertText = "" + preview[0]
	// while (b) {
	// alertText += b.textContent + "<br/>"
	// //b = a.iterateNext();
	// }
	content = content + '<input type="hidden" name="txtDescriptionLink" value="'
			+ alertText + '"/>'
	content = content + alertText
			+ '</td></tr><tr><td><strong>Product Name</strong></td><td>';

	// get Address
	// a = window.frames[0].document.evaluate(item[1],
	// window.frames[0].document,
	// null, XPathResult.ANY_TYPE, null);
	// b = a.iterateNext();
	alertText = "" + preview[1]
	// while (b) {
	// if (b.textContent.length < 150) {
	// alertText += b.textContent + "<br/>";
	// } else {
	// alertText += b.textContent.substring(0, 220) + "...<br/>";
	// }
	// b = a.iterateNext();
	// }
	content = content
			+ alertText
			+ '</td></tr><tr><td><strong>Image</strong></td><td style="width: '
			+ '150px;  vertical-align: top"><img style="width:150px;height:150px" src =';

	// getImage
	// a = window.frames[0].document.evaluate(item[2] + '/@src',
	// window.frames[0].document, null, XPathResult.ANY_TYPE, null);
	// b = a.iterateNext();
	alertText = "" + preview[2]
	// while (b) {
	// alertText += b.textContent+"</br>";
	// b = a.iterateNext();
	// }
	content = content + alertText + '></td></tr></tbody></table>';
	appendto = document.getElementById('popup');
	appendto.innerHTML = content;
	// sessionStorage.cart = '';
}