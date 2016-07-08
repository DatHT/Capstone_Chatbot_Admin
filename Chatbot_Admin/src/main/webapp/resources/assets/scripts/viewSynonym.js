$(document).ready(function () {
    // Table Origin
    $("#data-table-origin").bootgrid({
        ajax: true,
        post: function () {
            /* To accumulate custom parameter with the request object */
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
            };
        },
        url: "loadOrigin?" + tokenName + "=" + tokenValue,
        ss: {
            icon: 'zmdi icon',
            iconColumns: 'zmdi-view-module',
            iconDown: 'zmdi-expand-more',
            iconRefresh: 'zmdi-refresh',
            iconUp: 'zmdi-expand-less'
        },
    });

    $("#data-table-origin-footer .infoBar").removeClass("col-sm-6");
    $("#data-table-origin-footer .infoBar").addClass("col-sm-2");
    $("#data-table-origin-footer .pagination").parent().removeClass("col-sm-6");
    $("#data-table-origin-footer .pagination").parent().addClass("col-sm-10");

    // Table Synonym
    $("#data-table-synonym").bootgrid({
        ajax: true,
        post: function () {
            /* To accumulate custom parameter with the request object */
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
            };
        },
        url: "loadSynonyms?" + tokenName + "=" + tokenValue,
        ss: {
            icon: 'zmdi icon',
            iconColumns: 'zmdi-view-module',
            iconDown: 'zmdi-expand-more',
            iconRefresh: 'zmdi-refresh',
            iconUp: 'zmdi-expand-less'
        },
    });

    $("#data-table-synonym-footer .infoBar").removeClass("col-sm-6");
    $("#data-table-synonym-footer .infoBar").addClass("col-sm-2");
    $("#data-table-synonym-footer .pagination").parent().removeClass("col-sm-6");
    $("#data-table-synonym-footer .pagination").parent().addClass("col-sm-10");


});

function realoadTable(tableId) {
    $(tableId).bootgrid('reload');
}

function scrolltoSynonyms() {
    $("#data-table-synonym-footer")[0].scrollIntoView();
}