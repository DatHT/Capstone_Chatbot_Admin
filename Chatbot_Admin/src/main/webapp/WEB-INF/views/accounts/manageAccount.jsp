<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="STAFFS" value="${STAFFS}" />
<script src="resources/assets/scripts/accountScript.js"></script>
<div class="c-header">
	<h2>Manage Account</h2>
</div>
<div class="card">
	<div class="card-header">
		<h2>Manage Account</h2>
	</div>
	<div class="form-horizontal">
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
							id="inputAccount" placeholder="Account">
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input id="checkbox-isadmin" type="checkbox"
							name="isAdminChk"> <i class="input-helper"></i> is Admin
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input id="token" type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<button class="btn btn-primary btn-sm waves-effect"
						onclick="createNewAccount()">Create new account</button>
				</div>
			</div>
		</div>
	</div>
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
		<table id="user-data-table" class="table table-striped">
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
					<c:forEach var="staff" items="${STAFFS}">
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