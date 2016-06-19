<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="error" value="${ERROR}" />
<div class="four-zero">
    <div class="fz-inner">
        <h2>${error}</h2>
        <p>Sorry! System is now have problem!</p>
    </div>
</div>
