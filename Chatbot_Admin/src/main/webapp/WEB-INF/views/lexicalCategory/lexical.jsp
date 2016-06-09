<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/lexicalScript.js"></script>
<c:set var="lexicals" value="${LEXICAL}" />
<div class="row">

	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Manage Lexical And Pharse</h1>
	</div>
	<!-- end  page header -->
</div>
<div class="row">
	<div class="col-lg-3">

		<form role="form">
			<div class="form-group">
				<div class="form-group">
					<label>Lexical Category</label> <select class="form-control"
						onchange="loadPharse(this)" id="selectCategory">
						<option value="">----Please select----</option>
						<c:forEach var="lexical" items="${lexicals}">
							<option value="${lexical.id}">${lexical.name}</option>
						</c:forEach>


					</select>
				</div>
			</div>

		</form>

		<button class="btn btn-primary btn-lg"
			onclick="insertRowToAddNewPhrase('lexicalTable')">Add</button>
	</div>
	<br />

</div>
<div class="row">
	<div class="col-lg-12">
		<!-- Advanced Tables -->
		<div class="panel panel-default">
			<div class="panel-heading" id="tableHeader">Tables</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" id="phraseTable">
						<thead>
							<tr>
								<th>No.</th>
								<th>Name</th>
								<th>Action</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="lexicalTable">

						</tbody>
					</table>
				</div>

			</div>
		</div>
		<!--End Advanced Tables -->
		
	</div>
</div>
