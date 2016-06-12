<%-- 
    Document   : index
    Created on : Jan 14, 2016, 11:22:32 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get element Xpath of Selected Items</title>
        <link href="cssCode/runnable.css" rel='stylesheet' type='text/css'>
        <link href="cssCode/popup.css" rel='stylesheet' type='text/css'>
        <script src="Scripts/jquery-2.2.0.js" type="text/javascript"></script>
        <script src="Scripts/parseManually.js" type="text/javascript"></script>
        <link rel="shortcut icon" href="img/ico/tripleh.png">

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
        <%
            String url = (String) session.getAttribute("URL");
        %>
        <section>
            <!-- sidebar left start-->
            <div class="sidebar-left">
                <!--responsive view logo start-->
                <div class="logo dark-logo-bg">
                    <a href="homepage.jsp">

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
                <div class="page-head">
                    <h3>
                        Page: <%= url%> 
                    </h3>
                    <div class="state-information">
                        <ol class="breadcrumb m-b-less bg-less">
                            <li><a href="#">Parser Configuration</a></li>
                            <li class="active">Parse Manually</li>
                        </ol>
                    </div>
                </div>
                <!-- page head end-->               
                <div class="wrapper">                    
                    <div class="row">
                        <div class="col-lg-12">
                            <section class="panel panel-primary">
                                <header class="panel-heading header-customize-color">
                                    <label class="header-customize-font">
                                        Please Select An Element And Get XPath
                                    </label>
                                </header>                                
                                <div class="panel-body">
                                    <form name="myForm" id="myForm" action="InsertServlet" method="POST">
                                        <div class="form-group">
                                            <div class="progressRecipe">
                                                <div class="circle done">
                                                    <span class="labelRecipe">0</span>
                                                    <span class="title">Welcome</span>
                                                </div>
                                                <span class="bar half"></span>
                                                <div class="circle active">
                                                    <span class="labelRecipe">1</span>
                                                    <span class="title">Name</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">2</span>
                                                    <span class="title">Address</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">3</span>
                                                    <span class="title">ProfilePicture</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">4</span>
                                                    <span class="title">Price</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">5</span>
                                                    <span class="title">Time</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">6</span>
                                                    <span class="title">Rate</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">7</span>
                                                    <span class="title">UserRate</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">8</span>
                                                    <span class="title">Gallery</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">9</span>
                                                    <span class="title">Map</span>
                                                </div>
                                            </div>
                                            <div class="panel col-sm-12">
                                                <div class="panel-body col-sm-2 header-customize-color header-customize-font">
                                                     Food Name                                                   
                                                </div>
                                            <div class="col-sm-8">
                                                 <input class="text-danger" style="width: 500px; height: 50px"type="text" name="txtFoodName" value="" placeholder="   please insert food's name"/>
                                            </div>
                                        </div>
                                        <input type="button" class="btn btn-send m-b-10" value="BACK" onclick="back()"/>
                                        <input type="button" class="btn btn-primary m-b-10" id="btnNext" value="NEXT" onclick="next()"/>
                                        <!--disable-->
                                        <input type="button" class="btn btn-primary m-b-10" id="btnPreview" value="PREVIEW" onclick="openpopup('popup')" disabled/>
                                        <input type="submit" class="btn btn-success m-b-10" id="btnAdd" value="INSERT" onclick="addNew()" disabled/>

                                        <input type="button" class="btn btn-primary m-b-10" value="HOME" onclick="window.location = 'welcome.jsp'"/>
                                        <div class="table-responsive" style="min-height: 70px">

                                            <table class="table" id="tbItems" border="1" width="550">
                                                <th>Content</th>
                                            </table>
                                            <div id="showXPath"></div>
                                            <br/>
                                        </div>
                                    </div>
                                    <div id="popup" class="popup"></div>
                                    <div id="bg" class="popup_bg"></div> 

                                    <div class="col-sm-12">
                                        <iframe style="width: 100%" sandbox="allow-same-origin allow-scripts allow-popups allow-forms" width="700" height="500" 
                                                id="myframe" src="tmp.html">
                                        </iframe>
                                    </div>
                                    <table id="tbMain">

                                    </table>
                                </form><br/>
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
