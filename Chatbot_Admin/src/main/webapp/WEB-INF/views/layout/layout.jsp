<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>

<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<!-- Core CSS - Include with every page -->
<link href="resources/assets/plugins/bootstrap/bootstrap.css"
	rel="stylesheet" />
<link href="resources/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link href="resources/assets/plugins/pace/pace-theme-big-counter.css"
	rel="stylesheet" />
<link href="resources/assets/css/style.css" rel="stylesheet" />
<link href="resources/assets/css/main-style.css" rel="stylesheet" />

</head>

<body>
	<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
		prefix="tilesx"%>
	<tilesx:useAttribute name="current" />
	<!--  wrapper -->
	<div id="wrapper">
		<!-- Header -->
		<tiles:insertAttribute name="header" />

		<!-- nvbar slide left -->


		
		<!-- navbar side -->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<!-- sidebar-collapse -->
		<div class="sidebar-collapse">
			<!-- side-menu -->
			<ul class="nav" id="side-menu">
				<li class="nav-inner">
					<!-- user image section-->
					<div class="user-section">
						<div class="user-section-inner">
							<img src="resources/assets/img/user.jpg" alt="">
						</div>
						<div class="user-info">
							<div>
								Huynh <strong>Dat</strong>
							</div>
							<div class="user-text-online">
								<span class="user-circle-online btn btn-success btn-circle "></span>&nbsp;Online
							</div>
						</div>
					</div> <!--end user image section-->
				</li>

				<li class="${current == 'dataConfig' ? 'selected' : ''}"><a
					href="dataConfig"><i class="fa fa-dashboard fa-fw"></i>Data
						Config</a></li>
				<li><a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>Training
						Bot<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li class="${current == 'lexicalCategory' ? 'selected' : ''}">
							<a href="lexical">Lexical Category</a>
						</li>
						<li class="${current == 'example' ? 'selected' : ''}">
						<a href="example">Example</a></li>
					</ul> <!-- second-level-items --></li>
				<li class="${current == 'log' ? 'selected' : ''}">
				<a href="manageLog"><i class="fa fa-flask fa-fw"></i>Manage
						log</a></li>


				<li><a href="#"><i class="fa fa-files-o fa-fw"></i>Manage
						Information<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li class="${current == 'product' ? 'selected' : ''}">
						<a href="product">Product</a></li>
						<li class="${current == 'synonym' ? 'selected' : ''}">
						<a href="synonym">Synonym</a></li>
					</ul> <!-- second-level-items --></li>
			</ul>
			<!-- end side-menu -->
		</div>
		<!-- end sidebar-collapse --> </nav>
		<!-- end navbar side -->
		<!--  page-wrapper -->
		<div id="page-wrapper">

			<tiles:insertAttribute name="body" />

		</div>
		<!-- end page-wrapper -->

		<!-- footer -->
		<tiles:insertAttribute name="footer" />

	</div>
	<!-- end wrapper -->

	<!-- Core Scripts - Include with every page -->
	<script src="resources/assets/plugins/jquery-1.10.2.js"></script>
	<script src="resources/assets/plugins/bootstrap/bootstrap.min.js"></script>
	<script src="resources/assets/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="resources/assets/plugins/pace/pace.js"></script>
	<script src="resources/assets/scripts/siminta.js"></script>
	<script src="resources/assets/scripts/commonScript.js"></script>

</body>


</html>