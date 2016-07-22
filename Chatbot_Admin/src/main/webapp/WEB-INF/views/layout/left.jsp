<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication var="principal" property="principal"/>
</sec:authorize>

<div id="s-main-menu" class="sidebar">

    <ul class="main-menu menu-fixed">

        <c:if test="${principal.authorities == '[ADMIN]'}">
            <li class="${cur == 'dataConfig' ? 'check-active' : ''}">
                <a href="config">
                    <i class="zmdi zmdi-home"></i>
                    Data Config
                </a>
            </li>
        </c:if>

        <c:if test="${principal.authorities == '[ADMIN]'}">
            <li class="${cur == 'manageAccount' ? 'check-active' : ''}">
                <a href="manageAccount">
                    <i class="zmdi zmdi-assignment-account"></i>
                    Manage Account
                </a>
            </li>
        </c:if>

        <li class="sub-menu ${(cur == 'lexicalCategory') || (cur == 'example') ? 'check-active' : ''}">
            <a href="#" data-ma-action="submenu-toggle">
                <i class="zmdi zmdi-view-compact"></i>
                Training Bot
            </a>
            <ul style="display:${(cur == 'lexicalCategory') || (cur == 'example') ? 'block' : 'none'}">
                <li class="${cur == 'lexicalCategory' ? 'check-active' : ''}">
                    <a href="lexical">Lexical Category</a>
                </li>
                <li class="${cur == 'example' ? 'check-active' : ''}">
                    <a href="example">Example</a>
                </li>
            </ul>
        </li>

        <li class="${cur == 'log' ? 'check-active' : ''}">
            <a href="manageLog">
                <i class="zmdi zmdi-format-underlined"></i>
                Manage Log
            </a>
        </li>
		<li class="${current == 'crawler' ? 'check-active' : ''}">
					<a href="configuration"> <i class="zmdi zmdi-cloud-download"></i> Crawler Manager</a>
				</li>
        <li class="sub-menu ${(cur == 'product') || (cur == 'synonym') ? 'check-active' : ''}">
            <a href="#" data-ma-action="submenu-toggle">
                <i class="zmdi zmdi-collection-text"></i>
                Manage Information
            </a>
            <ul style="display:${(cur == 'product') || (cur == 'synonym') ? 'block' : 'none'}">
                <li class="${cur == 'product' ? 'check-active' : ''}">
                    <a href="product">Product</a>
                </li>
                <li class="${cur == 'synonym' ? 'check-active' : ''}">
                    <a href="synonym">Synonym</a>
                </li>
            </ul>
        </li>
        <%-- <li class="${cur == 'conversations' ? 'check-active' : ''}">
            <a href="conversations">
                <i class="zmdi zmdi-comments"></i>
                Conversations
            </a>
        </li> --%>
    </ul>
</div>

