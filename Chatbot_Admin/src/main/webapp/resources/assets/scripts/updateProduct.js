function performClick(elemId) {
    var elem = document.getElementById(elemId);
    if (elem && document.createEvent) {
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
    }
}

function previewFile() {
    var preview = document.getElementById('thumbImg');
    var file = document.getElementById('file').files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}

$(document).ready(function () {
    if (updateResult == 'false') {
        notify("Product Already Existed!", "warning");
    }
});