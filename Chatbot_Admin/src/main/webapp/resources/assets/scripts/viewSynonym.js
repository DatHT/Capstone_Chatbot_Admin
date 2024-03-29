var originId = "";
var isAddOrigin;
var isAddSynonym;

$(document).ready(function () {
    $("#div-synonyms").hide()
    $("#divAddOrigin").hide()
    $("#divAddSynonyms").hide()

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
                return "<a class='btn btn-success btn-icon waves-effect waves-circle waves-float' onclick='showFormOrigin("
                    + "`" + "Update Origin" + "`"
                    + ",1" + "," + row.id + "," + "`" + row.name + "`"
                    + ")'>"
                    + "<i class='zmdi zmdi-edit zmdi-hc-fw'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsDelete": function (column, row) {
                return "<button data-row-id='" + row.id + "' class='btn btn-danger btn-icon waves-effect waves-circle waves-float btn-delete' >" +
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
        $('#data-table-origin').find(".btn-delete").on("click", function(e) {
        	var wordId = $(this).data("row-id");
        	swal({
                title: "Are you sure?",
                text: "You will not be able to recover it!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                closeOnConfirm: true
            }, function(){
            	deleteWord(wordId, 0);
            });
        });
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
                return "<a id='" + "syn" + row.id + "' class='btn btn-success btn-icon waves-effect waves-circle waves-float' onclick='showFormSynonym("
                    + "`" + "Update Synonym" + "`"
                    + ",1" + "," + row.id + "," + "`" + row.name + "`"
                    + ")'>"
                    + "<i class='zmdi zmdi-edit zmdi-hc-fw'>"
                    + "</i>"
                    + "</a>";
            },
            "commandsDelete": function (column, row) {
                return "<button data-row-id='" + row.id + "' class='btn btn-danger btn-icon waves-effect waves-circle waves-float btn-delete-synonym'>" +
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
        $('#data-table-synonym').find(".btn-delete-synonym").on("click", function(e) {
        	var wordId = $(this).data("row-id");
        	swal({
                title: "Are you sure?",
                text: "You will not be able to recover it!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                closeOnConfirm: true
            }, function(){
            	deleteWord(wordId, 1);
            });
        });
    });

    $("#data-table-synonym-footer .infoBar").removeClass("col-sm-6");
    $("#data-table-synonym-footer .infoBar").addClass("col-sm-2");
    $("#data-table-synonym-footer .pagination").parent().removeClass("col-sm-6");
    $("#data-table-synonym-footer .pagination").parent().addClass("col-sm-10");
});

function showSynonyms(id, name) {
    $("#synonymHeader").text("Synonyms of " + name);
    $("#div-synonyms").show();
    originId = id;
    reloadTable("#data-table-synonym");
    scrollToSynonyms();
}

function showFormOrigin(title, type, id, wordName) {
    $("#originTitle").text(title);
    $("#div-origin-name").removeClass("has-error");
    $("#error-origin").text("");
    $("#divAddOrigin").show();
    if (type == 0) {
        isAddOrigin = 0;
        $('#txtOriginName').val("");
    } else if (type == 1) {
        isAddOrigin = 1;
        $("#originUpdateId").val(id);
        $('#txtOriginName').val(wordName);
    }
}

function showFormSynonym(title, type, id, wordName) {
    $("#synonymTitle").text(title);
    $("#div-synonym-name").removeClass("has-error");
    $("#error-synonym").text("");
    $("#divAddSynonyms").show();
    $("#divAddSynonyms").css("margin-top", "");
    if (type == 0) {
        isAddSynonym = 0;
        $('#txtSynonymName').val("");
    } else if (type == 1) {
        isAddSynonym = 1;
        $("#synonymUpdateId").val(id);
        $('#txtSynonymName').val(wordName);
        // var eleId = "#" + "syn" + id;
        // var viewableOffset = ($(eleId).parent().parent().parent().offset().top - $(window).scrollTop() + $("#divAddSynonyms").height() - $(eleId).parent().parent().parent().height()) / 2;
        // console.info(viewableOffset)
        // $("#divAddSynonyms").css("margin-top", viewableOffset);
    }
}

function hideFormOrigin() {
    $("#divAddOrigin").hide();
    $("#txtOriginName").val("");
    $("#div-origin-name").removeClass("has-error");
    $("#error-origin").text("");
}

function hideFormSynonym() {
    $("#divAddSynonyms").hide();
    $("#txtSynonymName").val("");
    $("#div-synonym-name").removeClass("has-error");
    $("#error-synonym").text("");
}

function reloadTable(tableId) {
    $(tableId).bootgrid('reload');
}

function scrollToSynonyms() {
    $("#data-table-synonym-footer")[0].scrollIntoView();
}

function showDeleteModal(id, type) {
    $('#deleteWordId').val(id);
    $('#wordType').val(type);
    $('#deleteModal').modal('show');
}

function addUpdateOrigin() {

    if (validName("#txtOriginName", "#div-origin-name", "#error-origin", "Please enter word")) {

        var wordName = $('#txtOriginName').val();

        if (isAddOrigin == 0) {
            var data = {
                "wordName": wordName,
            };

            $.ajax({
                type: "POST",
                data: data,
                url: "addOrigin?" + tokenName + "=" + tokenValue,
                success: function (data) {
                    if (data.result == true) {
                        $('#txtOriginName').val("");
                        notify("Add Successfully!", "info");
                        reloadTable("#data-table-origin");
                    } else {
                        notify("Word Already Existed!", "warning");
                    }
                }
            });
        } else if (isAddOrigin == 1) {

            var wordId = $("#originUpdateId").val();

            var data = {
                "wordId": wordId,
                "wordName": wordName,
            };

            $.ajax({
                type: "POST",
                data: data,
                url: "updateOrigin?" + tokenName + "=" + tokenValue,
                success: function (data) {
                    if (data.result == true) {
                        $('#txtOriginName').val("");
                        notify("Update Successfully!", "info");
                        reloadTable("#data-table-origin");
                        reloadTable("#data-table-synonym");
                    } else {
                        notify("Word Already Existed!", "warning");
                    }
                }
            });
        }
    }
}

function addUpdateSynonym() {

    if (validName("#txtSynonymName", "#div-synonym-name", "#error-synonym", "Please enter word")) {

        var wordName = $('#txtSynonymName').val();

        if (isAddSynonym == 0) {
            var data = {
                "wordName": wordName,
                "originId": originId,
            };

            $.ajax({
                type: "POST",
                data: data,
                url: "addSynonym?" + tokenName + "=" + tokenValue,
                success: function (data) {
                    if (data.result == true) {
                        $('#txtSynonymName').val("");
                        notify("Add Successfully!", "info");
                        reloadTable("#data-table-synonym");
                    } else {
                        notify("Word Already Existed!", "warning");
                    }
                }
            });
        } else if (isAddSynonym == 1) {

            var wordId = $("#synonymUpdateId").val();

            var data = {
                "wordId": wordId,
                "wordName": wordName,
                "originId": originId,
            };

            $.ajax({
                type: "POST",
                data: data,
                url: "updateSynonym?" + tokenName + "=" + tokenValue,
                success: function (data) {
                    if (data.result == true) {
                        $('#txtSynonymName').val("");
                        notify("Update Successfully!", "info");
                        reloadTable("#data-table-origin");
                        reloadTable("#data-table-synonym");
                    } else {
                        notify("Word Already Existed!", "warning");
                    }
                }
            });
        }
    }
}

function deleteWord(wordId, wordType) {
    var data = {
        "deleteWordId": wordId,
    };

    $.ajax({
        type: "POST",
        data: data,
        url: "deleteWord?" + tokenName + "=" + tokenValue,
        success: function (data) {
            $('#deleteModal').modal('hide');
            if (data.result == true) {
            	notify("It has been deleted!", "info");
            }
            if (wordType == 0) {
                reloadTable("#data-table-origin");
                $("#div-synonyms").hide();
            } else if (wordType == 1) {
                reloadTable("#data-table-synonym");
            }
        }
    });
}

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