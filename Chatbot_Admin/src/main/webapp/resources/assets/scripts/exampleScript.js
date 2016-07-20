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
    // var node = document.createElement("DIV");
    // var textnode = document.createTextNode("Dat");
    // alert(data);
    // node.appendChild(textnode);
    // document.getElementById("box-dragable").appendChild(node);
}

var xmlhttp;
var count = 0;
var resultIntents = "";

function addIntentRows(tableId, data) {
    var jsonData = JSON.parse(data);
    var tableElem = document.getElementById(tableId);
    deleteRows(tableElem);
    for (var i = 0; i < jsonData.userSays.length; i++) {
        var cells = [];
        cells[0] = i + 1;
        var listDisplay = [jsonData.userSays[i].data[0].text, jsonData.userSays[i].data[0].alias];
        
        cells[1] = listDisplay;
        cells[2] = "<button id='"
            + jsonData.templates[i]
            + "' onclick='showDeleteModal(this.id)' class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";

        addRow(tableElem, cells);
    }

}

function deletePattern(deleteId) {
    $('#deleteModal').modal('hide');
    var jsonData = JSON.parse(resultIntents);
    delete jsonData.priority;
    delete jsonData.webhookUsed;
    delete jsonData.lastUpdate;
    delete jsonData.auto;
    jsonData.templates.remove(deleteId);
    //remove userSay
    for(var i = 0; i < jsonData.userSays.length; i++) {
    	if(jsonData.userSays[i].data[0].text === deleteId) {
    		jsonData.userSays.splice(i,1);;
    	}
    }
    
    
    var cate = document.getElementById("selectIntent");
    var intentId = cate.options[cate.selectedIndex].value;
    // action here
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var result = xmlhttp.responseText;
            if (result.indexOf("success") > -1) {
                swal("Good job!", xmlhttp.responseText, "success");
            }
            if (result.indexOf("wrong") > -1) {
                swal("Error!", xmlhttp.responseText, "error");
            }
            if (result.indexOf("Sorry") > -1) {
                swal("Dupplicate!", xmlhttp.responseText, "info");
            }
            loadIntent(cate);
        }

    }
    var param = document.getElementById("paramName").value;
    var token = document.getElementById("token").value;
    var chosenExample = "";
    xmlhttp.open("POST", "/chatbot_admin/example/add", true);
    xmlhttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded;charset=utf-8");
    xmlhttp.send("pattern=" + JSON.stringify(jsonData) + "&id=" + intentId + "&" + param + "=" + token
        + "&trainingSentence=" + chosenExample);
    // action here

}

function moveDiv(divFromId, divToId) {
    var divFrom = document.getElementById(divFromId);
    var divTo = document.getElementById(divToId);
    var divs = divFrom.getElementsByTagName("div");
    var num = divs.length;
    for (var i = 0; i, i < num; i++) {
        divTo.appendChild(divs[i]);
        // divFrom.removeChild(divs[i]);
    }
}

function loadIntent(id) {
    $('#loadingModal').modal('show');
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            resultIntents = xmlhttp.responseText;
            $('#tableIntent').bootgrid("destroy");
            addIntentRows("intentTable", resultIntents);
            $('#tableIntent')
                .bootgrid(
                    {
                        css: {
                            icon: 'zmdi icon',
                            iconColumns: 'zmdi-view-module',
                            iconDown: 'zmdi-expand-more',
                            iconRefresh: 'zmdi-refresh',
                            iconUp: 'zmdi-expand-less'
                        },
                        formatters: {
                            "commands": function (column, row) {
                                return "<button data-row-name='"+row.name+"' id='"
                                    + row.name
                                    + "' class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float btn-delete-example'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
                            },
                            "commands-name": function (column, row) {
                            	var n = row.name.indexOf(",");
                                return row.name.substring(0, n) + "<br/>" + row.name.substring(n + 1);
                            }
                        }
                    }).on("loaded.rs.jquery.bootgrid", function() {
                    	/* Executes after data is loaded and rendered */
                        $('#tableIntent').find(".btn-delete-example").on("click", function(e) {
                        	var rowname = $(this).data("row-name");
                        	var data = rowname.split(",")[0];
                        	swal({
                                title: "Are you sure to delete this pattern " +  data + "?",
                                text: "You will not be able to recover it!",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "Yes, delete it!",
                                closeOnConfirm: false
                            }, function(){
                            	deletePattern(data);
                            });
                        });
                    });
            $('#loadingModal').modal('hide');
        }

    }
    xmlhttp.open("GET", "/chatbot_admin/example/" + id.value, true);
    xmlhttp.send();
}

function insertPattern() {
    if (resultIntents != "") {
        var div = document.getElementById("containerDiv");
        var divs = div.getElementsByClassName("draggable");
        var newPattern = "";
        var anyCount = 0;
        for (var i = 0; i < divs.length; i++) {
            var temp = "";
            if (divs[i].innerHTML == 'any') {

                if (anyCount == 0) {
                    temp = "@sys.any:" + divs[i].innerHTML;
                } else {
                    temp = "@sys.any:" + divs[i].innerHTML + anyCount;
                }
                newPattern += temp + " ";
                anyCount++;
            } else {
                newPattern += "@" + divs[i].innerHTML + ":" + divs[i].innerHTML
                    + " ";
            }

        }
        if (newPattern != "") {
        	var chosenExample = $("#chosenExample p").text();
        	
        	newPattern = newPattern.trim();
            var jsonData = JSON.parse(resultIntents);

            delete jsonData.priority;
            delete jsonData.webhookUsed;
            delete jsonData.lastUpdate;
            delete jsonData.auto;
            jsonData.templates.push(newPattern);
            
            //add example to userSay
            var data = [];
            var example = {
            		"text": newPattern, 
            		"alias" : chosenExample
            		};
            data.push(example);
            
            var userSay = {
            		"id" : generateUUID(),
            		"data" : data,
            		"isTemplate": true,
            	    "count": 0
            };
            jsonData.userSays.push(userSay);

            var cate = document.getElementById("selectIntent");
            var intentId = cate.options[cate.selectedIndex].value;
            // action here
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var result = xmlhttp.responseText;
                    if (xmlhttp.responseText.indexOf('success') > 0) {
                        swal({title: "Good job!", text: xmlhttp.responseText, type: "success"},
                            function () {
                                setTimeout(function () {
                                    location.reload();
                                }, 500);

                            }
                        );
                    } else {
                        swal("Sorry!", xmlhttp.responseText, "error");
                    }

                }

            }
            
            var param = document.getElementById("paramName").value;
            var token = document.getElementById("token").value;
            xmlhttp.open("POST", "/chatbot_admin/example/add", true);
            xmlhttp.setRequestHeader("Content-type",
                "application/x-www-form-urlencoded;charset=utf-8");
            xmlhttp.send("pattern=" + JSON.stringify(jsonData) + "&id="
                + intentId + "&" + param + "=" + token +
                "&trainingSentence=" + chosenExample);
            // action here

        } else {

            notify("Please drag an item to the box", "warning");
        }

    } else {
        notify("Please select intent category first", "warning");
    }

}

function displayStep2() {
    var step1 = document.getElementById("selectIntent");
    var selectId = step1.options[step1.selectedIndex].value;
    if (selectId == "empty") {
        notify("Please Choose intent first!", "info");
    } else {
        document.getElementById("step1").className = "col-lg-2";
        var step2 = document.getElementById("step2");
        step2.style.display = "block";

    }
}
function displayStep3() {

    var d = new Date();

    var step1 = document.getElementById("exampleList");
    var selectId = step1.options[step1.selectedIndex].value;
    if (selectId == "empty") {
        notify("Please Choose Example first!", "info");
    } else {
        document.getElementById("step2").className = "col-lg-2";
        var step3 = document.getElementById("step3");
        step3.style.display = "block";
        // display example
        var chosenExample = document.getElementById("chosenExample");
        var para = document.createElement("p");
        var node = document.createTextNode(selectId);
        para.appendChild(node);
        chosenExample.appendChild(para);

        // create textbox for user chosen
        var pContainer = document.getElementById('user-say-container');
        pContainer.addEventListener('mouseup', function () {
            var text = getTextSelection().trim();
            if (text && listPhrase[text] === undefined) {
                var divId = guid();
                var divDrop = "<div id='" + divId + "' class='droptarget' ondrop='drop(event)' ondragover='allowDrop(event)'>" +
                    text + " <i class='zmdi zmdi-close-circle-o' style='vertical-align:top' onclick='deleteWord(" + "`#" + divId + "`" + ")'></i>" +
                    "</div>"

                $("#containerDiv").append(divDrop);

            }
        });

    }
}

function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }

    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}

function deleteWord(id) {
    $(id).remove();
}

function showDeleteModal(name) {
    $('#deletePatternName').text(name);
    $('#deleteModal').modal('show');
}

function generateUUID() {
    var d = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (d + Math.random()*16)%16 | 0;
        d = Math.floor(d/16);
        return (c=='x' ? r : (r&0x3|0x8)).toString(16);
    });
    return uuid;
};