F
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
</head>
<body class="sticky-header">
	<%
		String message = (String) session.getAttribute("MESSAGE");
	%>
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
				<div class="logo dark-logo-bg hidden-xs hidden-sm"></div>

				<div class="icon-logo dark-logo-bg hidden-xs hidden-sm"></div>
				<!--logo and logo icon end-->

				<!--toggle button start-->
				<a class="toggle-btn"><i class="fa fa-outdent"></i></a>
				<!--toggle button end-->

				<div class="notification-wrap"></div>

			</div>
			<!-- header section end-->

			<!-- page head start-->
			<div class="page-head">
				<h3>Success Page</h3>
			</div>
			<div class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<section class="panel panel-primary">
							<div class="panel-body">
								<div class="form-group">
									<%=message%>
								</div>
								<button type="button" class="btn btn-success m-b-10"
									value="Return To HomePage" name="btnAction"
									onclick="window.location = 'configuration'">Return To
									Config Home</button>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</section>
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
</body>
</html>
