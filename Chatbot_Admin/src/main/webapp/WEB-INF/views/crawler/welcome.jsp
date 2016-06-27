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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/crawler/configscripts/loadConfigs.js"
	type="text/javascript"></script>
<body onload="return loadProcess('selectSite')" class="sticky-header">
	<script>
		regObjCfg = "${sessionScope.INFOCONFIG}";
		regObjPage = "${sessionScope.INFOPAGE}";
	</script>
	<div class="c-header">
		<h2>Crawler Manger</h2>
	</div>
	<div class="card">
		<div class="card-header">
			<h2>Configuration</h2>
		</div>
	</div>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Set List Page</h2>
			</div>
			<div class="panel-body">
				<form action="processServlet"
					onsubmit="return checkInputConfig(this)">
					<div class="form-group">
						<label for="input-01">URL of input page (required):</label> <input
							type="url" class="form-control" size="45" name="txtURL"
							id="input-01" required />
					</div>
					<button type="submit" id="confirmation" class="btn btn-primary"
						value="Set List Page" name="btnAction"
						onclick="getVal(this.value)">Set List Page</button>
					<!-- 							<input type="submit" id="confirmation" -->
					<!-- 								class="btn btn-primary m-b-10" value="Set List Page" -->
					<!-- 								name="btnAction" onclick="getVal(this.value)" /> -->
				</form>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Set Parser Config</h2>
			</div>
			<div class="panel-body">
				<form action="processServlet"
					onsubmit="return checkInputConfig(this)">
					<div class="form-group">
						<label for="input-01">URL of input page (required):</label> <input
							type="url" class="form-control" size="45" name="txtURL"
							id="input-01" required />
					</div>
					<button type="submit" id="confirmation" class="btn btn-primary"
						value="Set Parser Config" name="btnAction"
						onclick="getVal(this.value)">Set Parser Config</button>
					<!-- 							<input type="submit" id="confirmation" -->
					<!-- 								class="btn btn-primary m-b-10" value="Set List Page" -->
					<!-- 								name="btnAction" onclick="getVal(this.value)" /> -->
				</form>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<div class="col-lg-12">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Force Parse</h2>
			</div>
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
					<button type="submit" data-toggle="modal" href="#parsing"
						class="btn btn-primary" value="ForceParse" name="btnAction">ForceParse</button>
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

									<button class="btn btn-info" type="submit" value="STOP"
										name="btnAction">STOP</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				<!-- modal -->
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<!-- Placed js at the end of the document so the pages load faster -->

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

