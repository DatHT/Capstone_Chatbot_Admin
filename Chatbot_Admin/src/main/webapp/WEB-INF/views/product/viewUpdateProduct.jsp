<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link href="${pageContext.request.contextPath}/resources/assets/css/product.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/product.js"></script>
<script>
    var updateResult = '${updateResult}';
</script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/updateProduct.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="card" id="profile-main">
    <div class="pm-overview c-overflow">
        <div class="pmo-pic">
            <div class="p-relative">
                <img id="thumbImg" class="img-responsive" src="${productDetail.thumbPath}"
                     alt="<spring:message code="product_no_image_upload"/>">
                <a id="uploadThumb" href="#" class="pmop-edit" onclick="performClick('file')">
                    <i class="zmdi zmdi-camera"></i> <span class="hidden-xs"><spring:message
                        code="product_upload_image"/></span>
                </a>
            </div>
        </div>
    </div>

    <div class="pm-body clearfix">
        <div class="pmb-block">
            <div class="pmbb-header">
                <h2><i class="zmdi zmdi-edit m-r-5"></i> <spring:message code="product_header_update"/></h2>
            </div>
            <div class="pmbb-body p-l-30">
                <div class="pmbb-view">
                    <form id="update-form" action="updateProduct" method="POST"
                          onsubmit="return validOnSubmit('#name','#div-name','#error-name'
                                  ,'#address','#div-address','#error-address'
                                  ,'#rating','#div-rating','#error-rating'
                                  ,'#relatedUrl','#div-relatedUrl','#error-relatedUrl',
                                  '<spring:message code='product_error_empty_name'/>',
                                  '<spring:message code='product_error_empty_address'/>',
                                  '<spring:message code='product_error_invalid_rating'/>',
                                  '<spring:message code='product_error_empty_url'/>',
                                  '<spring:message code='product_error_invalid_url'/>')"
                          enctype="multipart/form-data">

                        <input id="updateProductId" name="updateProductId" type="hidden"
                               value="${productDetail.productId}">

                        <c:if test="${updateResult != 'false'}">
                            <%--Edit Name--%>
                            <dt class="p-t-10"><spring:message code="product_name"/><span class="red-text">(*)</span>
                            </dt>
                            <dd>
                                <div id="div-name">
                                    <div class="fg-line">
                                        <input id="name" name="name" type="text" class="form-control" autocomplete="off"
                                               onblur="validName('#name','#div-name','#error-name',
                                                       '<spring:message code='product_error_empty_name'/>')"
                                               onkeyup="validName('#name','#div-name','#error-name',
                                                       '<spring:message code='product_error_empty_name'/>')"
                                               value="${productDetail.productName}">
                                    </div>
                                    <small id="error-name" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit Address--%>
                            <dt><spring:message code="product_address"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div id="div-address">
                                    <div class="fg-line">
                                        <input id="address" name="address" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validAddress('#address','#div-address','#error-address',
                                                       '<spring:message code='product_error_empty_address'/>')"
                                               onkeyup="validAddress('#address','#div-address','#error-address',
                                                       '<spring:message code='product_error_empty_address'/>')"
                                               value="${productDetail.addressName}">
                                    </div>
                                    <small id="error-address" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit District--%>
                            <dt><spring:message code="product_district"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div class="fg-line">
                                    <select id="district" name="district" class="form-control">
                                        <c:forEach items="${districtList}" var="district">
                                            <c:if test="${productDetail.districtName == district.name}">
                                                <option value="${district.name}" selected>${district.name}</option>
                                            </c:if>
                                            <c:if test="${productDetail.districtName != district.name}">
                                                <option value="${district.name}">${district.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </dd>
                            <br>

                            <%--Edit Rating--%>
                            <dt><spring:message code="product_rating"/></dt>
                            <dd>
                                <div id="div-rating">
                                    <div class="fg-line">
                                        <input id="rating" name="rating" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validRating('#rating','#div-rating','#error-rating',
                                                       '<spring:message code='product_error_invalid_rating'/>')"
                                               onkeyup="validRating('#rating','#div-rating','#error-rating',
                                                       '<spring:message code='product_error_invalid_rating'/>')"
                                               value="${productDetail.rate}">
                                    </div>
                                    <small id="error-rating" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit Restaurant--%>
                            <dt><spring:message code="product_restaurant"/></dt>
                            <dd>
                                <div class="fg-line">
                                    <input id="restaurant" name="restaurant" type="text" class="form-control"
                                           autocomplete="off"
                                           value="${productDetail.restaurantName}">
                                </div>
                            </dd>
                            <br>

                            <%--Edit Url--%>
                            <dt><spring:message code="product_url"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div id="div-relatedUrl">
                                    <div class="fg-line">
                                        <input id="relatedUrl" name="relatedUrl" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl',
                                                       '<spring:message code='product_error_empty_url'/>',
                                                       '<spring:message code='product_error_invalid_url'/>')"
                                               onkeyup="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl',
                                                       '<spring:message code='product_error_empty_url'/>',
                                                       '<spring:message code='product_error_invalid_url'/>')"
                                               value="${productDetail.urlRelate}">
                                    </div>
                                    <small id="error-relatedUrl" class="help-block"></small>
                                </div>
                            </dd>
                        </c:if>

                        <c:if test="${updateResult == 'false'}">
                            <%--Edit Name--%>
                            <dt class="p-t-10"><spring:message code="product_name"/><span class="red-text">(*)</span>
                            </dt>
                            <dd>
                                <div id="div-name">
                                    <div class="fg-line">
                                        <input id="name" name="name" type="text" class="form-control" autocomplete="off"
                                               onblur="validName('#name','#div-name','#error-name',
                                                       '<spring:message code='product_error_empty_name'/>')"
                                               onkeyup="validName('#name','#div-name','#error-name',
                                                       '<spring:message code='product_error_empty_name'/>')"
                                               value="${name}">
                                    </div>
                                    <small id="error-name" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit Address--%>
                            <dt><spring:message code="product_address"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div id="div-address">
                                    <div class="fg-line">
                                        <input id="address" name="address" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validAddress('#address','#div-address','#error-address',
                                                       '<spring:message code='product_error_empty_address'/>')"
                                               onkeyup="validAddress('#address','#div-address','#error-address',
                                                       '<spring:message code='product_error_empty_address'/>')"
                                               value="${address}">
                                    </div>
                                    <small id="error-address" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit District--%>
                            <dt><spring:message code="product_district"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div class="fg-line">
                                    <select id="district" name="district" class="form-control">
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
                            </dd>
                            <br>

                            <%--Edit Rating--%>
                            <dt><spring:message code="product_rating"/></dt>
                            <dd>
                                <div id="div-rating">
                                    <div class="fg-line">
                                        <input id="rating" name="rating" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validRating('#rating','#div-rating','#error-rating',
                                                       '<spring:message code='product_error_invalid_rating'/>')"
                                               onkeyup="validRating('#rating','#div-rating','#error-rating',
                                                       '<spring:message code='product_error_invalid_rating'/>')"
                                               value="${rating}">
                                    </div>
                                    <small id="error-rating" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Edit Restaurant--%>
                            <dt><spring:message code="product_restaurant"/></dt>
                            <dd>
                                <div class="fg-line">
                                    <input id="restaurant" name="restaurant" type="text" class="form-control"
                                           autocomplete="off"
                                           value="${restaurant}">
                                </div>
                            </dd>
                            <br>

                            <%--Edit Url--%>
                            <dt><spring:message code="product_url"/><span class="red-text">(*)</span></dt>
                            <dd>
                                <div id="div-relatedUrl">
                                    <div class="fg-line">
                                        <input id="relatedUrl" name="relatedUrl" type="text" class="form-control"
                                               autocomplete="off"
                                               onblur="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl',
                                                       '<spring:message code='product_error_empty_url'/>',
                                                       '<spring:message code='product_error_invalid_url'/>')"
                                               onkeyup="validRelatedUrl('#relatedUrl','#div-relatedUrl','#error-relatedUrl',
                                                       '<spring:message code='product_error_empty_url'/>',
                                                       '<spring:message code='product_error_invalid_url'/>')"
                                               value="${relatedUrl}">
                                    </div>
                                    <small id="error-relatedUrl" class="help-block"></small>
                                </div>
                            </dd>
                        </c:if>


                        <input type="file" id="file" name="file" style="visibility: hidden;" accept="image/*"
                               onchange="previewFile()"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="m-t-30">
                            <button type="submit" class="btn btn-success">
                                <spring:message code="btn_save"/>
                            </button>
                            <a class="btn btn-danger" href="product">
                                <spring:message code="btn_cancel"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

