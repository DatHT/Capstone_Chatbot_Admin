<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/lexicalScript.js"></script>
<script src="resources/assets/scripts/commonScript.js"></script>
<c:set var="lexicals" value="${LEXICAL}" />
<div class="c-header">
	<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
	<input type="hidden" value="${_csrf.token}" id="token" />
	<h2>Manage Lexical And Pharse</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
	</ul>
</div>

<div class="card">
	<div class="card-header">
		<h2>Lexical Category</h2>
	</div>

	<div class="card-body card-padding">

		<div class="row">
			<div class="col-sm-3 m-b-15">

				<select class="chosen"
					data-placeholder="Choose a Lexical Category..."
					onchange="loadPharse(this)" id="selectCategory">
					<option value=""></option>

					<c:if test="${not empty lexicals}">
						<c:forEach var="lexical" items="${lexicals}">
							<option value="${lexical.id}">${lexical.name}</option>
						</c:forEach>
					</c:if>

				</select>



			</div>

		</div>

	</div>
</div>

<div class="card">
	<div class="card-header">
		<h2 id="tableHeader">Data table</h2>
	</div>

	<div class="card-body card-padding">
		
		<button class="btn btn-primary btn-lg waves-effect"
			onclick="insertRowToAddNewPhrase('lexicalTable')">Add New</button>
		<div class="row m-t-20">
		<!-- Advanced Tables -->
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered" id="phraseTable">
						<thead>
							<tr>
								<th data-column-id="id" data-type="numeric" data-identifier="true">No.</th>
								<th data-column-id="name">Name</th>
								<th data-column-id="delete" data-formatter="commandsDelete" data-sortable="false">Delete</th>
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
</div>

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
                    <h4>Are you sure to delete <strong id="deletePhraseName"></strong> ?</h4>
                </div>
            </div>
            <div class="modal-footer">
                    <button type="button" class="btn btn-success"
                            data-dismiss="modal">Cancel
                    </button>

                    <button type="submit" class="btn btn-danger"
                            onclick="deletePharse()">
                        Delete
                    </button>
            </div>
        </div>
    </div>
</div>
<!-- End modal -->