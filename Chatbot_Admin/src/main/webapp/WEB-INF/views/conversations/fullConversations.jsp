<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="dates" value="${DATES}" />
<div class="c-header">
	<h2>Conversations</h2>
</div>
<div class="card">
	<div class="card-header">
		<h2>
			Conversations <small>Here shows the conversations with PISB.</small>

		</h2>
	</div>

	<div class="card-body card-padding">
		<!-- <p class="c-black f-500 m-b-5">Accordion</p>
		<small>Extend the default collapse behavior to create an
			accordion with the panel component.</small> <br /> <br />
 -->
		<div class="row">
			<div class="col-sm-2 m-b-25">
				<p class="f-500 m-b-15 c-black">Choose date</p>
				<select class="selectpicker">
					<c:if test="${not empty dates}">
						<c:forEach var="date" items="${dates}">
							<option value="${date.value}">${date.name}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<div class="panel-group" role="tablist" aria-multiselectable="true">
			<div class="panel panel-collapse">
				<div class="panel-heading" role="tab" id="headingOne">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseOne" aria-expanded="true"
							aria-controls="collapseOne"> Collapsible Group Item #1 </a>
					</h4>
				</div>
				<div id="collapseOne" class="collapse in" role="tabpanel"
					aria-labelledby="headingOne">
					<div class="panel-body">
						<table class="table">
							<tr>
								<td>1</td>
								<td>Alexandra</td>
								<td>Christopher</td>
								<td>@makinton</td>
								<td>Ducky</td>
							</tr>
							<tr>
								<td>2</td>
								<td>Madeleine</td>
								<td>Hollaway</td>
								<td>@hollway</td>
								<td>Cheese</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>