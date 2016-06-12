<%-- 
    Document   : successParse
    Created on : Mar 29, 2016, 6:56:14 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Success Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	history.pushState(null, null, 'pagename');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, 'pagename');
	});
</script>
<!--right slidebar-->
<link href="css/slidebars.css" rel="stylesheet">

<!--switchery-->
<link href="js/switchery/switchery.min.css" rel="stylesheet"
	type="text/css" media="screen" />

<!--jquery-ui-->
<link href="js/jquery-ui/jquery-ui-1.10.1.custom.min.css"
	rel="stylesheet" />

<!--iCheck-->
<link href="js/icheck/skins/all.css" rel="stylesheet">

<link href="css/owl.carousel.css" rel="stylesheet">


<!--common style-->
<link href="css/style.css" rel="stylesheet">
<link href="css/style-responsive.css" rel="stylesheet">

<link href="cssCode/customize.css" rel="stylesheet">
<style>
.modal-body {
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
}
</style>
</head>
<body class="sticky-header">
	<section>
		<!-- sidebar left start-->
		<div class="sidebar-left">
			<!--responsive view logo start-->
			<div class="logo dark-logo-bg"></div>
			<!--responsive view logo end-->

			<div class="sidebar-left-info"></div>
		</div>
		<!-- sidebar left end-->
		<div class="body-content">
			<!-- header section start-->
			<div class="header-section">

				<!--logo and logo icon start-->
				<div class="logo dark-logo-bg hidden-xs hidden-sm">
				</div>

				<div class="icon-logo dark-logo-bg hidden-xs hidden-sm">
				</div>
				<!--logo and logo icon end-->

				<!--toggle button start-->
				<a class="toggle-btn"><i class="fa fa-outdent"></i></a>
				<!--toggle button end-->

				<div class="notification-wrap">
					<!--right notification end-->
				</div>

			</div>
			<!-- header section end-->

			<!-- page head start-->

			<div class="page-head">
				<h3>Success Page</h3>
			</div>
			<div class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading header-customize-color">
								<label class="header-customize-font"> SUCCESS </label>
							</header>
							<div class="panel-body">
								<div class="form-group">New configuration has been
									inserted to storage!</div>
								<input type="button" class="btn btn-info m-b-10"
									value="Return To HomePage" name="btnAction"
									onclick="window.location = 'configData'" />
							</div>
						</section>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading header-customize-color">
								<label class="header-customize-font"> Force parse </label>
							</header>
							<div class="panel-body">
								<form class="form-horizontal tasi-form" action="ForceParse">
									<div id="selected" class="form-group">
										<label class="col-sm-3 control-label" style="text-align: left">Number
											of page(*):</label>
										<div class="col-sm-9">
											<select name="txtPage" class="form-control m-b-10"
												onchange="changeNo()">
												<option>1</option>
												<option>2</option>
												<option>3</option>
												<option>4</option>
												<option>5</option>
												<option>10</option>
												<option>15</option>
												<option>20</option>
												<option>50</option>
												<option>100</option>
												<option>1000</option>
												<option>2000</option>
												<option>3000</option>
												<option id="zero"></option>
											</select>
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align: left">
											Or at page(*): </label>
										<div class="col-sm-9">
											<input type="number" min="0" class="form-control m-b-10"
												name="txtNoPage" id="input-no" onchange="changeNum()" />
										</div>
									</div>

									<input type="submit" data-toggle="modal" href="#parsing"
										class="btn btn-info m-b-10" value="ForceParse"
										name="btnAction" />
								</form>
							</div>
							<!-- Modal -->
							<form action="ProcessServlet">
								<div aria-hidden="true" aria-labelledby="myModalLabel"
									role="dialog" tabindex="-1" id="parsing" class="modal fade">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h4 class="modal-title">Your system is parsing now</h4>
											</div>
											<div class="modal-body"></div>
											<div class="modal-footer">
												<input class="btn btn-info" type="submit" value="STOP"
													name="btnAction" />
											</div>
										</div>
									</div>
								</div>
							</form>
							<!-- modal -->
						</section>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="js/jquery-1.10.2.min.js"></script>

	<!--jquery-ui-->
	<script src="js/jquery-ui/jquery-ui-1.10.1.custom.min.js"
		type="text/javascript"></script>

	<script src="js/jquery-migrate.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/modernizr.min.js"></script>

	<!--Nice Scroll-->
	<script src="js/jquery.nicescroll.js" type="text/javascript"></script>

	<!--right slidebar-->
	<script src="js/slidebars.min.js"></script>

	<!--switchery-->
	<script src="js/switchery/switchery.min.js"></script>
	<script src="js/switchery/switchery-init.js"></script>

	<!--Icheck-->
	<script src="js/icheck/skins/icheck.min.js"></script>
	<script src="js/todo-init.js"></script>

	<!--jquery countTo-->
	<script src="js/jquery-countTo/jquery.countTo.js"
		type="text/javascript"></script>

	<!--owl carousel-->
	<script src="js/owl.carousel.js"></script>


	<!--common scripts for all pages-->

	<script src="js/scripts.js"></script>


	<script type="text/javascript">
		$(document).ready(function() {

			//countTo

			$('.timer').countTo();

			//owl carousel

			$("#news-feed").owlCarousel({
				navigation : true,
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem : true,
				autoPlay : true
			});
		});

		$(window).on("resize", function() {
			var owl = $("#news-feed").data("owlCarousel");
			owl.reinit();
		});
	</script>
	<script>
		function changeNo() {
			document.getElementById("input-no").value = "";
		}
		function changeNum() {
			document.getElementById("zero").selected = "true";
		}
	</script>
</body>
</html>
