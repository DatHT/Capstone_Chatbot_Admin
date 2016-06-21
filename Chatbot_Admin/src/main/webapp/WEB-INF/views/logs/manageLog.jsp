<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
<script src="resources/assets/scripts/logScript.js"></script>
<style>
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
<div class="c-header">
	<h2>Logs Manager</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
	</ul>
</div>
<div class="card">
	<div class="card-header">
		<h2>Logs Manager</h2>
	</div>

	<div class="card-body card-padding">
		<div class="row">
			<div class="col-sm-3 m-b-15">
				<button onclick="updateLog()">Update Log</button>
			</div>
		</div>
	</div>
</div>

<div class="card">
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
								listContent[int]);
					}
				}
			}
		};
		xmlhttp.open('GET', '/chatbot_admin/getLog', true);
		xmlhttp.send(null);
	</script>

	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Misunderstand</h2>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>User say</th>
								<th>Action</th>
								<th>Intent</th>
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
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">No entry found</h2>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Food</th>
								<th>Location</th>
								<th>Action</th>
								<th>Intent</th>
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

<div>
	<!--  Modals-->
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="user-say-container" class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="user-say-in-modal">Modal title</h4>
				</div>
				<div class="modal-body">
					<div id="list-phrase"></div>
				</div>
				<%-- <div class="modal-footer">
					<select class="selectpicker" id="selectIntent">
						<option value="">--------Please select--------</option>
						<c:forEach var="intent" items="${intents}">
							<option value="${intent.id}">${intent.name}</option>
						</c:forEach>
					</select>
				</div> --%>
				<div class="modal-footer">
					<div id="select_phrase_guide" style="display: none;" class="col-sm-8 m-b-25">
						<img src="resources/assets/img/select_phrase_guide.gif"
							class="img-responsive m-b-15 w-100" alt="">
					</div>
					<button id="save-button" type="button"
						class="btn btn-primary btn-icon-text waves-effect">
						<i class="zmdi zmdi-check-all"></i> Save changes
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End modal -->

	<!-- Lexical list -->
	<select class="form-control listLexical">
		<option value="">----Please select----</option>
		<c:forEach var="lexical" items="${lexicals}">
			<option value="${lexical.id}">${lexical.name}</option>
		</c:forEach>
	</select>
	<!-- End lexical list -->
</div>
