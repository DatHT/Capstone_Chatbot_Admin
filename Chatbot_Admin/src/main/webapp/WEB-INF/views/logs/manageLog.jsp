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

.remove {
	color: #aaaaaa;
	float: right;
	font-size: 10px;
	font-weight: bold;
	margin-top: 15px;
}

.remove:hover, .remove:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.phrase-el {
	width: 30%;
}

.lexical-el {
	width: 50%;
}

.listLexical {
	display: none;
}

.choose-phrase-guide {
	float: left;
	margin-left: 10px;
	margin-top: -10px;
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
	<div class="modal fade in" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="user-say-container" class="modal-header">
					<button type="button" class="close">×</button>
					<h4 id="user-say-in-modal" class="modal-title" id="myModalLabel"></h4>
				</div>
				<div id="list-phrase" class="modal-body">
					<p class="choose-phrase-guide">Select text to choose phrase</p>
				</div>
				<div class="modal-footer">
					<button id="save-button" class="btn btn-primary">Save
						changes</button>
				</div>
			</div>
		</div>
	</div>
	<!-- <div id="myModal" class="modal">
		Modal content
		<div class="modal-content">
			<span class="close">CLOSE</span>
			<button id="save-button" class="btn-save" disabled>SAVE</button>
			<div id="user-say-container">
				<p id="user-say-in-modal"></p>
			</div>
			<div id="list-phrase"></div>
		</div>
	</div> -->
	<select class="listLexical btn btn-block">
		<option value="">----Please select----</option>
		<c:forEach var="lexical" items="${lexicals}">
			<option value="${lexical.id}">${lexical.name}</option>
		</c:forEach>
	</select>
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
