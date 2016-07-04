<script src="resources/assets/scripts/dataConfig.js"></script>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
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
			onclick="applySynchronize()" style="float:right">Apply</button>
			</div>
			<div class="row">
				<div class="col-sm-4 m-b-20">
					<div class="toggle-switch">
						<label for="ts1" class="ts-label">Synchronize From API To
							Database</label> <input id="ts1" type="checkbox" hidden="hidden">
						<label for="ts1" class="ts-helper"></label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4 m-b-20">
					<div class="toggle-switch">
						<label for="ts2" class="ts-label">Synchronize Database to
							API</label> <input id="ts2" type="checkbox" hidden="hidden"> <label
							for="ts2" class="ts-helper"></label>
					</div>
				</div>
			</div>

		</div>


	</div>