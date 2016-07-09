var originId = "";

$(document).ready(function () {
    $("#div-synonyms").hide()
    $("#divAddOrigin").hide()
    $("#divAddSynonyms").hide()

    // Table Origin
    var grid = $("#data-table-origin").bootgrid({
        ajax: true,
        post: function () {
            /* To accumulate custom parameter with the request object */
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
            };
        },
        url: "loadOrigin?" + tokenName + "=" + tokenValue,
        formatters: {
            "commandsSynonym": function (column, row) {
                return "<a class='btn btn-info btn-icon waves-effect waves-circle waves-float' onclick='showSynonyms("
                    + row.id
                    + ","
                    + "`" + row.name + "`"
                    + ")' "
                    + "'>"
                    + "<i class='zmdi zmdi-more-vert'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsUpdate": function (column, row) {
                return "<a class='btn btn-success btn-icon waves-effect waves-circle waves-float' "
                    + "href='viewUpdateProduct?productId="
                    + row.id
                    + "'>"
                    + "<i class='zmdi zmdi-edit zmdi-hc-fw'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsDelete": function (column, row) {
                return "<button class='btn btn-danger btn-icon waves-effect waves-circle waves-float' onclick='showDeleteModal("
                    + "`" + row.id + "`"
                    + ",0"
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
                originId: originId,
            };
        },
        url: "loadSynonyms?" + tokenName + "=" + tokenValue,
        formatters: {
            "commandsUpdate": function (column, row) {
                return "<a class='btn btn-success btn-icon waves-effect waves-circle waves-float' "
                    + "href='viewUpdateProduct?productId="
                    + row.id
                    + "'>"
                    + "<i class='zmdi zmdi-edit zmdi-hc-fw'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsDelete": function (column, row) {
                return "<button class='btn btn-danger btn-icon waves-effect waves-circle waves-float' onclick='showDeleteModal("
                    + "`" + row.id + "`"
                    + ",1"
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

    $("#data-table-synonym-footer .infoBar").removeClass("col-sm-6");
    $("#data-table-synonym-footer .infoBar").addClass("col-sm-2");
    $("#data-table-synonym-footer .pagination").parent().removeClass("col-sm-6");
    $("#data-table-synonym-footer .pagination").parent().addClass("col-sm-10");


});

function showSynonyms(id, name) {
    var header = $("#synonymHeader").text()
    $("#synonymHeader").text(header + " of " + name);
    $("#div-synonyms").show();
    originId = id;
    reloadTable("#data-table-synonym");
    scrolltoSynonyms();
}

function showFormOrigin(title) {
    $("#originTitle").text(title);
    $("#divAddOrigin").show();
}

function showFormSynonym(title) {
    $("#synonymTitle").text(title);
    $("#divAddSynonyms").show();
}

function hideFormOrigin() {
    $("#divAddOrigin").hide();
    $("#txtOriginName").val("");
}

function hideFormSynonym() {
    $("#divAddSynonyms").hide();
    $("#txtSynonymName").val("");
}

function reloadTable(tableId) {
    $(tableId).bootgrid('reload');
}

function scrolltoSynonyms() {
    $("#data-table-synonym-footer")[0].scrollIntoView();
}

function showDeleteModal(id, type) {
    $('#deleteWordId').val(id);
    $('#wordType').val(type);
    $('#deleteModal').modal('show');
}

function deleteWord() {
    var wordId = $('#deleteWordId').val();
    var data = {
        "deleteWordId": wordId,
    };

    $.ajax({
        type: "POST",
        data: data,
        async: false,
        url: "deleteWord?" + tokenName + "=" + tokenValue,
        success: function (result) {
            $('#deleteModal').modal('hide');
            if (result == 'true') {
                notify("Delete Successfully!", "info");
            }
            if ($('#wordType').val() == 0) {
                reloadTable("#data-table-origin");
                $("#div-synonyms").hide();
            } else if ($('#wordType').val() == 1) {
                reloadTable("#data-table-synonym");
            }
        }
    });
}