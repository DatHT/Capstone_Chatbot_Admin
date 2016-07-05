<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script>
    var tokenName = '${_csrf.parameterName}';
    var tokenValue = '${_csrf.token}';
</script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/viewProduct.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <spring:message code="product_header"/>
</head>

<div class="c-header">
    <h2 id="tableHeader"><spring:message code="product_header"/></h2>
</div>

<div class="card">
    <div class="card-body card-padding">
        <button class="btn btn-primary btn-lg waves-effect" onclick="showAddModal()">
            <spring:message code="product_btn_add"/>
        </button>
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

<!-- Data Table -->
<script type="text/javascript">

</script>

<!-- Modals-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div id="user-say-container" class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="user-say-in-modal">Add New Product</h4>
            </div>
            <form id="add-form" action="addProduct" method="POST"
                  enctype="multipart/form-data">

                <input id="updateProductId" name="updateProductId" type="hidden">

                <div class="modal-body" style="border-bottom: 0px">
                    <div>


                        <%--Name--%>
                        <div id="div-name">
                            <div class="fg-line">
                                <label>Name</label>
                                <input id="name" name="name" value="${name}"
                                       autocomplete="off" class="form-control"
                                       onblur="validName('#name','#div-name','#error-name')"
                                       onkeyup="validName('#name','#div-name','#error-name')">
                            </div>
                            <small id="error-name" class="help-block"></small>
                        </div>

                        <%--Address--%>
                        <div id="div-address">
                            <label>Address</label>
                            <div class="fg-line">
                                <input id="address" name="address" value="${address}"
                                       autocomplete="off" class="form-control"
                                       onblur="validAddress('#address','#div-address','#error-address')"
                                       onkeyup="validAddress('#address','#div-address','#error-address')">
                            </div>
                            <small id="error-address" class="help-block"></small>
                        </div>

                        <%--District--%>
                        <div id="div-district">
                            <label>District</label><br>
                            <div class="fg-line">
                                <select id="district"
                                        name="district" class="form-control">
                                    <c:forEach items="${districtList}" var="district">
                                        <c:if test="${districtName == district.name}">
                                            <option value="${district.name}" selected>${district.name}</option>
                                        </c:if>
                                        <c:if test="${districtName != district.name}">
                                            <option value="${district.name}">${district.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <%--Rating--%>
                        <div id="div-rating">
                            <label>Rating</label>
                            <div class="fg-line">
                                <input id="rating" name="rating" value="${rating}"
                                       autocomplete="off" class="form-control"
                                       onblur="validRating('#rating','#div-rating','#error-rating')"
                                       onkeyup="validRating('#rating','#div-rating','#error-rating')">
                            </div>
                            <small id="error-rating" class="help-block"></small>
                        </div>

                        <%--Restaurant--%>
                        <div id="div-restaurant">
                            <label>Restaurant</label>
                            <div class="fg-line">
                                <input id="restaurant" name="restaurant" value="${restaurant}"
                                       autocomplete="off" class="form-control"
                                       onblur="validRestaurant('#restaurant','#div-restaurant','#error-restaurant')"
                                       onkeyup="validRestaurant('#restaurant','#div-restaurant','#error-restaurant')">
                            </div>
                            <small id="error-restaurant" class="help-block"></small>
                        </div>

                        <%--Related Url--%>
                        <div id="div-relatedUrl">
                            <label>Related Url</label>
                            <div class="fg-line">
                                <input id="relatedUrl" name="relatedUrl" value="${relatedUrl}"
                                       autocomplete="off" class="form-control"
                                       onblur="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl')"
                                       onkeyup="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl')">
                            </div>
                            <small id="error-relatedUrl" class="help-block"></small>
                        </div>

                        <%--Thumbnail--%>
                        <label>Thumbnail</label><br>
                        <input id="file" type="file" name="file">

                        <br>
                    </div>
                </div>

                <div class="modal-footer">
                    <button id="cancel-button" type="button" class="btn btn-danger"
                            data-dismiss="modal">Cancel
                    </button>
                    <button id="add-button" type="button" class="btn btn-success"
                            onclick="validOnSubmit('#add-form','#name','#div-name','#error-name'
                            ,'#address','#div-address','#error-address'
                            ,'#rating','#div-rating','#error-rating'
                            ,'#restaurant','#div-restaurant','#error-restaurant'
                            ,'#relatedUrl','#div-relatedUrl','#error-relatedUrl')">
                        Submit
                    </button>
                </div>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
<!-- End modal -->

<input id="tmpName" type="hidden">
<input id="tmpAddress" type="hidden">
<input id="tmpRelatedUrl" type="hidden">
<input id="tmpRating" type="hidden">
<input id="tmpRestaurant" type="hidden">
<input id="tmpDistrict" type="hidden">


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