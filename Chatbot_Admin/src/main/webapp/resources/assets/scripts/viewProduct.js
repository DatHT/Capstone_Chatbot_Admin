$(document).ready(function () {

    if (addResult == 'true') {
        notify("Add Product Successfully!", "info");
    }

    $("#data-table-basic").bootgrid({
        ajax: true,
        post: function () {
            /* To accumulate custom parameter with the request object */
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
            };
        },
        url: "loadProduct?" + tokenName + "=" + tokenValue,
        formatters: {
            "commandsDetails": function (column, row) {
                return "<a class='btn btn-info btn-icon waves-effect waves-circle waves-float' "
                    + "href='viewProductDetails?productId="
                    + row.productId
                    + "'>"
                    + "<i class='zmdi zmdi-more-vert'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsUpdate": function (column, row) {
                return "<a class='btn btn-success btn-icon waves-effect waves-circle waves-float' "
                    + "href='viewUpdateProduct?productId="
                    + row.productId
                    + "'>"
                    + "<i class='zmdi zmdi-edit zmdi-hc-fw'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsDelete": function (column, row) {
                return "<button class='btn btn-danger btn-icon waves-effect waves-circle waves-float' onclick='showDeleteModal("
                    + "`" + row.productId + "`"
                    + ")'>" +
                    "<i class='zmdi zmdi-close'>" +
                    "</i>" +
                    "</button>";
            }
        },
        ss: {
            icon: 'zmdi icon',
            iconColumns: 'zmdi-view-module',
            iconDown: 'zmdi-expand-more',
            iconRefresh: 'zmdi-refresh',
            iconUp: 'zmdi-expand-less'
        },
    });
});

function showDeleteModal(productId, addressName) {
    $('#deleteProductId').val(productId);
    $('#deleteAddressName').val(addressName);
    $('#deleteModal').modal('show');
}