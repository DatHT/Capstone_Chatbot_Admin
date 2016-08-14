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
		<div class="card">
			<div class="card-header">
				<h3>SUCCESS!!!</h3>
			</div>
			<div class="card-body card-padding">
				<div class="row">
					<div class="col-lg-12">
						<section class="panel panel-primary">
							<div class="panel-body">
								<div class="form-group">
									<strong><%=message%></strong>
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
