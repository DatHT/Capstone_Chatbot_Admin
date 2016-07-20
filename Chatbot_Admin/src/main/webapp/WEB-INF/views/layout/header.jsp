<header id="header" class="media">
	<div class="pull-left h-logo">
		<a href="index.html" class="hidden-xs"> PSIB <small>Place
				Suggestion Intelligent Bot</small>
		</a>

		<div class="menu-collapse" data-ma-action="sidebar-open"
			data-ma-target="main-menu">
			<div class="mc-wrap">
				<div class="mcw-line top palette-White bg"></div>
				<div class="mcw-line center palette-White bg"></div>
				<div class="mcw-line bottom palette-White bg"></div>
			</div>
		</div>
	</div>

	<ul class="pull-right h-menu">
		<li class="hm-search-trigger"><a href="#"
			data-ma-action="search-open"> <i class="hm-icon zmdi zmdi-search"></i>
		</a></li>
		<li class="hm-alerts" data-user-alert="sua-messages"
			data-ma-action="sidebar-open" data-ma-target="user-alerts"><a
			href="#"><i class="hm-icon zmdi zmdi-notifications"></i></a></li>
		<li class="dropdown hm-profile"><a data-toggle="dropdown"
			href="#"> <img src="resources/assets/img/profile-pics/1.jpg"
				alt="">
		</a>

			<ul class="dropdown-menu pull-right dm-icon">
				<li><a href="profile"><i
						class="zmdi zmdi-account"></i> View Profile</a></li>
				<li><a href="#"
					onclick="document.getElementById('logoutForm').submit()"><i
						class="zmdi zmdi-time-restore"></i> Logout</a></li>
			</ul></li>
	</ul>


</header>

<form id="logoutForm" name="logoutForm" class="form-horizontal"
	role="form"
	action="${pageContext.request.contextPath}/j_spring_security_logout"
	method="POST">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>
