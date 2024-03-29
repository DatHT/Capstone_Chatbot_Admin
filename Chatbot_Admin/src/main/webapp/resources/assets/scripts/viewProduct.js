$(document).ready(function () {

    if (addResult == 'true') {
        notify("Add Product Successfully!", "info");
    }
    
    if (updateResult == 'true') {
        notify("Update Successfully!", "info");
    }

    if (deleteResult == 'true') {
        notify("Delete Product Successfully!", "info");
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
                return "<button data-row-product-id='"+row.productId+"' class='btn btn-danger btn-icon waves-effect waves-circle waves-float btn-delete-product' >" +
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
    }).on("loaded.rs.jquery.bootgrid", function() {
    	/* Executes after data is loaded and rendered */
        $('#data-table-basic').find(".btn-delete-product").on("click", function(e) {
        	var productId = $(this).data("row-product-id");
        	swal({
                title: "Are you sure?",
                text: "You will not be able to recover it!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                closeOnConfirm: false
            }, function(){
            	window.location.href= 'deleteProduct?deleteProductId=' + productId;
            });
        });
    });
});

function showDeleteModal(productId) {
    $('#deleteProductId').val(productId);
    $('#deleteModal').modal('show');
}