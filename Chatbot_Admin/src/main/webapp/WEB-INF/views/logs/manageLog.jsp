<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/logScript.js"></script>
<style>
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 50%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}
</style>
<div class="row">
	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Logs Manager</h1>
	</div>
	<!-- end  page header -->
</div>
<div>
	<button onclick="updateLog()">Update Log</button>

	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content">
			<span class="close">×</span>
			<div id="user-say-container">
				<p id="user-say-in-modal"></p>
			</div>
			<div id="list-phrase"></div>
		</div>

	</div>
</div>
<div class="row">
	<script>
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				var data = JSON.parse(xmlhttp.responseText);
				var listContent = data.contents;

				for (var int = 0; int < listContent.length; int++) {
					if (listContent[int].errCode == '300') {
						createRowNoEntry("no-entry-table-body",
								listContent[int]);
					} else if (listContent[int].errCode == '404') {
						createRowNotFound("not-found-table-body",
								listContent[int].contexts);
					}
				}
			}
		};
		xmlhttp.open('GET', '/chatbot_admin/getLog', true);
		xmlhttp.send(null);
	</script>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="panel panel-default">
			<div class="panel-heading">No entry found</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>User say</th>
							</tr>
						</thead>
						<tbody id="no-entry-table-body">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="panel panel-default">
			<div class="panel-heading">Misunderstand</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Food</th>
								<th>Location</th>
							</tr>
						</thead>
						<tbody id="not-found-table-body">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
</div>
