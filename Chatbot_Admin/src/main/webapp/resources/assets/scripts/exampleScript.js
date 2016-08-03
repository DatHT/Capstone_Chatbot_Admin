var flagStep1 = true;
var flagStep2 = false;
var flagStep3 = false;
var flagStep4 = false;
var flagSave = false;

var xmlhttp;
var count = 0;
var resultIntents = "";
var lexicalList = JSON.parse(lexicals);

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

var lexicalMap = {};

$(document).ready(function () {
    var size = lexicalList.length;
    lexicalMap['any'] = 0;
    for (var i = 0; i < size; i++) {
        lexicalMap[lexicalList[i].name] = 0;
    }
});

function drop(ev) {

    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    if (ev.target.id != "") {
        ev.target.appendChild(document.getElementById(data));
        var contentDiv = "";
        var size = lexicalList.length;
        var name;

        for (var i = 0; i < size; i++) {
            name = lexicalList[i].name;
            if (data == (name + lexicalMap[name]).toString()) {
                lexicalMap[name] += 1;
            }

            contentDiv += "<div id='"
                + guid()
                + "' class='draggable' draggable='true' ondragstart='drag(event)'>"
                + name + "</div>"
        }
        $('#box-dragable').html(contentDiv);
    }
}

function addIntentRows(tableId, data) {
    var jsonData = JSON.parse(data);
    var tableElem = document.getElementById(tableId);
    deleteRows(tableElem);
    for (var i = 0; i < jsonData.userSays.length; i++) {
        var cells = [];
        cells[0] = i + 1;
        var listDisplay = [jsonData.userSays[i].data[0].text,
            jsonData.userSays[i].data[0].alias];

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
    // remove userSay
    for (var i = 0; i < jsonData.userSays.length; i++) {
        if (jsonData.userSays[i].data[0].text === deleteId) {
            jsonData.userSays.splice(i, 1);
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
                notify("It has been deleted!", "info");
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
    xmlhttp.send("pattern=" + JSON.stringify(jsonData) + "&id=" + intentId
        + "&" + param + "=" + token + "&trainingSentence=" + chosenExample);
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

            if (flagStep2 || flagStep4) {
                insertPattern(id);
            }

            if (flagSave) {

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
                                    return "<button data-row-name='"
                                        + row.name
                                        + "' id='"
                                        + row.name
                                        + "' class='btn palette-Deep-Orange btn-icon bg waves-effect waves-circle waves-float btn-delete-example'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button>";
                                },
                                "commands-name": function (column, row) {
                                    var data = row.name.split(",");
                                    return data[0] + "<br/>" + data[1];
                                }
                            }
                        })
                    .on(
                        "loaded.rs.jquery.bootgrid",
                        function () {
                            /*
                             * Executes after data is loaded and
                             * rendered
                             */
                            $('#tableIntent')
                                .find(".btn-delete-example")
                                .on(
                                    "click",
                                    function (e) {
                                        var rowname = $(this)
                                            .data(
                                                "row-name");
                                        var data = rowname
                                            .split(",")[0];
                                        swal(
                                            {
                                                title: "Are you sure to delete this pattern "
                                                + data
                                                + "?",
                                                text: "You will not be able to recover it!",
                                                type: "warning",
                                                showCancelButton: true,
                                                confirmButtonColor: "#DD6B55",
                                                confirmButtonText: "Yes, delete it!",
                                                closeOnConfirm: true
                                            },
                                            function () {
                                                deletePattern(data);
                                            });
                                    });
                        });
            }

            $('#loadingModal').modal('hide');
        }

    }
    xmlhttp.open("GET", "/chatbot_admin/example/" + id, true);
    xmlhttp.send();
}

function insertPattern(intentId) {
    $('#loadingModal').modal('show');
    if (resultIntents != "") {
        var div = document.getElementById("containerDiv");
        var divs = div.getElementsByClassName("draggable");
        var newPattern = "";
        var anyCount = 0;

        // TODO index
        for (var i = 0; i < divs.length; i++) {
            var temp = "";
            if (divs[i].textContent.trim() == 'any') {

                if (anyCount == 0) {
                    temp = "@sys.any:" + divs[i].textContent.trim();
                } else {
                    temp = "@sys.any:" + divs[i].textContent.trim() + anyCount;
                }
                newPattern += temp + " ";
                anyCount++;
            } else {
                newPattern += "@" + divs[i].textContent.trim() + ":"
                    + divs[i].textContent.trim() + " ";
            }

        }
        // End
        if (newPattern != "") {
            var chosenExample = $("#chosenExample p").text();

            newPattern = newPattern.trim();
            var jsonData = JSON.parse(resultIntents);

            delete jsonData.priority;
            delete jsonData.webhookUsed;
            delete jsonData.lastUpdate;
            delete jsonData.auto;
            jsonData.templates.push(newPattern);

            // add example to userSay
            var data = [];
            var example = {
                "text": newPattern,
                "alias": chosenExample
            };
            data.push(example);

            var userSay = {
                "id": generateUUID(),
                "data": data,
                "isTemplate": true,
                "count": 0
            };
            jsonData.userSays.push(userSay);

            // var cate = document.getElementById("selectIntent");
            // var intentId = cate.options[cate.selectedIndex].value;
            // action here
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var result = xmlhttp.responseText;
                    if (xmlhttp.responseText.indexOf('success') > 0) {
                        flagSave = true;
                        if (flagStep2) {
                            showCard3();
                            flagSave = false;
                        }
                        $('#loadingModal').modal('hide');
                        notify("Your pattern was saved", "info");
                        if (flagStep4) {
                            location.reload();
                        }

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
                + intentId + "&" + param + "=" + token
                + "&trainingSentence=" + chosenExample);
            // action here

        } else {

            notify("Please drag an item to the box", "warning");
        }

    } else {
        notify("Please select intent category first", "warning");
    }

}

function showCard1Back() {
    document.getElementById("card-step2").style.display = "none";
    document.getElementById("card-step1").style.display = "block";
    $('#progress-status').css('width', 0 + '%').attr('aria-valuenow', 0);
    flagStep1 = true;
    flagStep2 = false;
}

function displayStep2() {
    var step1Example = document.getElementById("exampleList");
    var selectId = step1Example.options[step1Example.selectedIndex].value;
    var ownExample = document.getElementById("own-example").value;
    if (selectId == "empty") {
        if (ownExample === undefined || ownExample == "") {
            notify("Please Choose example or type your own example first",
                "info");
        } else {
            document.getElementById("your-example").innerHTML = ownExample;
            showCard2(ownExample);
            showTextInStep2(ownExample);
        }
    } else {
        if (ownExample === undefined || ownExample == "") {
            document.getElementById("your-example").innerHTML = selectId;
            showCard2();
            showTextInStep2(selectId);
        } else {
            notify("You just choose example or input the text", "info");
        }
    }
}

function showCard2(ownExample) {
    document.getElementById("card-step1").style.display = "none";
    document.getElementById("card-step2").style.display = "block";
    $('#progress-status').css('width', 25 + '%').attr('aria-valuenow', 25);
    flagStep1 = false;
    flagStep2 = true;
}

function showCard2Back() {
    document.getElementById("card-step3").style.display = "none";
    document.getElementById("card-step2").style.display = "block";
    $('#progress-status').css('width', 25 + '%').attr('aria-valuenow', 25);
    flagStep3 = false;
    flagStep2 = true;
}

function displayStep3() {
    var cate = document.getElementById("selectIntent");
    var intentId = "";
    var testName = "User_Test";
    for (var i = 0; i < cate.options.length; i++) {
        if ((cate.options[i].text).localeCompare(testName) == 0) {
            intentId = cate.options[i].value;
        }
    }
    if (intentId != "") {
        loadIntent(intentId);

    }

}

function showCard3() {
    document.getElementById("card-step2").style.display = "none";
    document.getElementById("card-step3").style.display = "block";
    $('#progress-status').css('width', 50 + '%').attr('aria-valuenow', 50);
    flagStep2 = false;
    flagStep3 = true;
}

function showCard3Back() {
    document.getElementById("card-step4").style.display = "none";
    document.getElementById("card-step3").style.display = "block";
    $('#progress-status').css('width', 50 + '%').attr('aria-valuenow', 50);
    flagStep4 = false;
    flagStep3 = true;
}

function displaStep4() {
    document.getElementById("card-step3").style.display = "none";
    document.getElementById("card-step4").style.display = "block";
    $('#progress-status').css('width', 75 + '%').attr('aria-valuenow', 75);
    flagStep3 = false;
    flagStep4 = true;
}

function finalStep() {
    var cate = document.getElementById("selectIntent");
    var intentId = cate.options[cate.selectedIndex].value;
    $('#progress-status').css('width', 100 + '%').attr('aria-valuenow', 100);
    loadIntent(intentId);

}

function showTextInStep2(example) {

    var d = new Date();

    // display example
    var chosenExample = document.getElementById("chosenExample");
    chosenExample.innerHTML="";
    var para = document.createElement("p");
    var node = document.createTextNode(example);
    para.appendChild(node);
    chosenExample.appendChild(para);

    // create textbox for user chosen
    var pContainer = document.getElementById('user-say-container');
    pContainer
        .addEventListener(
            'mouseup',
            function () {
                var text = getTextSelection().trim();
                if (text && listPhrase[text] === undefined) {
                    var divId = guid();
                    var divDrop = "<div id='"
                        + divId
                        + "' class='droptarget' ondrop='drop(event)' ondragover='allowDrop(event)'>"
                        + text
                        + " <i class='zmdi zmdi-close-circle-o' style='vertical-align:top' onclick='deleteWord("
                        + "`#" + divId + "`" + ")'></i>" + "</div>"

                    $("#containerDiv").append(divDrop);

                }
            });

}

function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000).toString(16)
            .substring(1);
    }

    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4()
        + s4() + s4();
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
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
        function (c) {
            var r = (d + Math.random() * 16) % 16 | 0;
            d = Math.floor(d / 16);
            return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
    return uuid;
};

function handleShowBags() {
    // delete type own example
    document.getElementById("own-example").value = "";
    document.getElementById("train-own-example").style.display = "none";
    document.getElementById("train-bag").style.display = "block";
}

function handleShowType() {
    // delete select in bags
    document.getElementById("exampleList").value = "empty";
    document.getElementById("train-bag").style.display = "none";
    document.getElementById("train-own-example").style.display = "block";
}

function processTraining() {
    if (flagStep1) {
        displayStep2();
    } else if (flagStep2) {
        displayStep3();
    } else if (flagStep3) {
        displaStep4();
    } else if (flagStep4) {
        finalStep();
    }
}

function backTraining() {
    if (flagStep2) {
        showCard1Back();
    } else if (flagStep3) {
        showCard2Back();
    } else if (flagStep4) {
        showCard3Back();
    }
}

function sendTestQuery() {
    var userQuery = document.getElementById("user-query").value;
    if (userQuery != "" && userQuery !== undefined) {
        document.getElementById("user-query").value = '';
        createDivChat("pull-right", userQuery);
        sendQueryToServer(userQuery);

    }
}

function sendQueryToServer(query) {
    // action here
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var result = xmlhttp.responseText;
            createDivChat("pull-left", result);
        }

    }
    var param = document.getElementById("paramName").value;
    var token = document.getElementById("token").value;
    xmlhttp.open("POST", "/chatbot_admin/example/testQuery", true);
    xmlhttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded;charset=utf-8");
    xmlhttp.send(param + "=" + token + "&trainingSentence=" + query);
    // action here
}

function createDivChat(pullPosition, textNode) {
    var chatFlow = document.getElementById("chat-flow");
    var wrapper = document.createElement("div");
    wrapper.className = "list-group-item media";

    var position = document.createElement("div");
    position.className = pullPosition;

    var img = document.createElement("img");
    img.className = "avatar-img";
    if (pullPosition == "pull-right") {
        img.src = "resources/assets/img/profile-pics/2.jpg";
    } else
        img.src = "resources/assets/img/profile-pics/4.jpg";
    position.appendChild(img);

    var bodyText = document.createElement("div");
    bodyText.className = "media-body";

    var userText = document.createElement("div");
    userText.className = "msb-item";

    var node = document.createTextNode(textNode);
    userText.appendChild(node);

    bodyText.appendChild(userText);

    wrapper.appendChild(position);
    wrapper.appendChild(bodyText);

    chatFlow.appendChild(wrapper);
}


function suggestPattern() {
    $('#loadingModal').modal('show');
    var ownExample = document.getElementById("own-example").value;
    var param = document.getElementById("paramName").value;
    var token = document.getElementById("token").value;
    $('#containerDiv').empty();
    var data = {
        "sentence": ownExample,
    };

    $.ajax({
        type: "POST",
        data: data,
        url: "example/genPattern?" + param + "=" + token,
        success: function (data) {
            console.info(data);
            if (data != "Sorry something wrong here") {
                data.forEach(function (d, index) {
                    var words = d.words;
                    var lexical = d.lexical;
                    var divId = guid();
                    if (lexical.includes('any')) {
                        lexical = 'any';
                    }

                    var divDrop = "<div id='"
                        + divId
                        + "' class='droptarget' ondrop='drop(event)' ondragover='allowDrop(event)'>"
                        + words
                        + " <i class='zmdi zmdi-close-circle-o' style='vertical-align:top' onclick='deleteWord("
                        + "`#" + divId + "`" + ")'></i>" + "<div id='"
                        + guid()
                        + "' class='draggable' draggable='true' ondragstart='drag(event)'>"
                        + lexical + "</div>" + "</div>"


                    $("#containerDiv").append(divDrop);
                });
            }

            $('#loadingModal').modal('hide');
        }
    });
}