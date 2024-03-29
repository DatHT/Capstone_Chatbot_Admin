<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value = "/resources/assets/css/runnable.css"/>"
	rel="stylesheet">
<link href='<c:url value="/resources/assets/css/popup.css"/>'
	rel="stylesheet">
<script src='<c:url value="/resources/assets/configscripts/parse.js" />'></script>

<link href="<c:url value="/resources/assets/css/slidebars.css"/>"
	rel="stylesheet">
</head>
<body>
	<c:set var="url" value="${sessionScope.URL}" />
	<form:form name="myForm" id="myForm" action="addPageDetails"
		method="post">
		<div class="scroller_anchor"></div>
		<div class="card scroller" id="scr">
			<div class="card-header">
				<h2>Please Select Required Field For Each Step</h2>
			</div>
			<div class="progressRecipe card-body card-padding"
				style="padding-bottom: 0px">
				<div class="circle done">
					<span class="labelRecipe">0</span> <span class="title">Welcome</span>
				</div>
				<span class="bar half"></span>
				<div class="circle active">
					<span class="labelRecipe">1</span> <span class="title"
						style="margin-left: -30px">RestaurantName</span>
				</div>
				<span class="bar"></span>
				<div class="circle">
					<span class="labelRecipe">2</span> <span class="title">Address</span>
				</div>
				<span class="bar"></span>
				<div class="circle">
					<span class="labelRecipe">3</span> <span class="title">UserRate</span>
				</div>
				<span class="bar" style="display: none" id="ratingId"></span>
				<div class="circle" style="display: none; color: white"
					id="ratingCoe">
					<span class="labelRecipe" style="color: none"> <input
						type="text" name="ratingCoe" value="" id="ratingText"
						style="text-align: center; color: black; border: none; width: 32px;" />
					</span><span class="title" style="margin-left: -30px; color: #b5b5ba">RatingCoefficient</span>
				</div>
			</div>
			<div class="card-body card-padding"
				style="padding-bottom: 0px; padding-top: 0px">
				<button type="button" class="btn btn-primary" id="btnBack"
					value="BACK" onclick="back()">BACK</button>
				<button type="button" class="btn btn-primary" id="btnNext"
					value="NEXT" onclick="next()">NEXT</button>
				<!--disable-->
				<button type="button" class="btn btn-primary" id="btnPreview"
					value="PREVIEW" onclick="openpopup('popup')" disabled>PREVIEW</button>
				<button type="submit" class="btn btn-primary" id="btnAdd"
					name="btnAction" value="AddNewConfiguration" onclick="addNew()"
					disabled>AddNewPageDetails</button>
				<button type="button" class="btn btn-primary" value="HOME"
					onclick="window.location = 'configuration'">HOME</button>
				<div class="table-responsive" style="min-height: 70px">
					<table class="table" id="tbItems" border="1" width="619"
						class="table">
						<th width="97%">Content</th>
					</table>
					<br />
					<div id="showXPath"></div>
					<br />
				</div>
			</div>
		</div>
		<div id="popup" class="popup"></div>
		<div id="bg" class="popup_bg"></div>
		<div class="col-sm-12 scollchange">
			<iframe width="100%" sandbox="allow-same-origin allow-form"
				width="700" height="500" id="myframe" src="resources/tmp.html"
				onload="this.style.height=this.contentDocument.body.scrollHeight +'px';">
			</iframe>
		</div>
		<table id="tbMain">

		</table>
	</form:form>
	<!-- Placed js at the end of the document so the pages load faster -->
</body>
<script>
	$(window).scroll(
			function(e) {
				// Get the position of the location where the scroller starts.
				var scroller_anchor = $(".scroller_anchor").offset().top;

				// Check if the user has scrolled and the current position is after the scroller start location and if its not already fixed at the top 
				if ($(this).scrollTop() >= scroller_anchor
						&& $('.scroller').css('position') != 'fixed') { // Change the CSS of the scroller to hilight it and fix it at the top of the screen.
					$('.scroller').css({
						'width' : document.getElementById("scr").offsetWidth,
						'position' : 'fixed',
						'z-index' : '100',
						'top' : '-20px',
					});
					// Changing the height of the scroller anchor to that of scroller so that there is no change in the overall height of the page.
					$('.scroller_anchor').css('height', '50px');
					$('.scollchange').css({
						'margin-top' : '350px',
						'positon' : 'absolute'
					});
					$('.popup').css({
						'position' : 'fixed',
						'z-index' : '100',
					});
				} else if ($(this).scrollTop() < scroller_anchor
						&& $('.scroller').css('position') != 'relative') { // If the user has scrolled back to the location above the scroller anchor place it back into the content.

					// Change the height of the scroller anchor to 0 and now we will be adding the scroller back to the content.
					$('.scroller_anchor').css('height', '0px');

					// Change the CSS and put it back to its original position.
					$('.scroller').css({
						'position' : 'relative',
						'width' : '100%',
					});
					$('.scollchange').css({
						'margin-top' : '0px'
					});
				}
			});
</script>
</html>
