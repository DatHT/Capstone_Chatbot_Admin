<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="resources/assets/scripts/exampleScript.js"></script>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
<div class="row">

	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Traing Bot By Example</h1>
	</div>
	<!-- end  page header -->


</div>

<div class="row">
	<div class="col-lg-5">

		<form role="form">
			<div class="form-group">
				<div class="form-group">
					<label>Intents</label> <select onchange="loadIntent(this)"
						class="form-control" id="selectIntent">
						<option value="">--------Please select--------</option>
						<c:forEach var="intent" items="${intents}">
							<option value="${intent.id}">${intent.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

		</form>
	</div>

	<div class="col-lg-7">
		<div class="panel panel-info">
			<div class="panel-heading">Explaination</div>
			<div class="panel-body">
				<p>An intent represents a mapping between what a user says and
					what action should be taken by your software.</p>
				<p>Example here is a "User say". Examples are written in natural
					language and annotated so that parameter values can be extracted</p>
			</div>

		</div>
	</div>
	<div class="col-lg-8">
		<div class="panel panel-primary">
			<div class="panel-heading">Your templete</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						id="intentTable">
						<thead>
							<tr>
								<th>No.</th>
								<th>Name</th>
								<th>Action</th>
								<th>Action</th>
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
<div class="row">
	<div class="col-lg-12">
		<!--Collapsible Accordion Panel Group   -->
		<div class="panel panel-default">
			<div class="panel-heading">Manage Example</div>
			<div class="panel-body">
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne">Add Example</a>
							</h4>

						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
							<div class="col-lg-4">
								<div id="box-dragable" ondrop="drop(event)"
									ondragover="allowDrop(event)">

									<c:forEach begin="1" end="4" varStatus="counter">
										<div id="any${counter.count}" class="draggable"
											draggable="true" ondragstart="drag(event)">any</div>
									</c:forEach>
									<c:forEach var="dragItem" items="${lexicals}"
										varStatus="counter">
										<div id="${dragItem.name}" class="draggable" draggable="true"
											ondragstart="drag(event)">${dragItem.name}</div>
									</c:forEach>
								</div>
								
							</div>
							<div class="col-lg-8">
								<div id="droptarget" ondrop="drop(event)"
									ondragover="allowDrop(event)"></div>
									
								<div id="buttonadd">
									<button type="button" onclick="insertPattern('intentTable')"
									 class="btn btn-primary btn-lg btn-block">Add New Pattern</button>
								</div>
								
		
							</div>
								

							</div>
						</div>

					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseTwo">Video Tuitorial</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<iframe width="854" height="480"
									src="https://www.youtube.com/embed/BJUvDz6xyPo" frameborder="0"
									allowfullscreen></iframe>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!--End Collapsible Accordion Panel Group   -->
	</div>
</div>


<style>
#box-dragable {
	width: 100%;
	background: #eee;
	margin: 10px auto;
	height: auto;
}

#buttonadd {
	margin: 10px auto;
	padding: 10px;
}

#droptarget {
	width: 100%;
	height: 70px;
	padding: 10px;
	margin: 0 auto;
	cursor: default;
	border: 1px solid #999;
}

.draggable {
	cursor: move;
	width: auto;
	height: 30px;
	background-color: #03a9f4;
	text-align: center;
	border-radius: 30%;
	display: inline-block;
	margin: 5px;
	box-shadow: 0 3px 10px rgba(0, 0, 0, 0.23), 0 3px 10px
		rgba(0, 0, 0, 0.16);
}

.painted {
	background-color: #03a9f4;
	color: #fff;
}

.draggable.hollow {
	cursor: default;
	background: #ececec;
}
</style>
