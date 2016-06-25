<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<section id="content">
	<div class="container">
		<div class="c-header">
			<h2>
				Malinda Hollaway <small>Web/UI Developer, Edinburgh,
					Scotland</small>
			</h2>
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
						<li><i class="zmdi zmdi-phone"></i> 00971 12345678 9</li>
						<li><i class="zmdi zmdi-email"></i> malinda-h@gmail.com</li>
						<li><i class="zmdi zmdi-pin"></i>
							<address class="m-b-0 ng-binding">
								44-46 Morningside Road,<br> Edinburgh,<br> Scotland
							</address></li>
					</ul>
				</div>
			</div>

			<div class="pm-body clearfix">
				<ul class="tab-nav tn-justified">
					<li class="active waves-effect"><a href="profile-about.html">About</a></li>
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
								<dd>00971 12345678 9</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>Email Address</dt>
								<dd>malinda.h@gmail.com</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>Twitter</dt>
								<dd>@malinda</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>Skype</dt>
								<dd>malinda.hollaway</dd>
							</dl>
						</div>

						<div class="pmbb-edit">
							<dl class="dl-horizontal">
								<dt class="p-t-10">Mobile Phone</dt>
								<dd>
									<div class="fg-line">
										<input type="text" class="form-control"
											placeholder="eg. 00971 12345678 9">
									</div>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt class="p-t-10">Email Address</dt>
								<dd>
									<div class="fg-line">
										<input type="email" class="form-control"
											placeholder="eg. malinda.h@gmail.com">
									</div>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt class="p-t-10">Twitter</dt>
								<dd>
									<div class="fg-line">
										<input type="text" class="form-control"
											placeholder="eg. @malinda">
									</div>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt class="p-t-10">Skype</dt>
								<dd>
									<div class="fg-line">
										<input type="text" class="form-control"
											placeholder="eg. malinda.hollaway">
									</div>
								</dd>
							</dl>

							<div class="m-t-30">
								<button class="btn btn-primary btn-sm">Save</button>
								<button data-pmb-action="reset" class="btn btn-link btn-sm">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>