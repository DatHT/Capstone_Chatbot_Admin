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
<div class="row">
    <div class="col-sm-8">
        <div class="card">
            <div class="card-header">
                <h2><spring:message code="synonym_header_origin"/></h2>
            </div>

            <div class="card-body card-padding">
                <div class="row m-t-20">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table id="data-table-origin" class="table table-condensed table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th data-column-id="number" data-type="numeric" data-identifier="true"
                                            data-sortable="false">
                                            <spring:message code="synonym_no"/>
                                        </th>
                                        <th data-column-id="name">
                                            <spring:message code="synonym_word"/>
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
        <div id="divAddOrigin" class="card">
            <div class="card-header">
                <h2><spring:message code="synonym_header_add_origin"/></h2>
            </div>
            <div class="card-body card-padding">
                <dt class="p-t-10">
                    Word
                </dt>
                <dd class="p-t-10">
                    <div class="fg-line">
                        <input type="text" class="form-control">
                    </div>
                </dd>
                <div class="m-t-30">
                    <button type="submit" class="btn btn-success">
                        <spring:message code="btn_save"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Synonyms--%>
<div class="row">
    <div class="col-sm-8">
        <div class="card">
            <div class="card-header">
                <h2><spring:message code="synonym_header_synonyms"/></h2>
            </div>

            <div class="card-body card-padding">
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
        <div class="card" id="divAddSynonyms">
            <div class="card-header">
                <h2><spring:message code="synonym_header_add_synonym"/></h2>
            </div>
            <div class="card-body card-padding">
                <dt class="p-t-10">
                    Word
                </dt>
                <dd class="p-t-10">
                    <div class="fg-line">
                        <input type="text" class="form-control">
                    </div>
                </dd>
                <div class="m-t-30">
                    <button type="submit" class="btn btn-success">
                        <spring:message code="btn_save"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>