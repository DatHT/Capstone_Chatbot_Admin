var regObjCfg, regObjPage;
var xmlDOMCfg, xmlDOMPage;
parser = new DOMParser();
var valueButton;

function getVal(value) {
    valueButton = value;
}

//$(document).ready(function(){ alert("start") }) 

function loadProcess(selectName) {
    xmlDOMCfg = parser.parseFromString(regObjCfg, "text/xml");
    xmlDOMPage = parser.parseFromString(regObjPage, "text/xml");
    if ((!regObjCfg) || (!regObjPage)) {
        return false;
    }
    if ((regObjCfg) && (regObjPage)) {
        xmlDOMCfg.async = false;
        xmlDOMPage.async = false;
        loadNode(xmlDOMCfg, xmlDOMPage, selectName);
    }
}

function loadNode(nodeCfg, nodePage, selectName) {
    if ((nodeCfg == null) || (nodePage == null)) {
        return;
    }    
    var resultCfg = nodeCfg.documentElement.childNodes;
    var resultPage = nodePage.documentElement.childNodes;
    for (var i = 0; i < resultCfg.length; i++) {
        for (var j = 0; j < resultPage.length; j++) {
            if (resultCfg[i].getAttribute('site') == resultPage[j].getAttribute('site')) {
            	var nextPageNode = resultPage[j].getElementsByTagName('nextPage')[0];
            	var y = nextPageNode.childNodes[0];
            	var z = y.nodeValue;
            	if(z!='N/A'){
            		var x = document.getElementById('selectSite');
            		var opt = document.createElement('option');
                    opt.value = resultPage[j].getAttribute('site');
                    opt.innerHTML = resultPage[j].getAttribute('site');
                    //c.text = result[i].getAttribute('site');
                    x.appendChild(opt);
            	}
            	if(z=='N/A'){
            		var x = document.getElementById('selectPage');
            		var opt = document.createElement('option');
                    opt.value = resultPage[j].getAttribute('site');
                    opt.innerHTML = resultPage[j].getAttribute('site');
                    //c.text = result[i].getAttribute('site');
                    x.appendChild(opt);
            	}
                
            }
        }
    }
}

function checkInputConfig(form) {
    var flagRedirect = 0;
    var str = form.txtURL.value;
    var result = "";
    var x;
    var str_array = str.split("/");
    for (var i = 0; i < 3; i++) {
        result = result + str_array[i] + "/";
    }
    if (valueButton == "Set Parser Config") {
        if (regObjCfg.search(result) != -1) {
//            swal({
//                title: "Are you sure?",
//                text: "This configuration existed. Do you want to config again?",
//                type: "warning",
//                showCancelButton: true,
//                confirmButtonColor: "#DD6B55",
//                onfirmButtonText: "Yes, delete it!",
//                cancelButtonText: "No, cancel plx!",
//                closeOnConfirm: true,
//                closeOnCancel: false
//            },
//            function (isConfirm) {
//                if (isConfirm) {
//                    flagRedirect = 1;
//                    alert(flagRedirect);
//                    //swal("Deleted!", "Your imaginary file has been deleted.", "success");
//                } else {
//                    flagRedirect = 0;
//                    swal("Cancelled", "Your imaginary file is safe :)", "error");   
//                    return false;
//                }
//            });
//            alert(flagRedirect);
//            if (flagRedirect === 1) {
//            } else {
//                return false;
//            }

//            alert(x)
            if (confirm("This configuration existed. Do you want to config again?") == true) {
            } else {
                return false;
            }
        }
    } else if (valueButton == "Set List Page") {
        if (regObjPage.search(result) != -1) {
            if (confirm("This configuration existed. Do you want to config again?") == true) {
            } else {
                return false;
            }
        }
    }
    return true;
}