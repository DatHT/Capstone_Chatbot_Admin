$(document).ready(function () {
    $("#data-table-basic").bootgrid({
        ajax: true,
        post: function () {
            console.info(tokenName);
            console.info(tokenValue);
            var myVars = {
                "tokenName": tokenName,
            } // notice no co
            /* To accumulate custom parameter with the request object */
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
            };
        },
        url: "loadProduct?" + tokenName + "=" + tokenValue,
        formatters: {
            "commandsDetails": function (column, row) {
                return "<a class='btn btn-info btn-icon waves-effect waves-circle waves-float' "
                    + "href='${pageContext.request.contextPath}/viewProductDetails?productId="
                    + row.productId
                    + "'>"
                    + "<i class='zmdi zmdi-more-vert'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsUpdate": function (column, row) {
                return "<button class='btn btn-success btn-icon waves-effect waves-circle waves-float' onclick='showUpdateModal("
                    + "`" + row.productId + "`"
                    + "," + "`" + row.productName.trim() + "`"
                    + "," + "`" + row.addressName.trim() + "`"
                    + "," + "`" + row.rate + "`"
                    + "," + "`" + row.restaurantName.trim() + "`"
                    + ")'>" +
                    "<i class='zmdi zmdi-edit zmdi-hc-fw'>" +
                    "</i>" +
                    "</button>";
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