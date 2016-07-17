<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/assets/configscripts/loadConfigs.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
	<input type="hidden" value="${_csrf.token}" id="token" />
	<script>
		regObjCfg = "${sessionScope.INFOCONFIG}";
		regObjPage = "${sessionScope.INFOPAGE}";
	</script>
	<div class="c-header">
		<h2>Crawler Manger</h2>
	</div>
	<div class="card">
		<div class="form-horizontal">
			<div class="card-header">
				<h2>Configuration</h2>
			</div>
			<div class="card-body card-padding">
				<form:form action="setListPage" method="post" id="form"
					onsubmit="return checkInputConfig()">
					<div class="card-body card-padding">
						<div class="form-group">
							<label for="input-01">URL of input page (required):</label> <input
								type="url" class="form-control" size="45" name="txtURL"
								id="input-01" required />
						</div>
						<div class="form-group">
							<button type="submit" id="confirmation" class="btn btn-primary"
								value="Set List Page" name="btnAction" onclick="getVal(this.value)">Set
								List Page</button>
						</div>
					</div>
					
				</form:form>
			</div>
		</div>
		<!-- End  Hover Rows  -->
	</div>
	<!-- Placed js at the end of the document so the pages load faster -->
</body>
</html>

