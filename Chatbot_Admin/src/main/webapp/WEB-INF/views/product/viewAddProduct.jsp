<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link href="${pageContext.request.contextPath}/resources/assets/css/product.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/product.js"></script>
<script>
    var addResult = '${addResult}';
</script>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/addProduct.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="c-header">
    <h2 id="tableHeader"><spring:message code="product_header_manage"/></h2>
</div>

<div class="card" id="profile-main">
    <div class="pm-overview c-overflow">
        <div class="pmo-pic">
            <div class="p-relative">
                <img id="thumbImg" class="img-responsive" src="" alt="<spring:message code="product_no_image_upload"/>">
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
                <h2><i class="zmdi zmdi-bookmark m-r-5"></i> <spring:message code="product_header_add"/></h2>
            </div>
            <div class="pmbb-body p-l-30">
                <div class="pmbb-view">
                    <form id="update-form" action="addProduct" method="POST"
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

                        <c:if test="${addResult != 'false'}">
                            <%--Name--%>
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

                            <%--Address--%>
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
                                               value="">
                                    </div>
                                    <small id="error-address" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--District--%>
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

                            <%--Rating--%>
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
                                               value="">
                                    </div>
                                    <small id="error-rating" class="help-block"></small>
                                </div>
                            </dd>
                            <br>

                            <%--Restaurant--%>
                            <dt><spring:message code="product_restaurant"/></dt>
                            <dd>
                                <div class="fg-line">
                                    <input id="restaurant" name="restaurant" type="text" class="form-control"
                                           autocomplete="off"
                                           value="">
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
                                               value="">
                                    </div>
                                    <small id="error-relatedUrl" class="help-block"></small>
                                </div>
                            </dd>
                        </c:if>

                        <c:if test="${addResult == 'false'}">
                            <%--Name--%>
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

                            <%--Address--%>
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

                            <%--District--%>
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

                            <%--Rating--%>
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

                            <%--Restaurant--%>
                            <dt><spring:message code="product_restaurant"/></dt>
                            <dd>
                                <div class="fg-line">
                                    <input id="restaurant" name="restaurant" type="text" class="form-control"
                                           autocomplete="off"
                                           value="${restaurant}">
                                </div>
                            </dd>
                            <br>

                            <%--Url--%>
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
                                <spring:message code="product_btn_save"/>
                            </button>
                            <a class="btn btn-danger" href="product">
                                <spring:message code="product_btn_cancel"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

