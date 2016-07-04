function showAddModal() {
    $('#add-form').attr('action', 'addProduct');
    $('#user-say-in-modal').text('Add New Product');
    $('#myModal').modal('show');
}

function showUpdateModal(productId, productName, addressName, urlRelate, rate, restaurantName, districtName) {
    $('#updateProductId').val(productId);

    $('#name').val(productName);
    $('#address').val(addressName);
    $('#relatedUrl').val(urlRelate);
    $('#rating').val(rate);
    $('#restaurant').val(restaurantName);
    $("#district").val(districtName);

    $('#tmpName').val(productName);
    $('#tmpAddress').val(addressName);
    $('#tmpRelatedUrl').val(urlRelate);
    $('#tmpRating').val(rate);
    $('#tmpRestaurant').val(restaurantName);
    $("#tmpDistrict").val(districtName);

    console.info($('#tmpName').val());

    $('#add-form').attr('action', 'updateProduct');
    $('#user-say-in-modal').text('Update Product');
    $('#myModal').modal('show');
}

function showDeleteModal(productId, addressName) {
    $('#deleteProductId').val(productId);
    $('#deleteAddressName').val(addressName);
    $('#deleteModal').modal('show');
}

function validName(fieldId, divId, errorId) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text("Please enter name");
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validAddress(fieldId, divId, errorId) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text("Please enter address");
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validRating(fieldId, divId, errorId) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text("Please enter rating");
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validRestaurant(fieldId, divId, errorId) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text("Please enter restaurant");
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validRelatedUrl(fieldId, divId, errorId) {
    var value = $(fieldId).val();
    if (value.length < 1) {
        $(divId).addClass("has-error");
        $(errorId).text("Please enter related url");
        $(errorId).css("visibility", "visible");
        return 0;
    }
    $(divId).removeClass("has-error");
    $(errorId).text("");
    return 1;
}

function validOnSubmit(formId, nameId, divNameId, errorNameId, addressId, divAddressIdId, errorAddressIdId,
                       rateId, divRateId, errorRateId, resId, divResId, errorResId,
                       urlId, divUrlId, errorUrlId) {
    var counter = 0;
    var changedCount = 0;

    if ($('#name').val().toLowerCase() != $('#tmpName').val().toLowerCase()) {
        changedCount++;
    }

    if ($('#address').val().toLowerCase() != $('#tmpAddress').val().toLowerCase()) {
        changedCount++;
    }

    if ($('#relatedUrl').val().toLowerCase() != $('#tmpRelatedUrl').val().toLowerCase()) {
        changedCount++;
    }

    if ($('#rating').val().toLowerCase() != $('#tmpRating').val().toLowerCase()) {
        changedCount++;
    }

    if ($('#restaurant').val().toLowerCase() != $('#tmpRestaurant').val().toLowerCase()) {
        changedCount++;
    }

    if ($('#district').val().toLowerCase() != $('#tmpDistrict').val().toLowerCase()) {
        changedCount++;
    }

    if (validName(nameId, divNameId, errorNameId) == 0) {
        counter++;
    }

    if (validAddress(addressId, divAddressIdId, errorAddressIdId) == 0) {
        counter++;
    }

    if (validRating(rateId, divRateId, errorRateId) == 0) {
        counter++;
    }

    if (validRestaurant(resId, divResId, errorResId) == 0) {
        counter++;
    }

    if (validRelatedUrl(urlId, divUrlId, errorUrlId) == 0) {
        counter++;
    }

    if (changedCount > 0) {
        if (counter == 0) {
            $(formId).submit();
        }
    } else {
        swal("", "Nothing has changed!", "warning");
    }
}