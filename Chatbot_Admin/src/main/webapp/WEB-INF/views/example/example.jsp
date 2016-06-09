<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/lexicalScript.js"></script>
<div class="row">

	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Traing Bot By Example</h1>
	</div>
	<!-- end  page header -->


</div>

<div class="row">
	<div class="col-lg-3">

		<form role="form">
			<div class="form-group">
				<div class="form-group">
					<label>Intents</label> <select class="form-control"
						onchange="loadIntent(this)" id="selectIntent">
						<option value="">----Please select----</option>
					</select>
				</div>
			</div>

		</form>
	</div>

	<div class="col-lg-8">
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
								<input class="form-control" placeholder="Enter text">
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
							<iframe width="854" height="480" src="https://www.youtube.com/embed/BJUvDz6xyPo" frameborder="0" allowfullscreen></iframe>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
		<!--End Collapsible Accordion Panel Group   -->
	</div>
</div>