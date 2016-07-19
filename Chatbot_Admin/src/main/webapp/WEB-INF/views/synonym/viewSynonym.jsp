<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link href="${pageContext.request.contextPath}/resources/assets/css/synonym.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script>
    var tokenName = '${_csrf.parameterName}';
    var tokenValue = '${_csrf.token}';
</script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/viewSynonym.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="c-header">
    <h2 id="tableHeader"><spring:message code="synonym_header_manage"/></h2>
</div>

<%--Origin--%>
<div id="div-origin" class="row">
    <div class="col-sm-8">
        <div class="card">
            <div class="card-header">
                <h2><spring:message code="synonym_header_origin"/></h2>
            </div>

            <div class="card-body card-padding">
                <a class="btn btn-primary waves-effect"
                   onclick="showFormOrigin('<spring:message code="synonym_header_add_origin"/>',0,0,'')">
                    <spring:message code="btn_add"/>
                </a>
                <div class="row m-t-20">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table id="data-table-origin" class="table table-condensed table-striped">
                                    <thead>
                                    <tr>
                                        <th data-column-id="number" data-type="numeric" data-identifier="true"
                                            data-sortable="false">
                                            <spring:message code="synonym_no"/>
                                        </th>
                                        <th data-column-id="name">
                                            <spring:message code="synonym_word"/>
                                        </th>
                                        <th data-column-id="synonym" data-formatter="commandsSynonym"
                                            data-sortable="false"
                                            data-align="center" data-header-align="center">
                                            <spring:message code="btn_synonyms"/>
                                        </th>
                                        <th data-column-id="update" data-formatter="commandsUpdate"
                                            data-sortable="false"
                                            data-align="center" data-header-align="center">
                                            <spring:message code="btn_update"/>
                                        </th>
                                        <th data-column-id="delete" data-formatter="commandsDelete"
                                            data-sortable="false"
                                            data-align="center" data-header-align="center">
                                            <spring:message code="btn_delete"/>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-4">
        <%--Add Origin--%>
        <div id="divAddOrigin" class="card">
            <div class="card-header">
                <h2 id="originTitle"></h2>
            </div>
            <div class="card-body card-padding">
                <dt class="p-t-10">
                    <spring:message code="synonym_word"/>
                </dt>
                <dd class="p-t-10">
                    <div id="div-origin-name">
                        <div class="fg-line">
                            <input id="txtOriginName" type="text" class="form-control">
                        </div>
                        <small id="error-origin" class="help-block"></small>
                    </div>
                </dd>
                <div class="m-t-30">
                    <button type="button" id="btnAddOrigin" class="btn btn-success" onclick="addUpdateOrigin()">
                        <spring:message code="btn_save"/>
                    </button>
                    <button class="btn btn-danger" onclick="hideFormOrigin()">
                        <spring:message code="btn_cancel"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Synonyms--%>
<div id="div-synonyms" class="row">
    <div class="col-sm-8">
        <div class="card">
            <div class="card-header">
                <h2 id="synonymHeader"></h2>
            </div>

            <div class="card-body card-padding">
                <a class="btn btn-primary waves-effect"
                   onclick="showFormSynonym('<spring:message code="synonym_header_add_synonym"/>',0,0,'')">
                    <spring:message code="btn_add"/>
                </a>
                <div class="row m-t-20">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table id="data-table-synonym" class="table table-condensed table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th data-column-id="number" data-type="numeric" data-identifier="true"
                                            data-sortable="false">
                                            <spring:message code="synonym_no"/>
                                        </th>
                                        <th data-column-id="name">
                                            <spring:message code="synonym_word"/>
                                        </th>
                                        <th data-column-id="update" data-formatter="commandsUpdate"
                                            data-sortable="false"
                                            data-align="center" data-header-align="center">
                                            <spring:message code="btn_update"/>
                                        </th>
                                        <th data-column-id="delete" data-formatter="commandsDelete"
                                            data-sortable="false"
                                            data-align="center" data-header-align="center">
                                            <spring:message code="btn_delete"/>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-4">
        <%--Add Synonym--%>
        <div class="card" id="divAddSynonyms">
            <div class="card-header">
                <h2 id="synonymTitle"></h2>
            </div>
            <div class="card-body card-padding">
                <dt class="p-t-10">
                    <spring:message code="synonym_word"/>
                </dt>
                <dd class="p-t-10">
                    <div id="div-synonym-name">
                        <div class="fg-line">
                            <input id="txtSynonymName" type="text" class="form-control">
                        </div>
                        <small id="error-synonym" class="help-block"></small>
                    </div>
                </dd>
                <div class="m-t-30">
                    <button type="button" class="btn btn-success" onclick="addUpdateSynonym()">
                        <spring:message code="btn_save"/>
                    </button>
                    <button class="btn btn-danger" onclick="hideFormSynonym()">
                        <spring:message code="btn_cancel"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Modals-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title"><spring:message code="text_delete_word"/></h4>
            </div>
            <div class="modal-body" style="border-bottom: 0px">
                <div>
                    <h4><spring:message code="text_confirm_delete"/></h4>
                </div>
            </div>
            <div class="modal-footer">
                <form action="deleteWord" method="POST">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">
                        <spring:message code="btn_cancel"/>
                    </button>

                    <button type="button" class="btn btn-success" onclick="deleteWord()">
                        <spring:message code="btn_delete"/>
                    </button>
                    <input id="deleteWordId" name="deleteWordId" type="hidden"/>
                    <input id="wordType" name="wordType" type="hidden"/>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- End modal -->

<input id="originUpdateId" type="hidden"/>
<input id="synonymUpdateId" type="hidden"/>