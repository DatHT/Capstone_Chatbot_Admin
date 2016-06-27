<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="true"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- Vendor CSS -->
<link href="resources/assets/vendors/bower_components/fullcalendar/dist/fullcalendar.min.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/animate.css/animate.min.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet">        
<link href="resources/assets/vendors/bower_components/google-material-color/dist/palette.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/bootstrap-select/dist/css/bootstrap-select.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/nouislider/distribute/jquery.nouislider.min.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="resources/assets/vendors/farbtastic/farbtastic.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/chosen/chosen.min.css" rel="stylesheet">
<link href="resources/assets/vendors/summernote/dist/summernote.css" rel="stylesheet">
<link href="resources/assets/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css" rel="stylesheet">
<link href="resources/assets/vendors/bootgrid/jquery.bootgrid.min.css" rel="stylesheet">


<!-- CSS -->
<link href="resources/assets/css/app.min.1.css" rel="stylesheet">
<link href="resources/assets/css/app.min.2.css" rel="stylesheet">

<!-- Core Scripts - Include with every page -->
	<!-- Javascript Libraries -->
        <script src="resources/assets/vendors/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="resources/assets/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="resources/assets/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="resources/assets/vendors/bower_components/Waves/dist/waves.min.js"></script>
        <script src="resources/assets/vendors/bootstrap-growl/bootstrap-growl.min.js"></script>
        <script src="resources/assets/vendors/bower_components/moment/min/moment.min.js"></script>
        <script src="resources/assets/vendors/bower_components/bootstrap-select/dist/js/bootstrap-select.js"></script>
        <script src="resources/assets/vendors/bower_components/nouislider/distribute/jquery.nouislider.all.min.js"></script>
        <script src="resources/assets/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
        <script src="resources/assets/vendors/bower_components/typeahead.js/dist/typeahead.bundle.min.js"></script>
        <script src="resources/assets/vendors/summernote/dist/summernote-updated.min.js"></script>
        <script src="resources/assets/vendors/bootgrid/jquery.bootgrid.updated.min.js"></script>
        <!-- Placeholder for IE9 -->
        <!--[if IE 9 ]>
            <script src="vendors/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
        <![endif]-->
	<script
		src="resources/assets/vendors/bower_components/chosen/chosen.jquery.min.js"></script>
	<script src="resources/assets/vendors/fileinput/fileinput.min.js"></script>
	<script src="resources/assets/vendors/input-mask/input-mask.min.js"></script>
	<script src="resources/assets/vendors/farbtastic/farbtastic.min.js"></script>

	<script
		src="resources/assets/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js"></script>
	<script
		src="resources/assets/vendors/bower_components/autosize/dist/autosize.min.js"></script>

	<script src="resources/assets/js/functions.js"></script>
	<script src="resources/assets/js/actions.js"></script>
	<script src="resources/assets/js/demo.js"></script>
</head>

<sec:authorize access="isAuthenticated()">
	<sec:authentication var="principal" property="principal" />
</sec:authorize>

<body data-ma-header="teal">
	<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
		prefix="tilesx"%>
	<tilesx:useAttribute name="current" />

	<!-- Header -->
	<tiles:insertAttribute name="header" />
	<!-- End Header -->
	<!--  wrapper -->
	<section id="main">

		<!-- nvbar slide left -->



		<!-- navbar side -->

		<aside id="s-main-menu" class="sidebar">
			<div class="smm-header">
				<i class="zmdi zmdi-long-arrow-left" data-ma-action="sidebar-close"></i>
			</div>



			<ul class="main-menu">

				<c:if test="${principal.authorities == '[ADMIN]'}">
					<li class="${current == 'dataConfig' ? 'selected' : ''}"><a
						href="dataConfig"><i class="zmdi zmdi-home"></i> Data Config</a></li>
				</c:if>
				<c:if test="${principal.authorities == '[ADMIN]'}">
					<li class="${current == 'manageAccount' ? 'selected' : ''}"><a
						href="manageAccount"><i class="zmdi zmdi-assignment-account"></i> Manage Account</a></li>
				</c:if>

				<li class="sub-menu"><a href="#"
					data-ma-action="submenu-toggle"><i
						class="zmdi zmdi-view-compact"></i> Training Bot</a>

					<ul>
						<li class="${current == 'lexicalCategory' ? 'selected' : ''}">
							<a href="lexical">Lexical Category</a>
						</li>
						<li class="${current == 'example' ? 'selected' : ''}"><a
							href="example">Example</a></li>
					</ul></li>

				<li class="${current == 'log' ? 'selected' : ''}"><a
					href="manageLog"> <i class="zmdi zmdi-format-underlined"></i>
						Manage Log
				</a></li>

				<li class="sub-menu"><a href="#"
					data-ma-action="submenu-toggle"><i
						class="zmdi zmdi-collection-text"></i> Manage Information</a>

					<ul>
						<li class="${current == 'product' ? 'selected' : ''}"><a
							href="product">Product</a></li>
						<li class="${current == 'synonym' ? 'selected' : ''}"><a
							href="synonym">Synonym</a></li>

					</ul></li>

			</ul>
		</aside>



		<!-- end navbar side -->



		<!--  page-wrapper -->
		<section id="content">
			<div class="container">

				<tiles:insertAttribute name="body" />
			</div>
		</section>
		<!-- end page-wrapper -->

		<!-- footer -->
		<tiles:insertAttribute name="footer" />

	</section>
	<!-- end section main -->



	<!-- Page Loader -->
	<div class="page-loader palette-Teal bg">
		<div class="preloader pl-xl pls-white">
			<svg class="pl-circular" viewBox="25 25 50 50">
                 <circle class="plc-path" cx="50" cy="50" r="20" />
             </svg>
		</div>
	</div>
</body>
</html>