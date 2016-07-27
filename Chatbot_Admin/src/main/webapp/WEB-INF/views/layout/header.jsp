<div id="page-header" class="container-fluid media header-height">

    <div class="pull-left h-logo">
        <a href="index.html" class="hidden-xs header-left-text"> PSIB</a>
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
		<li class="dropdown hm-profile"><a data-toggle="dropdown"
			href="#"> <img src="resources/assets/img/profile-pics/default-ava.png"
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
    <div class="my-h-logo">
        <center class="header-center-text hidden-xs">
            Place Suggestion Intelligent Bot
        </center>
        <div class="menu-collapse">

        </div>
    </div>


</div>

<form id="logoutForm" name="logoutForm" class="form-horizontal"
      role="form"
      action="${pageContext.request.contextPath}/j_spring_security_logout"
      method="POST">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
