<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/commonScript.js"></script>
<script src="resources/assets/scripts/exampleScript.js"></script>
<c:set var="intents" value="${INTENTS}" />
<c:set var="lexicals" value="${LEXICAL}" />

<div class="c-header">
	<h2>Training Bot By Example</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
	</ul>
</div>

<div class="row">
	<div class="col-lg-5">

		<div class="card">
			<div class="card-header">
				<h2>Intent</h2>
			</div>

			<div class="card-body card-padding">

				<div class="row">
						<select class="chosen" data-placeholder="Choose a Intent..."
							onchange="loadIntent(this)" class="form-control"
							id="selectIntent">
							<option value=""></option>

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
				var listExample = document.getElementById("exampleList");
				for (var int = 0; int < listContent.length; int++) {
					if (listContent[int].errCode == '300') {
						//alert(listContent[int].userSay);
						var option = document.createElement("option");
						option.text = listContent[int].userSay;
						option.value = listContent[int].userSay;
						listExample.add(option);
						
					}
				}
			}
		};
		xmlhttp.open('GET', '/chatbot_admin/getLog', true);
		xmlhttp.send(null);
	</script>
	<div class="col-lg-3">

		<div class="card">
			<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 1: Select Intent</h2>
				<button onclick="displayStep2()" class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float"><i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i></button>
			</div>
			
			<div class="card-body card-padding">

				<div class="row">
						<select class="chosen" data-placeholder="Choose a Intent..."
							onchange="loadIntent(this)" class="form-control"
							id="selectIntentS1">
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


	<div class="col-lg-3" style="display:none" id="step2">
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
							

						</select>
			</div>
		</div>
	</div>
	<div class="col-lg-6" style="display:none" id="step3">
		<div class="card">
		<div class="card-header ch-dark palette-Purple-300 bg">
				<h2>Step 3: Start Training</h2>
				<button class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float"><i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i></button>
			</div>
			<div class="card-body card-padding">
			<blockquote class="m-b-25" id="chosenExample">
            
			</blockquote>
			</div>
	</div>
	</div>

	
</div>


<div class="row">

	
	

	<div class="card">
			<div class="card-header">
				<h2>Manage Example</h2>
			</div>
			<div class="card-body card-padding">
				<!--Collapsible Accordion Panel Group   -->
		<div class="panel panel-default">
			
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
											class="btn btn-primary btn-lg btn-block">Add New
											Pattern</button>
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
		
	
</div>

<div class="row">

	<div class="col-lg-8">
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
	border-radius: 0%;
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
