<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <!-- Vendor CSS -->
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/fullcalendar/dist/fullcalendar.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/animate.css/animate.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/google-material-color/dist/palette.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap-select/dist/css/bootstrap-select.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/nouislider/distribute/jquery.nouislider.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/farbtastic/farbtastic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/chosen/chosen.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/summernote/dist/summernote.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendors/bootgrid/jquery.bootgrid.min.css"
          rel="stylesheet">

    <!-- CSS -->
    <link href="${pageContext.request.contextPath}/resources/assets/css/app.min.1.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/app.min.2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/layout.css" rel="stylesheet">

    <!-- Core Scripts - Include with every page -->
    <!-- Javascript Libraries -->
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/Waves/dist/waves.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bootstrap-growl/bootstrap-growl.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap-select/dist/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/nouislider/distribute/jquery.nouislider.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/typeahead.js/dist/typeahead.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/summernote/dist/summernote-updated.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/bootgrid/jquery.bootgrid.updated.min.js"></script>
    <!-- Placeholder for IE9 -->
    <!--[if IE 9 ]>
    <script src="${pageContext.request.contextPath}/resources/vendors/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
    <![endif]-->
    <script
            src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/chosen/chosen.jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/fileinput/fileinput.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/input-mask/input-mask.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/vendors/farbtastic/farbtastic.min.js"></script>

    <script
            src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/assets/vendors/bower_components/autosize/dist/autosize.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/assets/js/functions.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/js/actions.js"></script>
    <script src="${pageContext.request.contextPath}/resources/assets/js/demo.js"></script>
</head>


<body class="body-padding">
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
           prefix="tilesx" %>
<tilesx:useAttribute name="current"/>
<c:set var="cur" scope="request" value="${current}"/>

<nav class="navbar navbar-default navbar-fixed-top header-color">
    <tiles:insertAttribute name="header"/>
</nav>

<section id="main">
    <tiles:insertAttribute name="menu"/>

    <div class="container">
        <tiles:insertAttribute name="body"/>
    </div>
</section>

<nav class="navbar navbar-default navbar-fixed-bottom">
    <tiles:insertAttribute name="footer"/>
</nav>
​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​
<!-- Page Loader -->
<div class="page-loader palette-Teal bg">
    <div class="preloader pl-xl pls-white">
        <svg class="pl-circular" viewBox="25 25 50 50">
            <circle class="plc-path" cx="50" cy="50" r="20"/>
        </svg>
    </div>
</div>

<div class="modal fade" id="loadingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog"
         style="position: absolute;margin: auto;top: 0;right: 0;bottom: 0;left: 0;width: 100px;height: 100px;">
        <div class="modal-content">

            <div class="modal-body"
                 style="position: absolute;margin: auto;top: 0;right: 0;bottom: 0;left: 0;width: 100px;height: 100px;">

                <div class="preloader pl-xl">
                    <svg class="pl-circular" viewBox="25 25 50 50">
                        <circle class="plc-path" cx="50" cy="50" r="20"/>
                    </svg>
                </div>


            </div>

        </div>
    </div>
</div>

</body>
</html>