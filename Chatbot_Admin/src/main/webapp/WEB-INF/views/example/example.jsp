<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/commonScript.js"></script>
<script src="resources/assets/scripts/exampleScript.js"></script>
<script src="resources/assets/scripts/logScript.js"></script>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />
<c:set var="logs" value="${LOGS}" />

<div class="c-header">
	<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
	<input type="hidden" value="${_csrf.token}" id="token" />
	<h2>Training Bot By Example</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
	</ul>
</div>

<div class="row">
	<div class="col-lg-7">

		<div class="card">
			<div class="card-header">
				<h2>Explaination</h2>
			</div>
			<div class="card-body card-padding">
				<p>An intent represents a mapping between what a user says and
					what action should be taken by your software.</p>
				<p>Example here is a "User say". Examples are written in natural
					language and annotated so that parameter values can be extracted</p>
			</div>
		</div>
	</div>
</div>



<div class="row">
	<div class="col-lg-7" id="step1">

		<div class="card">
			<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 1: Select Intent</h2>
				<button onclick="displayStep2()" class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float"><i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i></button>
			</div>
			
			<div class="card-body card-padding">

				<div class="row">
						<select class="chosen" data-placeholder="Choose a Intent..."
							onchange="loadIntent(this)" class="form-control"
							id="selectIntent">
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


	<div class="col-lg-7" style="display:none" id="step2">
		<div class="card">
			<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 2: Choose example</h2>
				<button onclick="displayStep3()" class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float"><i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i></button>
			</div>
			<div class="card-body card-padding">
						<select class="chosen" data-placeholder="Some suggestion example..."
							onchange="pitchExample(this)" class="form-control"
							id="exampleList">
							<option value="empty"></option>
							<c:if test="${not empty logs}">
								<c:forEach var="log" items="${logs}">
									<c:if test="${not log.delete}">
										<option value="${log.train}">${log.train}</option>
									</c:if>
									
								</c:forEach>
							</c:if>
						</select>
			</div>
		</div>
	</div>
	<div class="col-lg-7" style="display:none" id="step3">
		<div class="card">
		<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 3: Start Training</h2>
				<button onclick="insertPattern()" class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float"><i class="zmdi zmdi-plus"></i></button>
			</div>
			<div class="card-body card-padding">
				<div id="user-say-container">
					<blockquote class="m-b-25" id="chosenExample">
            
					</blockquote>	
				</div>
			
			<div class="card">
			<div class="card-body card-padding" id="containerDiv">
				
			</div>
			</div>
			<div class="card">
				<div class="card-body card-padding">
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
				<table class="table table-striped table-bordered table-hover"
					id="tableIntent">
					<thead>
						<tr>
							<th  data-column-id="id" data-type="numeric" data-identifier="true">No.</th>
							<th data-column-id="name">Name</th>
							<th  data-column-id="update" data-formatter="commands" data-sortable="false">Action</th>
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
	padding: 10px;
	margin-right:2px;
	cursor: default;
	border: 1px solid #999;
	display: inline-block;
}

.draggable {
	cursor: move;
	width: auto;
	height: 30px;
	background-color: #03a9f4;
	text-align: center;
	border-radius: 0%;
	color: white;
	padding: 5px;
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
<!-- Modals-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete Product</h4>
            </div>
            <div class="modal-body" style="border-bottom: 0px">
                <div>
                    <h4>Are you sure to delete this pattern <strong id="deletePatternName"></strong> ?</h4>
                </div>
            </div>
            <div class="modal-footer">
                    <button type="button" class="btn btn-success"
                            data-dismiss="modal">Cancel
                    </button>

                    <button type="submit" class="btn btn-danger"
                            onclick="deletePattern()">
                        Delete
                    </button>
            </div>
        </div>
    </div>
</div>
<!-- End modal -->