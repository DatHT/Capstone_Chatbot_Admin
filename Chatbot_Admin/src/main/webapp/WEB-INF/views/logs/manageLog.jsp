<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
<script src="resources/assets/scripts/commonScript.js"></script>
<script src="resources/assets/scripts/logScript.js"></script>
<c:set var="paramName" value="${_csrf.parameterName}" />
<c:set var="token" value="${_csrf.token}" />
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
<div class="card">
	<div class="my-c-header">
		<h2>Logs Manager</h2>
	</div>

	<div class="card-header">
		<h2>Logs Manager</h2>
	</div>

	<div class="card-body card-padding">
		<div class="row">
			<div class="col-sm-3 m-b-15">
				<button class="btn btn-default btn-icon-text waves-effect"
					onclick="updateLog('${paramName}', '${token}')">
					<i class="zmdi zmdi-refresh"></i> Manual Update
				</button>
			</div>
		</div>
	</div>
</div>

	<div class="col-lg-7">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Misunderstand<small>Contains the sentence that BOT does not understand what user say </small></h2>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="no-entry-data-table"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th data-column-id="logid" data-visible="false">ID</th>
								<th data-column-id="status" data-formatter="statusIcon" data-visible="false"></th>
								<th data-column-id="custom" data-formatter="unreadText"
									data-identifier="true">User say</th>
								<th data-column-id="usersay" data-visible="false">main user say</th>
								<th data-column-id="count" data-visible="false"  data-order="desc">Count</th>
								<th data-column-id="commands" data-formatter="commands"
									data-sortable="false">Commands</th>
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
	<div class="col-lg-5">
		<!--    Hover Rows  -->
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">No entry found<small>Contains what user request but we have no result. </small></h2>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="not-found-data-table"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th data-column-id="logid" data-visible="false">ID</th>
								<th data-column-id="food" data-formatter="show-food-count">Food</th>
								<th data-column-id="location">Location</th>
								<th data-column-id="count" data-visible="false"  data-order="desc">Count</th>
								<th data-column-id="notfound-commands" data-formatter="notfound-commands"
									data-sortable="false">Commands</th>
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
<div class="col-lg-12">
	<div class="card">
		<div class="card-header">
			<h2 id="tableReport">Reported product<small>Contains the product which reported by user. It can be wrong name or address... </small></h2>
		</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table id="reported-data-table"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th data-column-id="logid" data-visible="false">ID</th>
							<th data-column-id="productId" data-identifier="true">Product
								ID</th>
							<th data-column-id="addressId" data-identifier="true">Address
								ID</th>
							<th data-column-id="productName">Product</th>
							<th data-column-id="districtName">District</th>
							<th data-column-id="restaurantName">Restaurant</th>
							<th data-column-id="reported-commands" data-formatter="reported-commands"
								data-sortable="false">Commands</th>
						</tr>
					</thead>
					<tbody id="reported-product-table-body">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div>
	<!--  Modals-->
	<div class="modal fade" id="myModal" data-modal-color="teal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="user-say-container" class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="user-say-in-modal">Modal title</h4>
				</div>
				<div class="modal-body" style="background: #FFFFFF; color:#212121;">
					<div id="list-phrase"></div>
				</div>
				<div class="modal-footer"  style="background: #FFFFFF; color:#212121;">
					<div id="select_phrase_guide" style="display: none; margin-bottom: 0px !important"
						class="col-sm-8 m-b-25">
						<img src="resources/assets/img/select_phrase_guide.gif"
							class="img-responsive m-b-15 w-100" alt="">
					</div>
					<button id="save-button" type="button"
						onclick="requestAddPhrase('${paramName}', '${token}')"
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
	<!-- Modal for full conversation -->
	<div class="modal fade" id="conversationModal" tabindex="-1"
		role="dialog" aria-hidden="true" data-modal-color="teal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<p style="font-size:17px;margin-bottom: 5px;">
						<small>USER SAYS:</small>
						<span class="modal-title" style="font-weight:700;">Modal title</span>
					</p>
					<small style="padding-left:20px;font-size:12px;">Contains all user request in session that have misunderstand sentence what user say.</small>
				</div>
				<div class="modal-body" style="max-height: 50vh;overflow-y: auto; background: #FFFFFF; color:#212121;">
					<div id="panel-group" class="panel-group" role="tablist"
						aria-multiselectable="true" style="margin-bottom: 0;">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info btn-add-training">Add this sentence to training pool </button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>
