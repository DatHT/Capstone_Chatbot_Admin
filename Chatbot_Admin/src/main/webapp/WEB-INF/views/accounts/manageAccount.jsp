<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/assets/scripts/accountScript.js"></script>

<c:set var="STAFFS" value="${STAFFS}" />
<div class="c-header">
	<h2>Manage Account</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
		<li class="dropdown"><a href="#" data-toggle="dropdown"> <i
				class="zmdi zmdi-more-vert"></i>
		</a>

			<ul class="dropdown-menu dropdown-menu-right">
				<li><a href="#">Refresh</a></li>
				<li><a href="#">Manage Widgets</a></li>
				<li><a href="#">Widgets Settings</a></li>
			</ul></li>
	</ul>
</div>
<div class="card">
	<div class="card-header">
		<h2>Manage Account</h2>
	</div>
	<form class="form-horizontal" role="form" action="addAccount" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card-header">
			<h2>
				Create new account <small>Use to create new account for
					staff to login into the system</small>
			</h2>
		</div>
		<div class="card-body card-padding">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">Account</label>
				<div class="col-sm-10">
					<div class="fg-line">
						<input type="text" name="username" class="form-control input-sm"
							id="inputAccount" placeholder="Account" onchange="checkIsExist()">
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<div class="fg-line">
						<input type="email" name="email" class="form-control input-sm"
							id="inputEmail3" placeholder="Email">
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input type="checkbox" name="isAdminChk" value="1">
							<i class="input-helper"></i> is Admin
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary btn-sm waves-effect">Create
						new account</button>
				</div>
			</div>
		</div>
	</form>
</div>
<div class="card">
	<div class="card-header">
		<h2>
			Basic Example <small>It's just that simple. Turn your simple
				table into a sophisticated data table and offer your users a nice
				experience and great features without any effort.</small>
		</h2>
	</div>

	<div class="table-responsive">
		<table id="data-table-basic" class="table table-striped">
			<thead>
				<tr>
					<th data-column-id="username" data-order="desc">User name</th>
					<th data-column-id="password">Password</th>
					<th data-column-id="email">Email</th>
					<th data-column-id="phone">Phone</th>
					<th data-column-id="address">Address</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty STAFFS}">
					<c:forEach var="staff" items="${STAFFS}" >
						<tr>
							<td>${staff.username}</td>
							<td>${staff.password}</td>
							<td>${staff.email}</td>
							<td>${staff.phone}</td>
							<td>${staff.address}</td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
		</table>
	</div>
</div>