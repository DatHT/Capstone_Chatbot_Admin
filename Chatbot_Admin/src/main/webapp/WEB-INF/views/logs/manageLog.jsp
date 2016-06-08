<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/logScript.js"></script>
<div class="row">
	<!--  page header -->
	<div class="col-lg-12">
		<h1 class="page-header">Logs Manager</h1>
	</div>
	<!-- end  page header -->
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

				for (var int = 0; int < listContent.length; int++) {
					if (listContent[int].errCode == '300') {
						createRowNoEntry("no-entry-table-body",
								listContent[int]);
					} else if (listContent[int].errCode == '404') {
						createRowNotFound("not-found-table-body",
								listContent[int].contexts);
					}
				}
			}
		};
		xmlhttp.open('GET', '/chatbot_admin/getLog', true);
		xmlhttp.send(null);
	</script>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="panel panel-default">
			<div class="panel-heading">No entry found</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>User say</th>
							</tr>
						</thead>
						<tbody id="no-entry-table-body">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<div class="col-lg-6">
		<!--    Hover Rows  -->
		<div class="panel panel-default">
			<div class="panel-heading">Misunderstand</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Food</th>
								<th>Location</th>
							</tr>
						</thead>
						<tbody id="not-found-table-body">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
</div>
