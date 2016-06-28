function validName() {
    var value = $("#name").val();
    if (value.length < 1) {
        $("#div-name").addClass("has-error");
        $("#error-name").text("Please enter name");
        $("#error-name").css("visibility", "visible");
        return 0;
    }
    $("#div-name").removeClass("has-error");
    $("#error-name").text("");
    $("#error-name").css("visibility", "hidden");
    return 1;
}

function validAddress() {
    var value = $("#address").val();
    if (value.length < 1) {
        $("#div-address").addClass("has-error");
        $("#error-address").text("Please enter address");
        $("#error-address").css("visibility", "visible");
        return 0;
    }
    $("#div-address").removeClass("has-error");
    $("#error-address").text("");
    $("#error-address").css("visibility", "hidden");
    return 1;
}

function validRating() {
    var value = $("#rating").val();
    if (value.length < 1) {
        $("#div-rating").addClass("has-error");
        $("#error-rating").text("Please enter rating");
        $("#error-rating").css("visibility", "visible");
        return 0;
    }
    $("#div-rating").removeClass("has-error");
    $("#error-rating").text("");
    $("#error-rating").css("visibility", "hidden");
    return 1;
}

function validRestaurant() {
    var value = $("#restaurant").val();
    if (value.length < 1) {
        $("#div-restaurant").addClass("has-error");
        $("#error-restaurant").text("Please enter restaurant");
        $("#error-restaurant").css("visibility", "visible");
        return 0;
    }
    $("#div-restaurant").removeClass("has-error");
    $("#error-restaurant").text("");
    $("#error-restaurant").css("visibility", "hidden");
    return 1;
}

function validRelatedUrl() {
    var value = $("#relatedUrl").val();
    if (value.length < 1) {
        $("#div-relatedUrl").addClass("has-error");
        $("#error-relatedUrl").text("Please enter related url");
        $("#error-relatedUrl").css("visibility", "visible");
        return 0;
    }
    $("#div-relatedUrl").removeClass("has-error");
    $("#error-relatedUrl").text("");
    $("#error-relatedUrl").css("visibility", "hidden");
    return 1;
}

function validOnSubmit() {
    var counter = 0;

    if (validName() == 0) {
        counter++;
    }

    if (validAddress() == 0) {
        counter++;
    }

    if (validRating() == 0) {
        counter++;
    }

    if (validRestaurant() == 0) {
        counter++;
    }

    if (validRelatedUrl() == 0) {
        counter++;
    }

    if (counter == 0) {
        $("#add-form").submit();
    }
}