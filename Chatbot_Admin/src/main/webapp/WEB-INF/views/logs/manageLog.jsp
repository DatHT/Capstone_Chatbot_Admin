<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="lexicals" value="${LEXICALS}" />
<script src="resources/assets/scripts/logScript.js"></script>
<style>
.modal {
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

.modal-footer {
	margin-top: 0px;
}

.remove {
	color: #aaaaaa;
	float: right;
	font-size: 10px;
	font-weight: bold;
	margin: 10px 20px;
}

.remove:hover, .remove:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.listLexical {
	display: none;
}

.choose-phrase-guide {
	float: left;
	margin: 11px 0px 0px 15px;
}
</style>
<div class="row">
	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Logs Manager</h1>
		<button onclick="updateLog()">Update Log</button>
	</div>
	<!-- end  page header -->
</div>
<div>
	<!--  Modals-->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="user-say-container" class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="user-say-in-modal">Modal title</h4>
				</div>
				<p class="choose-phrase-guide">Select text to choose phrase</p>
				<div id="list-phrase" class="modal-body"></div>
				<div class="modal-footer">
					<button id="save-button" type="button" class="btn btn-primary">Save
						changes</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End modal -->
	<!-- Lexical list -->
	<select class="listLexical btn btn-block">
		<option value="">----Please select----</option>
		<c:forEach var="lexical" items="${lexicals}">
			<option value="${lexical.id}">${lexical.name}</option>
		</c:forEach>
	</select>
	<!-- End lexical list -->
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
