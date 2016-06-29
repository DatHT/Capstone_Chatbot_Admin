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
<link href="<c:url value = "/resources/assets/cssCode/runnable.css"/>"
	rel="stylesheet">
<link href='<c:url value="/resources/assets/cssCode/popup.css"/>'
	rel="stylesheet">
<script
	src="<c:url value ="/resources/assets/configscripts/jquery-2.2.0.js"/>"></script>
<script
	src='<c:url value="/resources/assets/configscripts/parseList.js" />'></script>

<!--right slidebar-->
<link
	href="<c:url value="/resources/assets/cssboostrap/slidebars.css"/>"
	rel="stylesheet">
</head>
<body class="c-body">
	<c:set var="url" value="${sessionScope.URL}" />
	<div class="c-header">
		<h2>Configuration</h2>
	</div>
	<div class="card">
		<div class="card-header">
			<h2>Please Select An Element And Get XPath</h2>
		</div>
	</div>
	<section>
		<div class="col-lg-12">
			<section class="panel">
				<div class="panel-body">
					<form name="myForm" id="myForm" action="processServlet"
						method="GET">
						<div class="scroller_anchor"></div>
						<div class="form-group card card-header scroller" style="">
							<div class="progressRecipe">
								<div class="circle done">
									<span class="labelRecipe">0</span> <span class="title">Welcome</span>
								</div>
								<span class="bar half"></span>
								<div class="circle active">
									<span class="labelRecipe">1</span> <span class="title">EachProduct</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">2</span> <span class="title">FoodName</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">3</span> <span class="title">Image</span>
								</div>
								<span class="bar"></span>
								<div class="circle">
									<span class="labelRecipe">4</span> <span class="title">NextPage</span>
								</div>
							</div>
							<button type="button" class="btn btn-primary" value="BACK"
								onclick="back()">BACK</button>
							<button type="button" class="btn btn-primary" id="btnNext"
								value="NEXT" onclick="next()">NEXT</button>
							<!--disable-->
							<button type="button" class="btn btn-primary" id="btnPreview"
								value="PREVIEW" onclick="openpopup('popup')" disabled>PREVIEW</button>
							<button type="submit" class="btn btn-primary" id="btnAdd"
								name="btnAction" value="AddNewPageList" onclick="addNew()"
								disabled>AddNewPageList</button>
							<button type="button" class="btn btn-primary" value="HOME"
								onclick="window.location = 'crawler'">HOME</button>
							<div class="table-responsive" style="min-height: 70px">
								<table class="table" id="tbItems" border="1" width="619"
									class="table">
									<th style="width: 97%; height: 40px;">Content</th>
								</table>
								<br />
								<div id="showXPath"></div>
								<br />
							</div>
						</div>
						<div id="popup" class="popup"></div>
						<div id="bg" class="popup_bg"></div>
						<div class="col-sm-12 scollchange" style="">
							<iframe style="width: 100%;"
								sandbox="allow-same-origin allow-form" width="700" height="900"
								id="myframe" src="resources/tmp.html"> </iframe>
						</div>
						<table id="tbMain">

						</table>
					</form>
					<br />
				</div>
			</section>
		</div>
	</section>
</body>
<script>
$(window).scroll(function(e) {
    // Get the position of the location where the scroller starts.
    var scroller_anchor = $(".scroller_anchor").offset().top;
     
    // Check if the user has scrolled and the current position is after the scroller start location and if its not already fixed at the top 
    if ($(this).scrollTop() >= scroller_anchor && $('.scroller').css('position') != 'fixed') 
    {    // Change the CSS of the scroller to hilight it and fix it at the top of the screen.
        $('.scroller').css({
        	'width':'78.9%',
            'position': 'fixed',
            'z-index':'100',
            'top': '0px'
        });
        // Changing the height of the scroller anchor to that of scroller so that there is no change in the overall height of the page.
        $('.scroller_anchor').css('height', '50px');
        $('.scollchange').css({
            'margin-top': '250px',
            'positon':'absolute'
        });
    } 
    else if ($(this).scrollTop() < scroller_anchor && $('.scroller').css('position') != 'relative') 
    {    // If the user has scrolled back to the location above the scroller anchor place it back into the content.
         
        // Change the height of the scroller anchor to 0 and now we will be adding the scroller back to the content.
        $('.scroller_anchor').css('height', '0px');
         
        // Change the CSS and put it back to its original position.
        $('.scroller').css({
            'position': 'relative',
            'width':'100%',
        });
        $('.scollchange').css({
            'margin-top': '0px'
        });
    }
});
</script>
</html>