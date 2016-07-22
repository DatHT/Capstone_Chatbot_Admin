<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Success Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/assets/configscripts/loadConfigsParse.js"
	type="text/javascript"></script>
<c:set var="pageInfo" value="${sessionScope.PGS}" />
<c:set var="configInfo" value="${sessionScope.CFS}" />
</head>
<body onload="loadPage()">

	<input type="hidden" id="pageInfo" value="${pageInfo.site}" />
	<input type="hidden" id="configInfo" value="${configInfo.site}" />
	<input type="hidden" id="nextPage" value="${pageInfo.nextPage}" />
	<%
		String message = (String) session.getAttribute("MESSAGE");
	%>
	<div class="card">
		<div class="card-body card-padding">
			<div class="form-group">
				<strong><%=message%></strong>
			</div>
			<button type="button" class="btn btn-success m-b-10"
				value="Return To HomePage" name="btnAction"
				onclick="window.location = 'configuration'">Return To
				Config Home</button>
		</div>
	</div>
	<div class="form-horizental">
		<div class="col-sm-6" id="viewStatic">
			<!--    Hover Rows  -->
			<div class="card-header">
				<h2 id="tableHeader">Static Parse</h2>
			</div>
			<div class="card-body card-padding">
				<form:form class="form-horizontal tasi-form" action="staticParse"
					id="parseForm" method="post">
					<div class="card-boy card-padding">
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
						<div class="form-group">
							<button type="submit" data-toggle="modal" href="#parsing"
								class="btn btn-primary" value="StaticParse" name="btnAction"
								onclick="showModal2()">StaticParse</button>
						</div>
						<div class="" style="display: none" id="loading_image">
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
						</div>
					</div>
				</form:form>
			</div>
			<!-- End  Hover Rows  -->
		</div>
		<div class="col-md-6" id="viewDynamic">
			<!--    Hover Rows  -->
			<div class="card-header">
				<h2 id="tableHeader">Dynamic Parse</h2>
			</div>
			<div class="card-body card-padding">
				<form:form class="form-horizontal tasi-form" action="dynamicParse"
					id="parseForm" method="post">
					<div class="card-boy card-padding">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="selectPage"
								style="text-align: left"> Select site(*): </label>
							<div class="col-sm-9">
								<select class="form-control m-b-10" name="txtLinkPage"
									id="selectPage">
								</select>
							</div>
						</div>
						<div id="selected" class="form-group">
							<label class="col-sm-3 control-label" style="text-align: left">Times
								to load data(*):</label>
							<div class="col-sm-9">
								<select name="txtPage" class="form-control m-b-10"
									onchange="changeNoPage()">
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
									<option id="zero-num"></option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<button type="submit" data-toggle="modal" href="#parsing"
								class="btn btn-primary" style="margin-top: 8px"
								value="DynamicParse" name="btnAction" onclick="showModal()">DynamicParse</button>
						</div>
						<div class="" style="display: none" id="loading_image2">
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
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<!-- End  Hover Rows  -->
</body>
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
<script>
	function showModal() {
		document.getElementById("loading_image2").style.display = 'block';
	}
	function showModal2() {
		document.getElementById("loading_image").style.display = 'block';
	}
</script>
<script>
	function changeNoPage() {
		document.getElementById("input-no-page").value = "";
	}
	function changeNumPage() {
		document.getElementById("zero-num").selected = "true";
	}
</script>
<script>
	function loadPage() {
		loadProcess('selectSite');
		checkSelect();
	}
</script>
<script type="text/javascript">
	function checkSelect() {
		if (document.getElementById("selectSite").value == "") {
			document.getElementById("viewStatic").style.display = 'none';
			document.getElementById("viewDynamic").className = "card col-md-12";
		}
		if (document.getElementById("selectPage").value == "") {
			document.getElementById("viewDynamic").style.display = 'none';
			document.getElementById("viewStatic").className = "card col-md-12";

		}
		if ((document.getElementById("selectSite").value == "")
				&& (document.getElementById("selectPage").value == "")) {
			document.getElementById("viewForce").style.display = 'none';
		}
	}
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
<script>
	function showModal() {
		document.getElementById("loading_image2").style.display = 'block';
	}
	function showModal2() {
		document.getElementById("loading_image").style.display = 'block';
	}
</script>
<script>
	function changeNoPage() {
		document.getElementById("input-no-page").value = "";
	}
	function changeNumPage() {
		document.getElementById("zero-num").selected = "true";
	}
</script>
<script>
	function loadPage() {
		loadCfs();
		checkSelect();
	}
</script>
<script type="text/javascript">
	function checkSelect() {
		if (document.getElementById("selectSite").value == "") {
			document.getElementById("viewStatic").style.display = 'none';
			document.getElementById("viewDynamic").className = "card col-md-12";
		}
		if (document.getElementById("selectPage").value == "") {
			document.getElementById("viewDynamic").style.display = 'none';
			document.getElementById("viewStatic").className = "card col-md-12";

		}
		if ((document.getElementById("selectSite").value == "")
				&& (document.getElementById("selectPage").value == "")) {
			document.getElementById("viewForce").style.display = 'none';
		}
	}
</script>
</html>
