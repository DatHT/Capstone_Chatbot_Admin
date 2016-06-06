<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="error" value="${ERROR}" />
<div class="row">
	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">${error}</h1>
	</div>
	<!-- end  page header -->
</div>

