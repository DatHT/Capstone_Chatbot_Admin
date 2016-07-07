<%-- 
    Document   : manualAddFood
    Created on : May 24, 2016, 8:14:30 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body class="c-header">
	<div class="c-header">
		<h2>Crawler Manager</h2>
	</div>
	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-lg-12 m-b-15">
					<div class="panel-body btn-gap">
						<h2>
							<font color="red">${sessionScope.addSuccess}</font>
						</h2>
						<a href="configData" style="text-decoration: none; float: left;">
							<button style="margin-left: 350px" type="button"
								class="btn btn-primary btn-lg waves-effect" value="SetUp"
								name="btnAction">Parser Config</button>
						</a> <a href="manualAddFood"
							style="text-decoration: none; float: left;">
							<button style="margin-left: 150px" type="button"
								class="btn btn-primary btn-lg waves-effect">Manual Add
								Food</button>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
