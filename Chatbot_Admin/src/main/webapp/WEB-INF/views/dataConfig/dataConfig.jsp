<script src="resources/assets/scripts/dataConfig.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="api" value="${api_sync}" />
<c:set var="log" value="${log_sync}" />
<div class="c-header">
	<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
	<input type="hidden" value="${_csrf.token}" id="token" />
	<h2>Typography</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
		<li class="dropdown"><a href="#" data-toggle="dropdown"> <i
				class="zmdi zmdi-more-vert"></i>
		</a>

			<ul class="dropdown-menu dropdown-menu-right">
				<li><a href="#">Refresh</a></li>
				<li><a href="#">Manage Widgets</a></li>
				<li><a href="#">Widgets Settings</a></li>
			</ul></li>
	</ul>
</div>

<div class="row">
	<div class="card">
		<div class="card-header">
			<h2>Config Data</h2>
		</div>

		<div class="card-body card-padding">
			<div class="row">
				<button class="btn btn-primary btn-lg waves-effect"
					onclick="applySynchronize()" style="float: right">Apply</button>
			</div>
			<div class="row">
				<div class="toggle-switch col-lg-6" style="min-width: 800px !important;">
					<div class="col-md-8">
						<label for="ts1" class="ts-label">Synchronize From API To
							Database </label>
					</div>
					<div class="col-md-4">
						<input id="ts1" type="checkbox" hidden="hidden"
							<c:if test="${api.status}">checked</c:if>
							> 
							<label
							for="ts1" class="ts-helper"></label>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				<div class="toggle-switch col-lg-6" style="min-width: 800px !important;">
					<div class="col-md-8">
						<label for="ts2" class="ts-label">Update Log</label>

					</div>
					<div class="col-md-4">
						<input id="ts2" type="checkbox" hidden="hidden"
							<c:if test="${log.status}">checked</c:if>> <label
							for="ts2" class="ts-helper"></label>
					</div>
				</div>
			</div>
		</div>
	</div>


</div>