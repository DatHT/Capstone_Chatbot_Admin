<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<title>Manage Products</title>
</head>

<style>
	.help-block{
		visibility: hidden;
	}
</style>
<script src="resources/assets/scripts/product.js"></script>
<script src="resources/assets/scripts/commonScript.js"></script>
<div class="c-header">

	<h2 id="tableHeader">Manage Products</h2>
</div>

<div class="card">
	<div class="card-body card-padding">
		<button class="btn btn-primary btn-lg waves-effect"
			onclick="showAddModal()">Add New</button>
		<div class="row m-t-20">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="table-responsive">
						<table id="data-table-basic" class="table table-striped">
							<thead>
								<tr>
									<th data-column-id="id" data-type="numeric"
										data-identifier="true">No.</th>
									<th data-column-id="name">Name</th>
									<th data-column-id="address">Address</th>
									<th data-column-id="district">District</th>
									<th data-column-id="rate">Rating</th>
									<th data-column-id="restaurant">Restaurant</th>
									<th data-column-id="thumb">Thumb</th>
									<th data-column-id="url">Related Url</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${productList}" var="product"
									varStatus="counter">
									<tr>
										<td>${counter.count}</td>
										<td>${product.productName}</td>
										<td>${product.addressName}</td>
										<td>${product.districtName}</td>
										<td>${product.rate}</td>
										<td>${product.restaurantName}</td>
										<td>${product.thumbPath}</td>
										<td>${product.urlRelate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Data Table -->
<script type="text/javascript">
	
	function showAddModal() {
		$('#myModal').modal('show');
	}

	$(document).ready(function() {
		var result = '${addResult}';
		
		if (result == 'true') {
			notify("Add Product Successfully!", "info");
		} else if (result == 'false') {
			notify("Product Already Existed!", "error");
			showAddModal();
		}
		
		$('#myModal').on('hidden.bs.modal', function () {
			$("#name").val("");
			$("#address").val("");
			$("#district").find('option:eq(0)').prop('selected', true);
			$("#rating").val("");
			$("#restaurant").val("");
			$("#relatedUrl").val("");
			
			$("#div-name").removeClass("has-error");
			$("#error-name").text("");
			$("#error-name").css("visibility", "hidden");
			
			$("#div-address").removeClass("has-error");
			$("#error-address").text("");
			$("#error-address").css("visibility", "hidden");
			
			$("#div-rating").removeClass("has-error");
			$("#error-rating").text("");
			$("#error-rating").css("visibility", "hidden");
			
			$("#div-restaurant").removeClass("has-error");
			$("#error-restaurant").text("");
			$("#error-restaurant").css("visibility", "hidden");
			
			$("#div-relatedUrl").removeClass("has-error");
			$("#error-relatedUrl").text("");
			$("#error-relatedUrl").css("visibility", "hidden");
		});
		
		//Basic Example
		$("#data-table-basic").bootgrid({
			ss : {
				icon : 'zmdi icon',
				iconColumns : 'zmdi-view-module',
				iconDown : 'zmdi-expand-more',
				iconRefresh : 'zmdi-refresh',
				iconUp : 'zmdi-expand-less'
			},
		});
	});
</script>

<!--  Modals-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div id="user-say-container" class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="user-say-in-modal">Add New Product</h4>
			</div>
			<form id="add-form" action="addProduct?${_csrf.parameterName}=${_csrf.token}" method="POST" enctype="multipart/form-data">
				<div class="modal-body" style="border-bottom: 0px">
					<div>
						<c:if test="${addResult != false}">
							<%--Name--%>
							<div id="div-name">
								<label>Name</label>
								<input id="name" name="name"
									autocomplete="off" class="form-control"
									onblur="validName()"
									onkeyup="validName()">
								<small id="error-name" class="help-block"></small>	
							</div>
							
							<%--Address--%>
							<div id="div-address">
								<label>Address</label>
								<input id="address" name="address"
									autocomplete="off" class="form-control"
									onblur="validAddress()"
									onkeyup="validAddress()">
								<small id="error-address" class="help-block"></small>
							</div>
							
							<%--District--%>
							<div id="div-district">
								<label>District</label><br> 
								<select id="district"
									name="district" class="form-control">
									<c:forEach items="${districtList}" var="district">
										<option value="${district.id}">${district.name}</option>
									</c:forEach>
								</select>
							</div>
							
							<%--Rating--%>
							<div id="div-rating">
								<label>Rating</label> 
								<input id="rating" name="rating"
									autocomplete="off" class="form-control"
									onblur="validRating()"
									onkeyup="validRating()">
								<small id="error-rating" class="help-block"></small>
							</div>
							
							<%--Restaurant--%>
							<div id="div-restaurant">
								<label>Restaurant</label>
								<input id="restaurant" name="restaurant"
									autocomplete="off" class="form-control"
									onblur="validRestaurant()"
									onkeyup="validRestaurant()">
								<small id="error-restaurant" class="help-block"></small>
							</div>
							
							<%--Related Url--%>
							<div id="div-relatedUrl">
								<label>Related Url</label> 
								<input id="relatedUrl"
									name="relatedUrl" autocomplete="off" class="form-control"
									onblur="validRelatedUrl()"
									onkeyup="validRelatedUrl()">
								<small id="error-relatedUrl" class="help-block"></small>
							</div>
							
							<%--Thumbnail--%>
							<label>Thumbnail</label><br>
							<input type="file" name="file"> 	
						</c:if>
						<c:if test="${addResult == false}">
							<c:remove var="result"/>
							
							<%--Name--%>
							<div id="div-name">
								<label>Name</label>
								<input id="name" name="name" value="${name}"
									autocomplete="off" class="form-control"
									onblur="validName()"
									onkeyup="validName()">
								<small id="error-name" class="help-block"></small>		
							</div>
							
							<%--Address--%>
							<div id="div-address">
								<label>Address</label>
								<input id="address" name="address" value="${address}"
									autocomplete="off" class="form-control"
									onblur="validAddress()"
									onkeyup="validAddress()">
								<small id="error-address" class="help-block"></small>
							</div>
							
							<%--District--%>
							<div id="div-district">
								<label>District</label><br>
								<select id="district"
									name="district" class="form-control">
									<c:forEach items="${districtList}" var="district">
										<c:if test="${districtId == district.id}">
											<option value="${district.id}" selected>${district.name}</option>
										</c:if>
										<c:if test="${districtId != district.id}">
											<option value="${district.id}">${district.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							
							<%--Rating--%>
							<div id="div-rating">
								<label>Rating</label> 
								<input id="rating" name="rating" value="${rating}"
									autocomplete="off" class="form-control"
									onblur="validRating()"
									onkeyup="validRating()">
								<small id="error-rating" class="help-block"></small>
							</div>
						
							<%--Restaurant--%>
							<div id="div-restaurant">
								<label>Restaurant</label> 
								<input id="restaurant" name="restaurant" value="${restaurant}"
									autocomplete="off" class="form-control"
									onblur="validRestaurant()"
									onkeyup="validRestaurant()">
								<small id="error-restaurant" class="help-block"></small>
							</div>
							
							<%--Related Url--%>
							<div id="div-relatedUrl">
								<label>Related Url</label> 
								<input id="relatedUrl" name="relatedUrl" value="${relatedUrl}" 
									autocomplete="off" class="form-control"
									onblur="validRelatedUrl()"
									onkeyup="validRelatedUrl()">
								<small id="error-relatedUrl" class="help-block"></small>		
							</div>
							
							<%--Thumbnail--%>
							<label>Thumbnail</label><br>
							<input type="file" name="file">
						</c:if>
						
							<br>
					</div>
				</div>

				<div class="modal-footer">
					<button id="cancel-button" type="button" class="btn btn-danger"
						data-dismiss="modal">Cancel</button>
					<button id="add-button" type="button" class="btn btn-success"
						onclick="validOnSubmit()">Add</button>
				</div>

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>
<!-- End modal -->