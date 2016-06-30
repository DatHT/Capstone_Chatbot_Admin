<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
<script src="resources/assets/scripts/logScript.js"></script>
<c:set var="paramName" value="${_csrf.parameterName}"/>
<c:set var="token" value="${_csrf.token}"/>
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
				<button class="btn btn-default btn-icon-text waves-effect"
					onclick="updateLog()">
					<i class="zmdi zmdi-refresh"></i> Manual Update
				</button>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Misunderstand</h2>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="no-entry-data-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th data-column-id="usersay" data-identifier="true">User say</th>
								<th data-column-id="count">Count</th>
								<th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
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
					<table id="not-found-data-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th data-column-id="food">Food</th>
								<th data-column-id="location">Location</th>
								<th data-column-id="addProduct" data-formatter="addProduct" data-sortable="false">Add product</th>
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
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
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
				<div class="modal-footer">
					<div id="select_phrase_guide" style="display: none;"
						class="col-sm-8 m-b-25">
						<img src="resources/assets/img/select_phrase_guide.gif"
							class="img-responsive m-b-15 w-100" alt="">
					</div>
					<button id="save-button" type="button" onclick="requestAddPhrase('${paramName}', '${token}')"
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
