<%-- 
    Document   : welcome
    Created on : Mar 6, 2016, 12:26:43 PM
    Author     : Dell
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Setup parser config</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value = "/resources/crawler/configscripts/jquery-2.2.0.js"/>"
	type="text/javascript"></script>
<script src="<c:url value = "/resources/crawler/configscripts/loadConfigs.js"/>"
	type="text/javascript"></script>
<script src="<c:url value = "/resources/crawler/dist/sweetalert.min.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value = "/resources/crawler/dist/sweetalert.css" />">

<!--right slidebar-->
<link href="<c:url value = "/resources/crawler/cssboostrap/slidebars.css" />"
	rel="stylesheet">

<!--switchery-->
<link href="<c:url value = "/resources/crawler/js/switchery/switchery.min.css"/>"
	rel="stylesheet" type="text/css" media="screen" />

<!--jquery-ui-->
<link
	href="<c:url value = "/resources/crawler/js/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>"
	rel="stylesheet" />

<!--iCheck-->
<link href="<c:url value = "/resources/crawler/js/icheck/skins/all.css" />"
	rel="stylesheet">

<link href="<c:url value = "/resources/crawler/css/owl.carousel.css"/>"
	rel="stylesheet">


<!--common style-->
<link href="<c:url value="/resources/crawler/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/crawler/css/style-responsive.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/crawler/cssCode/customize.css" />"
	rel="stylesheet">
</head>
<body onload="return loadProcess('selectSite')" class="sticky-header">
	<script>
		regObjCfg = "${sessionScope.INFOCONFIG}";
		regObjPage = "${sessionScope.INFOPAGE}";
	</script>
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
			<!-- body content start-->
			<!-- sidebar left end-->
			<div class="header-section">
				<!--logo and logo icon start-->
				<div class="logo dark-logo-bg hidden-xs hidden-sm"></div>

				<div class="icon-logo dark-logo-bg hidden-xs hidden-sm"></div>
				<!--logo and logo icon end-->

				<!--toggle button start-->
				<a class="toggle-btn"><i class="fa fa-outdent"></i></a>
				<!--toggle button end-->

				<div class="notification-wrap"></div>
			</div>
			<div class="page-head">
				</br>
			</div>
			<div class="wrapper">
				<div class="row">
					<div class="col-sm-6">
						<section class="panel panel-primary">
							<header class="panel-heading header-customize-color">
								<label class="header-customize-font">Setup List Page</label>
							</header>
							<div class="panel-body">
								<form action="processServlet"
									onsubmit="return checkInputConfig(this)">
									<div class="form-group">
										<label for="input-01">URL of input page (required):</label> <input
											type="url" class="form-control" size="45" name="txtURL"
											id="input-01" required />
									</div>
									<input type="submit" id="confirmation"
										class="btn btn-primary m-b-10" value="Set List Page"
										name="btnAction" onclick="getVal(this.value)" />


								</form>
							</div>
						</section>
					</div>
					<div class="col-sm-6">
						<section class="panel panel-primary">
							<header class="panel-heading header-customize-color">
								<label class="header-customize-font">Set Parser Config</label>
							</header>
							<div class="panel-body">
								<form action="processServlet" onsubmit="return checkInputConfig(this)">
									<div class="form-group">
										<label for="input-01">URL of input page (required):</label> <input
											type="url" class="form-control" size="45" name="txtURL"
											id="input-01" required />
									</div>
									<input type="submit" id="confirmation"
										class="btn btn-primary m-b-10" value="Set Parser Config"
										name="btnAction" onclick="getVal(this.value)" />
								</form>
							</div>
						</section>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<section class="panel">
							<header class="panel-heading btn-success">
								<label class="header-customize-font">Force Parse</label>
							</header>
							<div class="panel-body">
								<form class="form-horizontal tasi-form" action="forceParse"
									id="parseForm">
									<div class="form-group">
										<label class="col-sm-3 control-label" for="selectSite"
											style="text-align: left"> Select site(*): </label>
										<div class="col-sm-9">
											<select class="form-control m-b-10" name="txtLinkPage"
												id="selectSite">
											</select>
										</div>
									</div>
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
								<!-- Modal -->
								<form action="forceParse">
									<div aria-hidden="true" aria-labelledby="myModalLabel"
										role="dialog" tabindex="-1" id="parsing" class="modal fade">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title">Your system is parsing now</h4>	
												</div>
												<div class="modal-body">
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />
													<img src="<c:url value="/resources/assets/img/loader2.gif"/>" />															
												</div>
												<div class="modal-footer">
												
													<input class="btn btn-info" type="submit" value="STOP"
														name="btnAction" />
												</div>
											</div>
										</div>
									</div>
								</form>
								<!-- modal -->
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="<c:url value="/resources/crawler/js/jquery-1.10.2.min.js" />"></script>

	<!--jquery-ui-->
	<script
		src="<c:url value ="/resources/crawler/js/jquery-ui/jquery-ui-1.10.1.custom.min.js" />"
		type="text/javascript"></script>

	<script src="<c:url value="/resources/crawler/js/jquery-migrate.js"/>"></script>
	<script src="<c:url value="/resources/crawler/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/crawler/js/modernizr.min.js" />"></script>

	<!--Nice Scroll-->
	<%--         <script src="<c:url value="/resources/crawler/js/jquery.nicescroll.js" type="text/javascript" />"> </script> --%>

	<!--right slidebar-->
	<script src="<c:url value="/resources/crawler/js/slidebars.min.js" />"></script>

	<!--switchery-->
	<script src="<c:url value="/resources/crawler/js/switchery/switchery.min.js" />"></script>
	<script
		src="<c:url value= "/resources/crawler/js/switchery/switchery-init.js" />"></script>

	<!--Icheck-->
	<script src="<c:url value="/resources/crawler/js/icheck/skins/icheck.min.js" />"></script>
	<script src="<c:url value="/resources/crawler/js/todo-init.js" />"></script>

	<!--jquery countTo-->
	<%--         <script src="<c:url value="/resources/crawler/js/jquery-countTo/jquery.countTo.js"  type="text/javascript" />"></script> --%>

	<!--owl carousel-->
	<script src="<c:url value="/resources/crawler/js/owl.carousel.js" />"></script>


	<!--common scripts for all pages-->
	<script src="<c:url value="/resources/crawler/js/scripts.js" />"></script>
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
		$("form").find("select").change(function() {
			$(this).siblings("select").val("");
			// optionally $("form").find("select").not($(this)).val("");        
		});
		function changeNo() {
			document.getElementById("input-no").value = "";
		}
		function changeNum() {
			document.getElementById("zero").selected = "true";
		}
	</script>
	<script>
		$('#parseForm').submit(function() {
			$('#loading_image').show(); // show animation
			return true; // allow regular form submission
		});
	</script>

</body>
</html>

