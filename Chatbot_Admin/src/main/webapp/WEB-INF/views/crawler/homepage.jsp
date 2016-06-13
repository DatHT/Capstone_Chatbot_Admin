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
<title>Welcome</title>
</head>
<body class="sticky-header">
	<section>
            <!-- sidebar left start-->
            <div class="sidebar-left">
                <!--responsive view logo start-->
                <div class="logo dark-logo-bg">
                </div>
                <!--responsive view logo end-->

                <div class="sidebar-left-info">

                </div>
            </div>
            <!-- sidebar left end-->

            <!-- body content start-->
		<div class="body-content">
                <!-- header section start-->
                <div class="header-section">

                    <!--logo and logo icon start-->
                    <div class="logo dark-logo-bg hidden-xs hidden-sm">
                    </div>

                    <div class="icon-logo dark-logo-bg hidden-xs hidden-sm">
                    </div>
                    <!--logo and logo icon end-->

                    <!--toggle button start-->
                    <a class="toggle-btn"><i class="fa fa-outdent"></i></a>
                    <!--toggle button end-->

                    <div class="notification-wrap">
                    </div>

                </div>
                <!-- header section end-->
                <!-- page head start-->
                <div class="page-head">
                    </br>
                </div>
			<div class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<section class="panel panel-primary slider">
							<header class="panel-heading">
								<label class="panel-title header-customize-font">
									Welcome To Crawler Page </label>
							</header>
							<div class="panel-body btn-gap">
								<h2>
									<font color="red">${sessionScope.addSuccess}</font>
								</h2>
								<a href="configData" style="text-decoration: none">
									<button style="margin-left: 150px" type="button"
										class="btn btn-default btn-xl" value="SetUp" name="btnAction">
										Parser Config</button>
								</a> <a href="manualAddFood" style="text-decoration: none">
									<button style="margin-left: 150px" type="button"
										class="btn btn-default btn-xl">Manual Add Food</button>
								</a>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
