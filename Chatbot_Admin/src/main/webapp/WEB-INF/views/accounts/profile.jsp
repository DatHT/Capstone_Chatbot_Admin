<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="user" value="${USER}" />
<script>
	var user = '${user}';
</script>
<script src="resources/assets/scripts/accountScript.js"></script>

<section id="content">
	<div class="container">
		<div class="c-header">
			<h2>${user.username}</h2>
		</div>

		<div class="card" id="profile-main">
			<div class="pm-overview c-overflow">
				<div class="pmo-pic">
					<div class="p-relative">
						<a href="#"> <img class="img-responsive"
							src="resources/assets/img/profile-pics/default-ava.png" alt="">
						</a>
					</div>
				</div>

				<div class="pmo-block pmo-contact hidden-xs">
					<h2>Contact</h2>

					<ul>
						<c:if test="${not empty user.phone}">
							<li><i class="zmdi zmdi-phone"></i>${user.phone}</li>
						</c:if>
						<c:if test="${not empty user.email}">
							<li><i class="zmdi zmdi-email"></i> ${user.email}</li>
						</c:if>
						<c:if test="${not empty user.address}">
							<li><i class="zmdi zmdi-pin"></i>
								<address class="m-b-0 ng-binding">${user.address}</address></li>
						</c:if>
					</ul>
				</div>
			</div>

			<div class="pm-body clearfix">
				<ul class="tab-nav tn-justified">
					<li class="active waves-effect"><a href="#">About</a></li>
				</ul>


				<div class="pmb-block">
					<div class="pmbb-header">
						<h2>
							<i class="zmdi zmdi-equalizer m-r-5"></i> Summary
						</h2>

						<ul class="actions">
							<li class="dropdown"><a href="#" data-toggle="dropdown">
									<i class="zmdi zmdi-more-vert"></i>
							</a>

								<ul class="dropdown-menu dropdown-menu-right">
									<li><a data-pmb-action="edit" href="#">Edit</a></li>
								</ul></li>
						</ul>
					</div>
					<div class="pmbb-body p-l-30">
						<div class="pmbb-view">Sed eu est vulputate, fringilla
							ligula ac, maximus arcu. Donec sed felis vel magna mattis ornare
							ut non turpis. Sed id arcu elit. Sed nec sagittis tortor. Mauris
							ante urna, ornare sit amet mollis eu, aliquet ac ligula. Nullam
							dolor metus, suscipit ac imperdiet nec, consectetur sed ex. Sed
							cursus porttitor leo.</div>

						<div class="pmbb-edit">
							<div class="fg-line">
								<textarea class="form-control" rows="5" placeholder="Summary...">Sed eu est vulputate, fringilla ligula ac, maximus arcu. Donec sed felis vel magna mattis ornare ut non turpis. Sed id arcu elit. Sed nec sagittis tortor. Mauris ante urna, ornare sit amet mollis eu, aliquet ac ligula. Nullam dolor metus, suscipit ac imperdiet nec, consectetur sed ex. Sed cursus porttitor leo.</textarea>
							</div>
							<div class="m-t-10">
								<button class="btn btn-primary btn-sm">Save</button>
								<button data-pmb-action="reset" class="btn btn-link btn-sm">Cancel</button>
							</div>
						</div>
					</div>
				</div>
				<div class="pmb-block">
					<div class="pmbb-header">
						<h2>
							<i class="zmdi zmdi-phone m-r-5"></i> Change password
						</h2>
					</div>
					<div class="pmbb-body p-l-30">
						<form action="changePassword" method="post"
							onsubmit="return checkConfirmPassword()">
							<div class="pmbb-view">
								<dl class="dl-horizontal">
									<dt class="p-t-10">Old password</dt>
									<dd>
										<input id="password" name="password" type="password"
											class="form-control" />
									</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt class="p-t-10">New password</dt>
									<dd>
										<input id="new_password" name="new_password" type="password"
											class="form-control" />
									</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt class="p-t-10">Confirm password</dt>
									<dd>
										<input id="confirm_password" name="confirm_password"
											type="password" class="form-control" />
									</dd>
								</dl>
								<div class="m-t-30">
									<button class="btn btn-primary" style="float: right;">Save</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="pmb-block">
					<c:if test="${not empty user}">
						<div class="pmbb-header">
							<h2>
								<i class="zmdi zmdi-phone m-r-5"></i> Contact Information
							</h2>

							<ul class="actions">
								<li class="dropdown"><a href="#" data-toggle="dropdown">
										<i class="zmdi zmdi-more-vert"></i>
								</a>

									<ul class="dropdown-menu dropdown-menu-right">
										<li><a data-pmb-action="edit" href="#">Edit</a></li>
									</ul></li>
							</ul>
						</div>
						<div class="pmbb-body p-l-30">
							<div class="pmbb-view">
								<dl class="dl-horizontal">
									<dt>Mobile Phone</dt>
									<dd>${user.phone}</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt>Email Address</dt>
									<dd>${user.email}</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt>Address</dt>
									<dd>${user.address}</dd>
								</dl>
							</div>

							<div class="pmbb-edit">
								<dl class="dl-horizontal">
									<dt class="p-t-10">Mobile Phone</dt>
									<dd>
										<div class="fg-line">
											<c:if test="${not empty user.phone }">
												<input type="text" class="form-control"
													placeholder="eg. ${user.phone}">
											</c:if>
											<c:if test="${empty user.phone }">
												<input type="text" class="form-control"
													placeholder="eg. 01234567890">
											</c:if>
										</div>
									</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt class="p-t-10">Email Address</dt>
									<dd>
										<div class="fg-line">
											<c:if test="${not empty user.email }">
												<input type="text" class="form-control"
													placeholder="eg. ${user.email}">
											</c:if>
											<c:if test="${empty user.email }">
												<input type="text" class="form-control"
													placeholder="eg. abc@abc.com">
											</c:if>
										</div>
									</dd>
								</dl>
								<dl class="dl-horizontal">
									<dt class="p-t-10">Address</dt>
									<dd>
										<div class="fg-line">
											<c:if test="${not empty user.address }">
												<input type="text" class="form-control"
													placeholder="eg. ${user.address}">
											</c:if>
											<c:if test="${empty user.address }">
												<input type="text" class="form-control"
													placeholder="eg. 123 abc, bdf efg">
											</c:if>
										</div>
									</dd>
								</dl>
								<div class="m-t-30">
									<button class="btn btn-primary btn-sm">Save</button>
									<button data-pmb-action="reset" class="btn btn-link btn-sm">Cancel</button>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</section>