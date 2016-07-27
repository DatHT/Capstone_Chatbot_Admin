<div id="ab" class="clearfix" data-columns="2">

	<div class="column size-1of2">
		<div class="card" id="step1" style="padding-top: 0%">
			<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 1: Select Intent</h2>
				<button onclick="displayStep2()"
					class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float">
					<i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i>
				</button>
			</div>

			<div class="card-body card-padding">

				<div class="row">
					<select class="chosen" data-placeholder="Choose a Intent..."
						onchange="loadIntent(this)" class="form-control" id="selectIntent">
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

</div>

<div class="row">

	<div class="col-lg-7" style="display: none" id="step3">
		<div class="card">
			<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 3: Start Training</h2>
				<button onclick="insertPattern()"
					class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float">
					<i class="zmdi zmdi-plus"></i>
				</button>
			</div>
			<div class="card-body card-padding">
				<div id="user-say-container">
					<blockquote class="m-b-25" id="chosenExample"></blockquote>
				</div>

				<div class="card">
					<div class="card-body card-padding" id="containerDiv"></div>
				</div>
				<div class="card">
					<div class="card-body card-padding">
						<div id="box-dragable" ondrop="drop(event)"
							ondragover="allowDrop(event)">

							<c:forEach begin="1" end="4" varStatus="counter">
								<div id="any${counter.count}" class="draggable" draggable="true"
									ondragstart="drag(event)">any</div>
							</c:forEach>
							<c:forEach var="dragItem" items="${lexicals}" varStatus="counter">
								<div id="${dragItem.name}" class="draggable" draggable="true"
									ondragstart="drag(event)">${dragItem.name}</div>
							</c:forEach>
						</div>
					</div>
				</div>
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
