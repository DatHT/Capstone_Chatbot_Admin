<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script>
    var tokenName = '${_csrf.parameterName}';
    var tokenValue = '${_csrf.token}';
    var addResult = '${addResult}';
</script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/viewProduct.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="c-header">
    <h2 id="tableHeader"><spring:message code="product_header_manage"/></h2>
</div>

<div class="card">
    <div class="card-body card-padding">
        <a class="btn btn-primary btn-lg waves-effect" href="viewAddProduct">
            <spring:message code="product_btn_add"/>
        </a>
        <div class="row m-t-20">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="table-responsive">
                        <table id="data-table-basic" class="table table-condensed table-hover table-striped">
                            <thead>
                            <tr>
                                <th data-column-id="number" data-type="numeric" data-identifier="true"
                                    data-sortable="false">
                                    <spring:message code="product_no"/>
                                </th>
                                <th data-column-id="productName">
                                    <spring:message code="product_name"/>
                                </th>
                                <th data-column-id="addressName">
                                    <spring:message code="product_address"/>
                                </th>
                                <th data-column-id="rate">
                                    <spring:message code="product_rating"/>
                                </th>
                                <th data-column-id="restaurantName">
                                    <spring:message code="product_restaurant"/>
                                </th>
                                <th data-column-id="details" data-formatter="commandsDetails" data-sortable="false"
                                    data-align="center" data-header-align="center">
                                    <spring:message code="product_btn_details"/>
                                </th>
                                <th data-column-id="update" data-formatter="commandsUpdate" data-sortable="false"
                                    data-align="center" data-header-align="center">
                                    <spring:message code="product_btn_update"/>
                                </th>
                                <th data-column-id="delete" data-formatter="commandsDelete" data-sortable="false"
                                    data-align="center" data-header-align="center">
                                    <spring:message code="product_btn_delete"/>
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

<!-- Modals-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete Product</h4>
            </div>
            <div class="modal-body" style="border-bottom: 0px">
                <div>
                    <h4>Are you sure?</h4>
                </div>
            </div>
            <div class="modal-footer">
                <form action="deleteProduct" method="POST">
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Cancel
                    </button>

                    <button type="submit" class="btn btn-success"
                            onclick="">
                        Delete
                    </button>
                    <input id="deleteProductId" name="deleteProductId" type="hidden"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- End modal -->