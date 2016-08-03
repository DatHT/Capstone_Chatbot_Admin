<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<!-- Mirrored from byrushan.com/projects/mae/1-0/login.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 17 Jun 2016 02:29:32 GMT -->
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Material Admin</title>


<!-- Vendor CSS -->
<link
	href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/animate.css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/google-material-color/dist/palette.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css"
	rel="stylesheet">

<!-- CSS -->
<link
	href="${pageContext.request.contextPath}/resources/assets/css/app.min.1.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/app.min.2.css"
	rel="stylesheet">
</head>

<body>
	<div class="login" data-lbg="teal">
		<!-- Login -->
		<div class="l-block toggled" id="l-login">
			<div class="lb-header palette-Teal bg">
				<i class="zmdi zmdi-account-circle"></i> Hi there! Welcome to PSIB
			</div>

			<div class="lb-body">

				<form action="j_spring_security_check" method="POST">
					<div class="form-group fg-float">
						<div class="fg-line">
							<input name="username" type="text"
								class="input-sm form-control fg-input"> <label
								class="fg-label">Username</label>
						</div>
					</div>

					<div class="form-group fg-float">
						<div class="fg-line">
							<input name="password" type="password"
								class="input-sm form-control fg-input"> <label
								class="fg-label">Password</label>
						</div>
					</div>

					<button type="submit" class="btn palette-Teal bg">Sign in</button>

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>

	<!-- Javascript Libraries -->
	<script
		src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/jquery/dist/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/Waves/dist/waves.min.js"></script>

	<!-- Placeholder for IE9 -->
	<!--[if IE 9 ]>
<script src="${pageContext.request.contextPath}/vendors/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
<![endif]-->

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/functions.js"></script>

</body>
</html>