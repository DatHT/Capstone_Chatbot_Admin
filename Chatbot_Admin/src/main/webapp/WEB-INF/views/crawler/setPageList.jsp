<%-- 
    Document   : setPageList
    Created on : Feb 23, 2016, 10:21:54 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value = "/resources/assets/cssCode/runnable.css"/>"
	rel="stylesheet">
<link href='<c:url value="/resources/assets/cssCode/popup.css"/>'
	rel="stylesheet">
<script
	src="<c:url value ="/resources/assets/configscripts/jquery-2.2.0.js"/>"></script>
<script
	src='<c:url value="/resources/assets/configscripts/parseList.js" />'></script>

<!--right slidebar-->
<link
	href="<c:url value="/resources/assets/cssboostrap/slidebars.css"/>"
	rel="stylesheet">
</head>
<body class="c-header">
	<c:set var="url" value="${sessionScope.URL}" />
	<div class="c-header">
		<h2>Configuration</h2>
	</div>
	<div class="card">
		<div class="card-header">
			<h2>Please Select An Element And Get XPath</h2>
		</div>
	</div>
	<section>
		<div class="col-lg-12">
			<section class="panel">
				<div class="panel-body">
					<form name="myForm" id="myForm" action="processServlet"
						method="GET">
						<div class="form-group">
							<div class="progressRecipe">
								<div class="circle done">
									<span class="labelRecipe">0</span> <span class="title">Welcome</span>
								</div>
								<span class="bar"></span>
								<div class="circle active">
									<span class="labelRecipe">1</span> <span class="title">EachProduct</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">2</span> <span class="title">FoodName</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">3</span> <span class="title">Image</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">4</span> <span class="title">NextPage</span>
								</div>
							</div>
							<button type="button" class="btn btn-primary" value="BACK"
								onclick="back()">BACK</button>
							<button type="button" class="btn btn-primary" id="btnNext"
								value="NEXT" onclick="next()">NEXT</button>
							<!--disable-->
							<button type="button" class="btn btn-primary" id="btnPreview"
								value="PREVIEW" onclick="openpopup('popup')" disabled>PREVIEW</button>
							<button type="submit" class="btn btn-primary" id="btnAdd"
								name="btnAction" value="AddNewPageList" onclick="addNew()"
								disabled>AddNewPageList</button>
							<button type="button" class="btn btn-primary" value="HOME"
								onclick="window.location = 'configData'">HOME</button>
							<div class="table-responsive" style="min-height: 70px">

								<table class="table" id="tbItems" border="1" width="619"
									class="table">
									<th width="97%">Content</th>
								</table>
								<br />
								<div id="showXPath"></div>
								<br />
							</div>
						</div>
						<div id="popup" class="popup"></div>
						<div id="bg" class="popup_bg"></div>
						<div class="col-sm-12">
							<iframe style="width: 100%"
								sandbox="allow-same-origin allow-form" width="700" height="500"
								id="myframe" src="resources/tmp.html"> </iframe>
						</div>
						<table id="tbMain">

						</table>
					</form>
					<br />
				</div>
			</section>
		</div>
	</section>
	<!-- Placed js at the end of the document so the pages load faster -->
	<script
		src="<c:url value="/resources/assets/js/jquery-1.10.2.min.js" />"></script>

	<!--jquery-ui-->
	<script
		src="<c:url value ="/resources/assets/js/jquery-ui/jquery-ui-1.10.1.custom.min.js" />"
		type="text/javascript"></script>
	<!--Nice Scroll-->
	<%--         <script src="<c:url value="/resource/js/jquery.nicescroll.js" type="text/javascript" />"> </script> --%>

	<!--right slidebar-->
	<script src="<c:url value="/resources/assets/js/slidebars.min.js" />"></script>

	<!--switchery-->
	<script
		src="<c:url value="/resources/assets/js/switchery/switchery.min.js" />"></script>
	<script
		src="<c:url value= "/resources/assets/js/switchery/switchery-init.js" />"></script>

	<!--Icheck-->
	<script
		src="<c:url value="/resources/assets/js/icheck/skins/icheck.min.js" />"></script>
	<script src="<c:url value="/resources/assets/js/todo-init.js" />"></script>

	<!--jquery countTo-->
	<%--         <script src="<c:url value="/resource/js/jquery-countTo/jquery.countTo.js"  type="text/javascript" />"></script> --%>

	<!--owl carousel-->
	<script src="<c:url value="/resources/assets/js/owl.carousel.js" />"></script>


	<!--common scripts for all pages-->
	<script src="<c:url value="/resources/assets/js/scripts.js" />"></script>
</body>
</html>
