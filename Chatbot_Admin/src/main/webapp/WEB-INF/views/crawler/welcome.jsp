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
<body onload="return loadPage()" class="sticky-header">
	<script>
		regObjCfg = "${sessionScope.INFOCONFIG}";
		regObjPage = "${sessionScope.INFOPAGE}";
	</script>
	<div class="c-header">
		<h2>Crawler Manger</h2>
	</div>
	<div class="card">

		<div class="form-horizontal">
			<div class="card-header">
				<h2>Configuration</h2>
			</div>
			<div class="card-body card-padding">
				<form action="processServlet"
					onsubmit="return checkInputConfig(this)">
					<div class="card-body card-padding">
						<div class="form-group">
							<label for="input-01">URL of input page (required):</label> <input
								type="url" class="form-control" size="45" name="txtURL"
								id="input-01" required />
						</div>
						<div class="form-group">
							<button type="submit" id="confirmation" class="btn btn-primary"
								value="Set List Page" name="btnAction"
								onclick="getVal(this.value)">Set List Page</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<div class="card" id="viewForce">
		<div class="card-header">
			<h2>Force Parser</h2>
		</div>
		<div class="form-horizontal">
			<div class="card col-sm-6" id="viewStatic">
				<!--    Hover Rows  -->
				<div class="card-header">
					<h2 id="tableHeader">Static Parse</h2>
				</div>
				<div class="card-body card-padding">
					<form class="form-horizontal tasi-form" action="staticParse"
						id="parseForm">
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
									class="btn btn-primary" value="StaticParse" name="btnAction">StaticParse</button>
							</div>
						</div>
					</form>
					<!-- 					Modal -->
					<!-- 					<form action="staticParse"> -->
					<!-- 						<div aria-hidden="true" aria-labelledby="myModalLabel" -->
					<!-- 							role="dialog" tabindex="-1" id="parsing" class="modal fade"> -->
					<!-- 							<div class="modal-dialog"> -->
					<!-- 								<div class="modal-content"> -->
					<!-- 									<div class="modal-header"> -->
					<!-- 										<h4 class="modal-title">Your system is parsing now</h4> -->
					<!-- 									</div> -->
					<!-- 									<div class="modal-body"> -->
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<!-- 									</div> -->
					<!-- 									<div class="modal-footer"> -->

					<!-- 										<button class="btn btn-primary" type="submit" value="STOP" -->
					<!-- 											name="btnAction">STOP</button> -->
					<!-- 									</div> -->
					<!-- 								</div> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</form> -->
					<!-- modal -->
				</div>
				<!-- End  Hover Rows  -->
			</div>
			<div class="card col-md-6" id="viewDynamic">
				<!--    Hover Rows  -->
				<div class="card-header">
					<h2 id="tableHeader">Dynamic Parse</h2>
				</div>
				<div class="card-body card-padding">
					<form class="form-horizontal tasi-form" action="dynamicParse"
						id="parseForm">
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
								</br> </br>
							</div>
							<div class="form-group">
								<button type="submit" data-toggle="modal" href="#parsing"
									class="btn btn-primary" style="margin-top: 8px"
									value="DynamicParse" name="btnAction" onclick="showModal()">DynamicParse</button>
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
					</form>
					<!-- Modal -->
					<!-- 					<form action="dynamicParse"> -->
					<!-- 						<div aria-hidden="true" aria-labelledby="myModalLabel" -->
					<!-- 							role="dialog" tabindex="-1" id="parsing" class="modal fade"> -->
					<!-- 							<div class="modal-dialog"> -->
					<!-- 								<div class="modal-content"> -->
					<!-- 									<div class="modal-header"> -->
					<!-- 										<h4 class="modal-title">Your system is parsing now</h4> -->
					<!-- 									</div> -->
					<!-- 									<div class="modal-body"> -->
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<%-- 										<img src="<c:url value="/resources/assets/img/loader2.gif"/>" /> --%>
					<!-- 									</div> -->
					<!-- 									<div class="modal-footer"> -->

					<!-- 										<button class="btn btn-primary" type="submit" value="STOP" -->
					<!-- 											name="btnAction">STOP</button> -->
					<!-- 									</div> -->
					<!-- 								</div> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</form> -->
					<!-- modal -->
				</div>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<!-- Placed js at the end of the document so the pages load faster -->
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

</body>
</html>

