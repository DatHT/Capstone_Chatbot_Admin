/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var urlXPath = null;
var col = 0, count = 0, flagBack = 0, flagDeleteName = 0, flagClick = 0, flagEditXPath = 0, flagWrap = 0, flagClickNew = 0;
var currentPosition = 0;
var step = 3;
var str1, str2, content, value, oldWrap;
var indexComplete = ["", "", "", "", ""];
$(document).ready(function () {
    var iframeDoc = document.getElementById('myframe').contentWindow;
    $(iframeDoc).click(function (event) {
        event.preventDefault();
        if (count < 4) {
            urlXPath = createXPathFromElement(event.target);
            content = event.target.innerHTML;
            //alert(standardContent(content));
            content = standardContent(content);
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
            if (count >= 3) {
                value = commonXpath(urlXPath);
            } else {
                if (count == 2) {
                    value = imgXPath(event.target);
                }
            }
            if (value.indexOf('img') > -1) {
                showCart("image" + "'\'", 'tbItems', 1);
            } else {
                showCart(content + "'\'", 'tbItems', 1);
            }
            //alert("CONTENT: " + event.target.innerHTML + "\nXPATH: " + urlXPath); 

            //Apply highlight div
            //alert(getEleCss(value));
            flagClickNew = 1;
            try {
                $("#myframe").contents().find(oldWrap).removeAttr("style", "background-color: #69c2fe;");

                $("#myframe").contents().find(getEleCss(value)).attr("style", "background-color: #69c2fe;");
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

        //get nth
        var nthResult = new Array();
        for (var j = 0; j <= count; j++) {
            var start = rs_arr[j].indexOf('[');
            var end = rs_arr[j].indexOf(']');
            var portion = rs_arr[j].substring(start, end + 1);
            nthResult[j] = rs_arr[j].substring(start + 1, end);
            rs_arr[j] = rs_arr[j].replace(portion, '');
        }
//        var start = result.indexOf('[');
//        var end = result.indexOf(']');
//        var portion = result.substring(start, end + 1);
        //var nthResult = result.substring(start + 1, end);
        //alert(portion);
        //result = result.replace(portion, '');
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
                final = final + " " + rs_arr[j] + ":nth-of-type(" + nthResult[j] + ")";
            } else {
                final = final + " " + rs_arr[j];
            }
        }

//        if (nthResult != "") {
//            return "." + rs + result + ":nth-of-type(" + nthResult + ") ";
//        }
//        else {
//            return "." + rs + result;
//        }
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
        //alert(portion);
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
        if ((flagClick != 0) || (indexComplete[currentPosition] != "")) {
//            if(indexComplete[currentPosition]==""){
//                indexComplete[count] = value;
//            }
//            if (flagClick == 1) {
//                indexComplete[count] = value;
//            }
            $("#myframe").contents().find(oldWrap).removeAttr("style", "background-color: #69c2fe;");
            deleteRow('tbItems', 1);
            currentPosition++;

            document.getElementById("showXPath").innerHTML = "";

            //show current content
            if (indexComplete[currentPosition] != "" && currentPosition < 4 && flagClickNew != 1 && flagEditXPath != 1) {
                var newX = indexComplete[currentPosition];
                value = indexComplete[currentPosition];
                var a = window.frames[0].document.evaluate(newX
                        , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
                var b = a.iterateNext();
                //???
                flagClick = 1;
                //Apply highlight div
//        alert(getEleCss(newX));       
                var alertText = ""
                while (b) {
                    alertText += b.textContent + "    "
                    b = a.iterateNext();
                }
                //alert(alertText);
                if (newX.indexOf('img') > -1) {
                    showCart("image" + "'\'", 'tbItems', 1);
                } else {
                    showCart(alertText + "'\'", 'tbItems', 1);
                }
                try {
                    $("#myframe").contents().find(oldWrap).removeAttr("style", "background-color: #69c2fe;");

                    $("#myframe").contents().find(getEleCss(newX)).attr("style", "background-color: #69c2fe;");
                    oldWrap = getEleCss(newX);
                } catch (e) {
                }
                haveData = 1;
            }

            //end show

            if (flagEditXPath == 1) {
                flagClick = 1;
                flagEditXPath = 0;
            }

            if (flagClick == 1) {
                flagClick = 0;
                if (haveData == 1) {
                    indexComplete[count] = indexComplete[currentPosition - 1];//*****
                    flagClick = 1;
                } else {
                    indexComplete[count] = value;//*****
                }
            }
            flagClickNew = 0;
            count++;
            //        if ((count == 1)||(count == 2)||(count == 3)) {
//            if ((count == 2) && (flagDeleteName == 1)) {
//                //            deleteRow('tbItems', 1);
//                deleteRow('tbItems', 1);
//                flagDeleteName = 0;
//            }
            progressBar();
            if (count > 1) {
                if (flagBack != 1) {
                    //                deleteRow('tbItems', 1);
                    //deleteRow('tbItems', 1);
                } else {
                    flagBack = 0;
                }
            }
            showCart(indexComplete[currentPosition - 1] + "'\'", 'tbMain', 2);
            addToCart(indexComplete[currentPosition - 1]);
            if (count == 4) {
                //            alert("FINISHED");
                //            document.myForm.submit();
                //                openpopup('popup');
                document.getElementById("btnNext").disabled = true;
                document.getElementById("btnPreview").disabled = false;
                document.getElementById("btnAdd").disabled = false;
            }
        } else {
            alert("PLEASE CHOOSE ONE ELEMENT");
        }
    }
}

function back() {
    if (count >= 1) {
        if (flagClick == 1) {
            deleteRow('tbItems', 1);
            flagClick = 0;
        }
        currentPosition--;
        document.getElementById("showXPath").innerHTML = "";
        col--;
        deleteRow('tbMain', col);
        if (flagBack != 1) {
            //            deleteRow('tbItems',1);  
            deleteRow('tbItems', 1);
        }
        //show current content
        var newX = indexComplete[currentPosition];
        value = newX;
        var a = window.frames[0].document.evaluate(newX
                , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
        var b = a.iterateNext();
        //???
        flagClick = 1;
        //Apply highlight div
//        alert(getEleCss(newX));       
        var alertText = ""
        while (b) {
            alertText += b.textContent + "    "
            b = a.iterateNext();
        }
        //alert(alertText);
        if (newX.indexOf('img') > -1) {
            showCart("image" + "'\'", 'tbItems', 1);
        } else {
            showCart(alertText + "'\'", 'tbItems', 1);
        }

        try {
            $("#myframe").contents().find(oldWrap).removeAttr("style", "background-color: #69c2fe;");

            $("#myframe").contents().find(getEleCss(newX)).attr("style", "background-color: #69c2fe;");
            oldWrap = getEleCss(newX);
        } catch (e) {
        }
        //end show




        if (count == 4) {
            document.getElementById("btnNext").disabled = false;
//            document.getElementById("btnPreview").disabled = true;
//            document.getElementById("btnAdd").disabled = true;
        }
        flagBack = 1;//danh dau dang back
        backProgressBar();
        count--;
        removeFromCart();
        //sessionStorage.cart = null;
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
    $('.progressRecipe .circle:nth-of-type(' + step + ')').removeClass('active');
    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ') .labelRecipe').html(step - 2);
    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ')').addClass('active').removeClass('done');
    if (indexComplete[step - 2] != "") {
        $('.progressRecipe .circle:nth-of-type(' + step + ')').addClass('done');
    }
//    if(indexComplete[step-3]==1)
//    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ')').addClass('active').removeClass('done');
//    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ') .labelRecipe').html(step - 2);
//    $('.progressRecipe .bar:nth-of-type(' + (step - 1) + ')').removeClass('active');
//    $('.progressRecipe .bar:nth-of-type(' + (step - 2) + ')').addClass('active').removeClass('done');
}

function progressBar() {
    $('.progressRecipe .circle:nth-of-type(' + step + ')').addClass('active');
    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ')').removeClass('active').addClass('done');
    $('.progressRecipe .circle:nth-of-type(' + (step - 1) + ') .labelRecipe').html('&#10003;');
//    $('.progressRecipe .bar:nth-of-type(' + (step - 1) + ')').addClass('active');
//    $('.progressRecipe .bar:nth-of-type(' + (step - 2) + ')').removeClass('active').addClass('done');
    step++;
}
;

//Get XPath from selected Element
function createXPathFromElement(elm) {
    var allNodes = document.getElementsByTagName('*');
    //alert(elm.firstChild.nodeValue);
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode)
    {
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
                segs.unshift(elm.localName.toLowerCase() + "[@id='" + elm.getAttribute('id') + "']");
            }
        } else if (elm.hasAttribute('class')) {
            segs.unshift(elm.localName.toLowerCase() + "[@class='" + elm.getAttribute('class') + "']");
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
}
;

//get XPath of image
function imgXPath(elm) {
    var allNodes = document.getElementsByTagName('*');
    //alert(elm.firstChild.nodeValue);
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode)
    {
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
                segs.unshift(elm.localName.toLowerCase() + "[@id='" + elm.getAttribute('id') + "']");
            }
        } else if (elm.hasAttribute('class')) {
            segs.unshift(elm.localName.toLowerCase() + "[@class='" + elm.getAttribute('class') + "']");
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
        if (str_array[i].search("div") != -1 && str_array[i].search("class") != -1) {
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
}
;


//Preview
function addToCart(selectedItem) {
    if (typeof (sessionStorage) !== "undefined") {
        if (sessionStorage.cart == null) {
            sessionStorage.cart = '';
        }
        if (count == 1) {
            sessionStorage.cart = '';
        }
        sessionStorage.cart = sessionStorage.cart +
                selectedItem + "'\'"
        //sessionStorage.cart = selectedItem + "'\'";
    } else {
        alert("browser is not supported storage!!!");
    }
}

//remove last element of cart
function removeFromCart() {
    //    if (sessionStorage.cart == null) {
    //        sessionStorage.cart = '';
    //    }
    var item = sessionStorage.cart.split("'\'");
    sessionStorage.cart = '';

    for (var i = 0; i < item.length - 2; i++) {
        sessionStorage.cart = sessionStorage.cart +
                item[i] + "'\'";
    }
}

function editXPath() {
    flagEditXPath = 1;
    //alert(document.getElementsByName('txtXPath')[0].value);
    //get newXpath
    deleteRow('tbItems', 1);
    var newX = document.getElementsByName('txtXPath')[0].value;
    value = newX;
    try {
        var a = window.frames[0].document.evaluate(newX
                , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
        var b = a.iterateNext();
        //???
        flagClick = 1;
        //Apply highlight div
//        alert(getEleCss(newX));       
    } catch (e) {
        flagClick = 0;
        showCart("INVALID XPATH" + "'\'", 'tbItems', 3);
        return;
    }
    //var b = a.iterateNext();
    var alertText = ""
    while (b) {
        alertText += b.textContent + "    "
        b = a.iterateNext();
    }
    //alert(alertText);
    showCart(alertText + "'\'", 'tbItems', 1);
    $("#myframe").contents().find(oldWrap).removeAttr("style", "background-color: #69c2fe;");

    $("#myframe").contents().find(getEleCss(newX)).attr("style", "background-color: #69c2fe;");
    oldWrap = getEleCss(newX);
}

function addRow(tableId, cells, type) {
    var tableElem = document.getElementById(tableId);
    var newCell;
    var newRow;
    for (var i = 0; i < cells.length - 1; i++) {
        newRow = tableElem.insertRow(tableElem.rows.length);

        //                    newCell = newRow.insertCell(newRow.cells.length);
        //                    newCell.innerHTML = ++col;
        newCell = newRow.insertCell(newRow.cells.length);
        if (type == 1) {
            newCell.innerHTML =
                    '<input type="text" class="form-control" name="txtTemp" value="' + cells[i] + '" size="76"/>'
                    + '<input type="button" class="btn btn-info m-b-less" value="Edit" onclick="showXPath()"/>';
        } else if (type == 2) {
            col++;
            switch (count) {
                case 1:
                    newCell.innerHTML =
                            '<input type="hidden" name="NAME" value="' + cells[i] + '" size="78"/>';
                    break;
                case 2:
                    newCell.innerHTML =
                            '<input type="hidden" name="ADDRESS" value="' + cells[i] + '" size="78"/>';
                    break;
                case 3:
                    newCell.innerHTML =
                            '<input type="hidden" name="USER_RATE" value="' + cells[i] + '" size="78"/>';
                    break;
                case 4:
                    newCell.innerHTML =
                            '<input type="hidden" name="MAP" value="' + cells[i] + '" size="78"/>';
                    break;
            }

        } else if (type == 3) {
            newCell.innerHTML =
                    '<input type="text" style="color:red;" name="txtTemp" class="form-control" value="' + cells[i] + '" size="76"/>'
                    + '<input type="button" class="btn btn-info m-b-less" value="Edit" onclick="showXPath()"/>';
        }
        //newCell.width = 200;
    }
    return newRow;
}
//0:item    1:Main
function deleteRow(tableId, rowNumber) {
    var tableElem = document.getElementById(tableId);
    if (rowNumber > 0 && rowNumber < tableElem.rows.length && tableId != 'tbMain') {
        tableElem.deleteRow(rowNumber);
    }
    //                else {
    //                    if(tableId!='tbMain'){
    //                        alert("Failed");}
    //                }
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
            + 'id="txtXPath" class="form-control" value="' + value + '" size="90" onchange="editXPath()">';
}

function addNew() {
    while (currentPosition < 4) {
        count++;
        progressBar();
        showCart(indexComplete[currentPosition] + "'\'", 'tbMain', 2);
        addToCart(indexComplete[currentPosition]);
        currentPosition++;
    }
    document.myForm.submit();
}

function openpopup(id) {
    //Append content first
    appendcontents(indexComplete);
//Calculate Page width and height 
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
    //Make the background div tag visible, overlap everything on screen
    var divbg = document.getElementById('bg');
    divbg.style.visibility = "visible";
    divbg.style.height = document.documentElement.scrollHeight + 'px';

    var divobj = document.getElementById(id);
    divobj.style.visibility = "visible";
    if (navigator.appName === "Microsoft Internet Explorer")
        computedStyle = divobj.currentStyle;
    else
        computedStyle = document.defaultView.getComputedStyle(divobj, null);
    //Get Div width and height from StyleSheet 
    var divWidth = computedStyle.width.replace('px', '');
    var divHeight = computedStyle.height.replace('px', '');
    var divLeft = (pageWidth - divWidth) / 3;
    var divTop = (pageHeight - divHeight) / 5;
    //Set Left and top coordinates for the div tag 
    divobj.style.left = divLeft + "px";
    divobj.style.top = divTop + "px";
    //Put a Close button for closing the popped up Div tag 
    if (divobj.innerHTML.indexOf("closepopup('" + id + "')") < 0)
        divobj.innerHTML = "<a href=\"#\" onclick=\"closepopup('" + id + "')\"><span class=\"btn btn-info m-b-less\" style=\"float: right\">X</span></a>" + divobj.innerHTML;
}
function closepopup(id) {
    var divbg = document.getElementById('bg');
    divbg.style.visibility = "hidden";
    var divobj = document.getElementById(id);
    divobj.style.visibility = "hidden";

//    count=0;
//    for(var i=0; i<5; i++){
//        //        backProgressBar();
//        back();
//    }
}
function appendcontents(item) {
    //var item = items.split("'\'");
    var content = '<h3>Information</h3><br/><table border="1" style="width: 485px" class="table"><thead></thead><tr><th style="width: 10%">Type</th><th style="width: 90%">Content</th></tr><tbody>'
            + '<tr><td><strong>Name</strong></td><td>';
    //get Name
    var a = window.frames[0].document.evaluate(item[0]
            , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
    var b = a.iterateNext();
    var alertText = ""
    while (b) {
        alertText += b.textContent + "<br/>";
        b = a.iterateNext();
    }
    content = content + alertText + '</td></tr><tr><td><strong>Address</strong></td><td>';

    //get Address
    a = window.frames[0].document.evaluate(item[1]
            , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
    b = a.iterateNext();
    alertText = ""
    while (b) {
        if (b.textContent.length < 150) {
            alertText += b.textContent + "<br/>";
        } else {
            alertText += b.textContent.substring(0, 220) + "...<br/>";
        }
        b = a.iterateNext();
    }
    content = content + alertText + '</td></tr><tr><td><strong>User Rate</strong></td><td>';
    //getRate
    a = window.frames[0].document.evaluate(item[2]
            , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
    b = a.iterateNext();
    alertText = "";
    while (b) {
        alertText += b.textContent + '</br>';
        b = a.iterateNext();
    }
    content = content + alertText + '</td></tr><tr><td><strong>Map</strong></td><td style="width: '
            + '200px;  vertical-align: top">';
    //getIngredient
    a = window.frames[0].document.evaluate(item[3] + '/@src'
            , window.frames[0].document, null, XPathResult.ANY_TYPE, null);
    b = a.iterateNext();
    alertText = "";
    while (b) {
        alertText += b.textContent + '<br/>';
        b = a.iterateNext();
    }
    content = content + alertText + '</td></tr></tbody></table>';

    appendto = document.getElementById('popup');
    appendto.innerHTML = content + '<input type="submit" class="btn btn-info m-b-10" id="btnAdd" name="btnAction" value="AddNewConfiguration" onclick="addNew()"/>';
//    sessionStorage.cart = '';
}