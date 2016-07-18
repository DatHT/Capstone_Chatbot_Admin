<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="card" id="profile-main">
    <div class="pm-overview c-overflow">
        <div class="pmo-pic">
            <div class="p-relative">
                <img id="thumbImg" class="img-responsive" src="${productDetail.thumbPath}"
                     alt="<spring:message code="product_no_image"/>">
            </div>
        </div>
    </div>

    <div class="pm-body clearfix">
        <div class="pmb-block">
            <div class="pmbb-header">
                <h2><i class="zmdi zmdi-info m-r-5"></i> <spring:message code="product_header_details"/></h2>
            </div>
            <div class="pmbb-body p-l-30">
                <div class="pmbb-view">

                    <%--Name--%>
                    <dt><spring:message code="product_name"/></dt>
                    <dd>${productDetail.productName}</dd>
                    <br>

                    <%--Address--%>
                    <dt><spring:message code="product_address"/></dt>
                    <dd>${productDetail.addressName}</dd>
                    <br>

                    <%--District--%>
                    <dt><spring:message code="product_district"/></dt>
                    <dd>${productDetail.districtName}</dd>
                    <br>

                    <%--Rating--%>
                    <dt><spring:message code="product_rating"/></dt>
                    <dd>${productDetail.rate}</dd>
                    <br>

                    <%--Restaurant--%>
                    <dt><spring:message code="product_restaurant"/></dt>
                    <dd>${productDetail.restaurantName}</dd>
                    <br>

                    <%--Url--%>
                    <dt><spring:message code="product_url"/></dt>
                    <dd>${productDetail.urlRelate}</dd>
                    <br>
                </div>

                <div class="pmbb-edit">

                    <%--Edit Name--%>
                    <dt class="p-t-10"><spring:message code="product_name"/><span class="red-text">(*)</span></dt>
                    <dd>
                        <div class="fg-line">
                            <input type="text" class="form-control" value="${productDetail.productName}">
                        </div>
                    </dd>
                    <small id="error-name" class="help-block"></small>
                    <br>

                    <%--Edit Address--%>
                    <dt><spring:message code="product_address"/><span class="red-text">(*)</span></dt>
                    <dd>
                        <div class="fg-line">
                            <input type="text" class="form-control" value="${productDetail.addressName}">
                        </div>
                    </dd>
                    <small id="error-address" class="help-block"></small>
                    <br>

                    <%--Edit District--%>
                    <dt><spring:message code="product_district"/><span class="red-text">(*)</span></dt>
                    <dd>
                        <div class="fg-line">
                            <select id="district"
                                    name="district" class="form-control">
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
                        <div class="fg-line">
                            <input type="text" class="form-control" value="${productDetail.rate}">
                        </div>
                    </dd>
                    <br>

                    <%--Edit Restaurant--%>
                    <dt><spring:message code="product_restaurant"/></dt>
                    <dd>
                        <div class="fg-line">
                            <input type="text" class="form-control" value="${productDetail.restaurantName}">
                        </div>
                    </dd>
                    <br>

                    <%--Edit Url--%>
                    <dt><spring:message code="product_url"/></dt>
                    <dd>
                        <div class="fg-line">
                            <input type="text" class="form-control" value="${productDetail.urlRelate}">
                        </div>
                    </dd>
                    <br>

                    <div class="m-t-30">
                        <button class="btn btn-success">Save</button>
                        <button data-pmb-action="reset" class="btn btn-danger">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

