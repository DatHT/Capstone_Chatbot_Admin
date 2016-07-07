$(document).ready(function () {
    if (updateResult == 'false') {
        notify("Product Already Existed!", "warning");
    } else if (updateResult == 'true') {
        notify("Update Successfully!", "info");
    }
});