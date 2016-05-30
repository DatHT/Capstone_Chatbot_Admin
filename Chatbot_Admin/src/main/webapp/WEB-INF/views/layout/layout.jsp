<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
   
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> <tiles:insertAttribute name="title" ignore="true" /> </title>
    <!-- Core CSS - Include with every page -->
    <link href="resources/assets/plugins/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="resources/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="resources/assets/plugins/pace/pace-theme-big-counter.css" rel="stylesheet" />
    <link href="resources/assets/css/style.css" rel="stylesheet" />
      <link href="resources/assets/css/main-style.css" rel="stylesheet" />

</head>

<body>
    <!--  wrapper -->
    <div id="wrapper">
    	<!-- Header -->
		<tiles:insertAttribute name="header" />

		<!-- nvbar slide left -->
		<tiles:insertAttribute name="left"/>
        <!--  page-wrapper -->
        <div id="page-wrapper">
        
        	<tiles:insertAttribute name="body" />
            
        </div>
        <!-- end page-wrapper -->
        
        <!-- footer -->
        <tiles:insertAttribute name="footer"/>

    </div>
    <!-- end wrapper -->

    <!-- Core Scripts - Include with every page -->
    <script src="resources/assets/plugins/jquery-1.10.2.js"></script>
    <script src="resources/assets/plugins/bootstrap/bootstrap.min.js"></script>
    <script src="resources/assets/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="resources/assets/plugins/pace/pace.js"></script>
    <script src="resources/assets/scripts/siminta.js"></script>

</body>


</html>