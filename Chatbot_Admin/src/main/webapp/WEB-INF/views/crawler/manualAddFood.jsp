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
        <title>Manual Add New Food</title>
        <link href="cssCode/runnable.css" rel='stylesheet' type='text/css'>
        <link href="cssCode/popup.css" rel='stylesheet' type='text/css'>
        <script src="Scripts/jquery-2.2.0.js" type="text/javascript"></script>

        <!--right slidebar-->
        <link href="css/slidebars.css" rel="stylesheet">

        <!--switchery-->
        <link href="js/switchery/switchery.min.css" rel="stylesheet" type="text/css" media="screen" />

        <!--jquery-ui-->
        <link href="js/jquery-ui/jquery-ui-1.10.1.custom.min.css" rel="stylesheet" />

        <!--iCheck-->
        <link href="js/icheck/skins/all.css" rel="stylesheet">

        <link href="css/owl.carousel.css" rel="stylesheet">


        <!--common style-->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/style-responsive.css" rel="stylesheet">

        <link href="cssCode/customize.css" rel="stylesheet">
    </head>
    <body class="sticky-header">
        <section>
            <!-- sidebar left start-->
            <div class="sidebar-left">
                <!--responsive view logo start-->
                <div class="logo dark-logo-bg">
                    <a href="homepage.jsp">

                        <!--<i class="fa fa-maxcdn"></i>-->
                        <span class="brand-name">SUNGU</span>
                    </a>
                </div>
                <!--responsive view logo end-->

                <div class="sidebar-left-info">
                    <!-- visible small devices start-->
                    <div class=" search-field">  </div>
                    <!-- visible small devices end-->

                    <!--sidebar nav start-->
                    <ul class="nav nav-pills nav-stacked side-navigation">
                        <li>
                            <h3 class="navigation-title">Navigation</h3>
                        </li>
                        <li class="active"><a href="homepage.jsp"><i class="fa fa-home"></i> <span>HomePage</span></a></li>
                        <li>
                            <h3 class="navigation-title">Components</h3>
                        </li>
                        <li class="menu-list"><a href=""><i class="fa fa-cogs"></i> <span>Configuration </span></a>
                            <ul class="child-list">
                                <li><a href="ProcessServlet?btnAction=SetUp"> Parser Configuration</a></li>
                                <li><a href="ProcessServlet?btnAction=Scheduler"> Scheduler Configuration</a></li>
                            </ul>
                        </li>

                    </ul>
                    <!--sidebar nav end-->

                    <!--sidebar widget start-->
                    <div class="sidebar-widget">
                        <h4>Server Status</h4>
                        <ul class="list-group">
                            <li>
                                <span class="label label-danger pull-right">33%</span>
                                <p>CPU Used</p>
                                <div class="progress progress-xs">
                                    <div class="progress-bar progress-bar-danger" style="width: 33%;">
                                        <span class="sr-only">33%</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <span class="label label-warning pull-right">65%</span>
                                <p>Bandwidth</p>
                                <div class="progress progress-xs">
                                    <div class="progress-bar progress-bar-warning" style="width: 65%;">
                                        <span class="sr-only">65%</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!--sidebar widget end-->

                </div>
            </div>
            <!-- sidebar left end-->

            <!-- body content start-->
            <div class="body-content" >
                <!-- header section start-->
                <div class="header-section">

                    <!--logo and logo icon start-->
                    <div class="logo dark-logo-bg hidden-xs hidden-sm">
                        <a href="homepage.jsp">
                            <span class="brand-name">SUNGU</span>
                        </a>
                    </div>

                    <div class="icon-logo dark-logo-bg hidden-xs hidden-sm">
                        <a href="homepage.jsp">
                            <img src="img/tripleh.png" alt="">
                            <!--<i class="fa fa-maxcdn"></i>-->
                        </a>
                    </div>
                    <!--logo and logo icon end-->

                    <!--toggle button start-->
                    <a class="toggle-btn"><i class="fa fa-outdent"></i></a>
                    <!--toggle button end-->

                    <div class="notification-wrap">
                        <!--left notification start-->
                        <div class="left-notification">
                            <ul class="notification-menu">
                                <!--notification info start-->
                                <li>
                                    <a href="javascript:;" class="btn btn-default dropdown-toggle info-number" data-toggle="dropdown">
                                        <i class="fa fa-bell-o"></i>
                                        <span class="badge bg-warning">4</span>
                                    </a>

                                    <div class="dropdown-menu dropdown-title ">

                                        <div class="title-row">
                                            <h5 class="title yellow">
                                                You have 4 New Notification
                                            </h5>
                                            <a href="javascript:;" class="btn-warning btn-view-all">View all</a>
                                        </div>
                                        <div class="notification-list-scroll sidebar">
                                            <div class="notification-list mail-list not-list">
                                                <a href="javascript:;" class="single-mail">
                                                    <span class="icon bg-primary">
                                                        <i class="fa fa-envelope-o"></i>
                                                    </span>
                                                    <strong>New User Registration</strong>

                                                    <p>
                                                        <small>Just Now</small>
                                                    </p>
                                                    <span class="un-read tooltips" data-original-title="Mark as Read" data-toggle="tooltip" data-placement="left">
                                                        <i class="fa fa-circle"></i>
                                                    </span>
                                                </a>
                                                <a href="javascript:;" class="single-mail">
                                                    <span class="icon bg-success">
                                                        <i class="fa fa-comments-o"></i>
                                                    </span>
                                                    <strong> Private message Send</strong>

                                                    <p>
                                                        <small>30 Mins Ago</small>
                                                    </p>
                                                    <span class="un-read tooltips" data-original-title="Mark as Read" data-toggle="tooltip" data-placement="left">
                                                        <i class="fa fa-circle"></i>
                                                    </span>
                                                </a>
                                                <a href="javascript:;" class="single-mail">
                                                    <span class="icon bg-warning">
                                                        <i class="fa fa-warning"></i>
                                                    </span> Application Error
                                                    <p>
                                                        <small> 2 Days Ago</small>
                                                    </p>
                                                    <span class="read tooltips" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="left">
                                                        <i class="fa fa-circle-o"></i>
                                                    </span>
                                                </a>
                                                <a href="javascript:;" class="single-mail">
                                                    <span class="icon bg-dark">
                                                        <i class="fa fa-database"></i>
                                                    </span> Database Overloaded 24%
                                                    <p>
                                                        <small>1 Week Ago</small>
                                                    </p>
                                                    <span class="read tooltips" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="left">
                                                        <i class="fa fa-circle-o"></i>
                                                    </span>
                                                </a>
                                                <a href="javascript:;" class="single-mail">
                                                    <span class="icon bg-danger">
                                                        <i class="fa fa-warning"></i>
                                                    </span>
                                                    <strong>Server Failed Notification</strong>

                                                    <p>
                                                        <small>10 Days Ago</small>
                                                    </p>
                                                    <span class="un-read tooltips" data-original-title="Mark as Read" data-toggle="tooltip" data-placement="left">
                                                        <i class="fa fa-circle"></i>
                                                    </span>
                                                </a>

                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <!--notification info end-->
                            </ul>
                        </div>
                        <!--left notification end-->


                        <!--right notification start-->
                        <div class="right-notification">
                            <ul class="notification-menu">

                                <li>
                                    <a href="javascript:;" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <span class=" fa fa-angle-down"></span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-usermenu purple pull-right">

                                        <li><a href="LogoutServlet"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="sb-toggle-right">
                                        <i class="fa fa-indent"></i>
                                    </div>
                                </li>

                            </ul>
                        </div>
                        <!--right notification end-->
                    </div>

                </div>
                <!-- header section end-->
                <!-- page head start-->

                <!-- page head end-->               
                <div class="wrapper">                    
                    <div class="row">
                        <div class="col-lg-12">
                            <section class="panel">
                                <header class="panel-heading header-customize-color">
                                    <label class="header-customize-font">
                                        Please Fill All Text Box To Add New Food
                                    </label>
                                </header>                                
                                <div class="panel-body">
                                    <h2><font color="red">${sessionScope.addFail}</font></h2>
                                    <form action="ProcessServlet" method="POST">
                                        <table border="0" class="table table-hover tab-pane">
                                            <tbody>
                                                <tr>
                                                    <td>Food Name</td>
                                                    <td><input type="text" name="txtFoodName" value="" placeholder="Mì cay, cá sốt cà ..."/></td>
                                                </tr>
                                                <tr>
                                                    <td>Restaurant Name</td>
                                                    <td><input type="text" name="txtResName" value="" placeholder=" nhà hàng mặt trăng, quán cơm siêu nhân ..."/></td>
                                                </tr>
                                                <tr>
                                                    <td>Price</td>
                                                    <td><input type="text" name="txtMinPrice" value="" placeholder="min..."/> -   
                                                        <input type="text" name="txtMaxPrice" value="" placeholder="max..."/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td><input type="text" name="txtAddress" value="" placeholder="123 Tô Ký.."/></td>
                                                </tr>
                                                <tr>
                                                    <td>District</td>
                                                    <td><select name="txtDistrict">
                                                            <option></option>
                                                            <option>Quận 1</option>
                                                            <option>Quận 2</option>
                                                            <option>Quận 3</option>
                                                            <option>Quận 4</option>
                                                            <option>Quận 5</option>
                                                            <option>Quận 6</option>
                                                            <option>Quận 7</option>
                                                            <option>Quận 8</option>
                                                            <option>Quận 9</option>
                                                            <option>Quận 10</option>
                                                            <option>Quận 11</option>
                                                            <option>Quận 12</option>
                                                            <option>Quận Tân Bình</option>
                                                            <option>Quận Tân Phú</option>
                                                            <option>Quận Bình Tân</option>
                                                            <option>Quận Phú Nhuận</option>
                                                            <option>Quận Gò Vấp</option>
                                                            <option>Hóc Môn</option>
                                                            <option>Nhà Bè</option>
                                                            <option>Củ Chi</option>
                                                            <option>Quận Bình Thạnh</option>
                                                            <option>Quận Thủ Đức</option>
                                                            <option>Bình Chánh</option>
                                                            <option>Cần Giờ</option>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Province</td>
                                                    <td><select name="txtProvince">
                                                            <option> TP. HCM</option>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" value="AddNewFood" name="btnAction" class="btn btn-primary"/>
                                                        <input type="reset" value="Reset" class="btn btn-primary"/></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Placed js at the end of the document so the pages load faster -->
        <script src="js/jquery-1.10.2.min.js"></script>

        <!--jquery-ui-->
        <script src="js/jquery-ui/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>

        <script src="js/jquery-migrate.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/modernizr.min.js"></script>

        <!--Nice Scroll-->
        <script src="js/jquery.nicescroll.js" type="text/javascript"></script>

        <!--right slidebar-->
        <script src="js/slidebars.min.js"></script>

        <!--switchery-->
        <script src="js/switchery/switchery.min.js"></script>
        <script src="js/switchery/switchery-init.js"></script>

        <!--Icheck-->
        <script src="js/icheck/skins/icheck.min.js"></script>
        <script src="js/todo-init.js"></script>

        <!--jquery countTo-->
        <script src="js/jquery-countTo/jquery.countTo.js"  type="text/javascript"></script>

        <!--owl carousel-->
        <script src="js/owl.carousel.js"></script>


        <!--common scripts for all pages-->

        <script src="js/scripts.js"></script>


        <script type="text/javascript">

            $(document).ready(function () {

                //countTo

                $('.timer').countTo();

                //owl carousel

                $("#news-feed").owlCarousel({
                    navigation: true,
                    slideSpeed: 300,
                    paginationSpeed: 400,
                    singleItem: true,
                    autoPlay: true
                });
            });

            $(window).on("resize", function () {
                var owl = $("#news-feed").data("owlCarousel");
                owl.reinit();
            });

        </script>
    </body>
</html>
