<%-- 
    Document   : setPageList
    Created on : Feb 23, 2016, 10:21:54 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get element Xpath of Selected Items</title>
        <link href="<c:url value = "/resources/crawler/cssCode/runnable.css"/>" rel="stylesheet">
        <link href='<c:url value="/resources/crawler/cssCode/popup.css"/>' rel="stylesheet">
        <script src="<c:url value ="/resources/crawler/configscripts/jquery-2.2.0.js"/>"></script>
        <script src='<c:url value="/resources/crawler/configscripts/parseList.js" />'></script>
        
        <!--right slidebar-->
        <link href="<c:url value="/resources/crawler/cssboostrap/slidebars.css"/>" rel="stylesheet">

        <!--switchery-->
        <link href="<c:url value="/resources/crawler/js/switchery/switchery.min.css" />" rel="stylesheet" type="text/css" media="screen" />

        <!--jquery-ui-->
        <link href="<c:url value="/resources/crawler/js/jquery-ui/jquery-ui-1.10.1.custom.min.css" />" rel="stylesheet" />

        <!--iCheck-->
        <link href="<c:url value="/resources/crawler/js/icheck/skins/all.css" />" rel="stylesheet">

        <link href="<c:url value="/resources/crawler/cssboostrap/owl.carousel.css" />" rel="stylesheet">

        <!--common style-->
        <link href="<c:url value="/resources/crawler/css/style.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/crawler/css/style-responsive.css" />" rel="stylesheet">
        
        <link href="<c:url value="/resources/crawler/cssCode/customize.css" />" rel="stylesheet">
    </head>
    <body class="sticky-header">
        <c:set var="url" value="${sessionScope.URL}"/>
        
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
            <div class="body-content" >
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
                            <section class="panel">
                                <header class="panel-heading header-customize-color">
                                    <label class="header-customize-font">
                                        Please Select An Element And Get XPath
                                    </label>
                                </header>                            
                                <div class="panel-body">
                                    <form name="myForm" id="myForm" action="ProcessServlet" method="GET">
                                        <div class="form-group">
                                            <div class="progressRecipe">
                                                <div class="circle done">
                                                    <span class="labelRecipe">0</span>
                                                    <span class="title">Welcome</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle active">
                                                    <span class="labelRecipe">1</span>
                                                    <span class="title">Each Product</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">2</span>
                                                    <span class="title">Food Name</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">3</span>
                                                    <span class="title">Image</span>
                                                </div>
                                                <span class="bar"></span>
                                                <div class="circle">
                                                    <span class="labelRecipe">4</span>
                                                    <span class="title">Next Page</span>
                                                </div>
                                            </div>
                                            <input type="button" class="btn btn-info m-b-10" value="BACK" onclick="back()"/>
                                            <input type="button" class="btn btn-info m-b-10" id="btnNext" value="NEXT" onclick="next()"/>
                                            <!--disable-->
                                            <input type="button" class="btn btn-info m-b-10" id="btnPreview" value="PREVIEW" onclick="openpopup('popup')" disabled/>
                                            <input type="submit" class="btn btn-info m-b-10" id="btnAdd" name ="btnAction" value="AddNewPageList" onclick="addNew()" disabled/>

                                            <input type="button" class="btn btn-info m-b-10" value="HOME" onclick="window.location = 'welcome.jsp'"/>
                                            <div class="table-responsive" style="min-height: 70px">

                                                <table class="table" id="tbItems" border="1" width="619" class="table">
                                                    <th width="97%">Content</th>
                                                </table><br/>
                                                <div id="showXPath"></div>
                                                <br/>
                                            </div>
                                        </div>
                                        <div id="popup" class="popup"></div>
                                        <div id="bg" class="popup_bg"></div> 
                                        <div class="col-sm-12">
                                            <iframe style="width: 100%" sandbox="allow-same-origin allow-pointer-lock allow-scripts allow-popups allow-forms" width="700" height="500" 
                                                    id="myframe" src="<c:url value="/resources/tmp.html"/>" onload="this.style.height=this.contentDocument.body.scrollHeight +'px';">
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
        <script src="<c:url value="/resources/crawler/js/jquery-1.10.2.min.js" />"></script>

        <!--jquery-ui-->
        <script src="<c:url value ="/resources/crawler/js/jquery-ui/jquery-ui-1.10.1.custom.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/resources/crawler/js/jquery-migrate.js"/>"></script>
        <script src="<c:url value="/resources/crawler/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/crawler/js/modernizr.min.js" />"></script>

        <!--Nice Scroll-->
<%--         <script src="<c:url value="/resource/js/jquery.nicescroll.js" type="text/javascript" />"> </script> --%>

        <!--right slidebar-->
        <script src="<c:url value="/resources/crawler/js/slidebars.min.js" />"></script>

        <!--switchery-->
        <script src="<c:url value="/resources/crawler/js/switchery/switchery.min.js" />"></script>
        <script src="<c:url value= "/resources/crawler/js/switchery/switchery-init.js" />"></script>

        <!--Icheck-->
        <script src="<c:url value="/resources/crawler/js/icheck/skins/icheck.min.js" />"></script>
        <script src="<c:url value="/resources/crawler/js/todo-init.js" />"></script>

        <!--jquery countTo-->
<%--         <script src="<c:url value="/resources/crawler/js/jquery-countTo/jquery.countTo.js"  type="text/javascript" />"></script> --%>

        <!--owl carousel-->
        <script src="<c:url value="/resources/crawler/js/owl.carousel.js" />"></script>


        <!--common scripts for all pages-->
        <script src="<c:url value="/resources/crawler/js/scripts.js" />"></script>
    </body>
</html>
