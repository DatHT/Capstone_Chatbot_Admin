<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script
	src="${pageContext.request.contextPath}/resources/assets/scripts/commonScript.js"></script>
<script>
    var tokenName = '${_csrf.parameterName}';
    var tokenValue = '${_csrf.token}';

</script>
<script
	src="${pageContext.request.contextPath}/resources/assets/scripts/logScript.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/scripts/exampleScript.js"></script>
<c:set var="intents" value="${INTENTS}" />
<div class="card">
	<div class="my-c-header">
		<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
		<input type="hidden" value="${_csrf.token}" id="token" />
		<h2>View Pattern Training</h2>
	</div>

	<div class="card-header">
		<h2>Intent Category</h2>
	</div>

	<div class="card-body card-padding">

		<div class="row">
			<div class="col-sm-3 m-b-15">

				<select class="chosen" data-placeholder="Choose a Intent..."
					onchange="loadIntent(this)" class="form-control" id="selectIntentView">
					<option value="empty"></option>

					<c:if test="${not empty intents}">
						<c:forEach var="intent" items="${intents}">
							<option value="${intent.id}">${intent.name}</option>
						</c:forEach>
					</c:if>

				</select>


			</div>

		</div>

	</div>
</div>

<div class="row">

	<div class="col-lg-12">
		<div class="card">
			<div class="card-header">
				<h2 id="tableHeader">Your templete</h2>
			</div>
			<div class="card-body card-padding">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-striped table-bordered"
								id="tableIntent">
								<thead>
									<tr>
										<th data-column-id="id" data-type="numeric"
											data-identifier="true">No.</th>
										<th data-column-id="name" data-formatter="commands-name">Name</th>
										<th data-column-id="update" data-formatter="commands"
											data-sortable="false">Action</th>
									</tr>
								</thead>
								<tbody id="intentTable">

								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>


	</div>
</div>
