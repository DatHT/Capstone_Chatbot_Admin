<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/assets/scripts/conversationScript.js"></script>
<c:set var="dates" value="${DATES}" />

<div class="card">
	<div class="my-c-header">
		<h2>Conversations</h2>
	</div>

	<div class="card-header">
		<h2>
			Conversations <small>Here shows the conversations with PISB.</small>

		</h2>
	</div>

	<div class="card-body card-padding">
		<!-- <p class="c-black f-500 m-b-5">Accordion</p>
		<small>Extend the default collapse behavior to create an
			accordion with the panel component.</small> <br /> <br />
 -->
		<div class="row">
			<div class="col-sm-2 m-b-25">
				<p class="f-500 m-b-15 c-black">Choose date</p>
				<select class="chosen"
					data-placeholder="Choose a Lexical Category..."
					onchange="loadFullConversation(this)" id="selectCategory">
					<c:if test="${not empty dates}">
						<c:forEach var="date" items="${dates}">
							<option value="${date.value}">${date.name}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<div id="panel-group" class="panel-group" role="tablist" aria-multiselectable="true">
		</div>
	</div>
</div>