function validName(fieldId, divId, errorId, emptyMessage) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text(emptyMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validAddress(fieldId, divId, errorId, emptyMessage) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text(emptyMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validRating(fieldId, divId, errorId, invalidMessage) {
    var value = $(fieldId).val();

    if (value.length < 1) {
        $(divId).removeClass("has-error");
        $(errorId).text("");
        return 1;
    }

    if (!isInt(value) && !isFloat(value)) {
        $(divId).addClass("has-error");
        $(errorId).text(invalidMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }

    if (value < 0 || value > 10) {
        $(divId).addClass("has-error");
        $(errorId).text(invalidMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }

    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validRelatedUrl(fieldId, divId, errorId, emptyMessage, invalidMessage) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text(emptyMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }

    if (!isUrl(value)) {
        $(divId).addClass("has-error");
        $(errorId).text(invalidMessage);
        $(errorId).css("visibility", "visible");
        return 0;
    }

    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validOnSubmit(nameId, divNameId, errorNameId, addressId, divAddressIdId, errorAddressIdId,
                       rateId, divRateId, errorRateId, urlId, divUrlId, errorUrlId,
                       emptyNameMessage, emptyAddressMessage, invalidRatingMessage,
                       emptyUrlMessage, invalidUrlMessage) {
    var counter = 0;

    if (validName(nameId, divNameId, errorNameId, emptyNameMessage) == 0) {
        counter++;
    }

    if (validAddress(addressId, divAddressIdId, errorAddressIdId, emptyAddressMessage) == 0) {
        counter++;
    }

    if (validRating(rateId, divRateId, errorRateId, invalidRatingMessage) == 0) {
        counter++;
    }

    if (validRelatedUrl(urlId, divUrlId, errorUrlId, emptyUrlMessage, invalidUrlMessage) == 0) {
        counter++;
    }

    if (counter == 0) {
        return true;
    }

    return false;
}

function isInt(n) {
    return n % 1 == 0;
}

function isFloat(n) {
    return Number(n) == n && n % 1 !== 0;
}

function isUrl(s) {
    var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
    return regexp.test(s);
}