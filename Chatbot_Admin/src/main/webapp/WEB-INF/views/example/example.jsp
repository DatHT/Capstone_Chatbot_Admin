<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script src="resources/assets/scripts/commonScript.js"></script>
<script>
    var lexicals = '${jsonLexical}';
</script>
<script src="resources/assets/scripts/exampleScript.js"></script>
<script src="resources/assets/scripts/logScript.js"></script>
<c:set var="intents" value="${INTENTS}"/>
<c:set var="lexicals" value="${LEXICAL}"/>
<c:set var="logs" value="${LOGS}"/>
<div class="card">
	<div class="my-c-header">
		<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
		<input type="hidden" value="${_csrf.token}" id="token" />
		<h2>Training Bot By Example</h2>
	</div>
	<div class="card-header cw-header palette-Teal-400 bg" style="margin: 2%;">
		<div class="cwh-year">Training your bot step by step</div>
		<div class="cwh-day" id="your-example"></div>
			<!-- <h2>Training your bot step by step</h2>
			<h3 ></h3> -->
			<button onclick="processTraining()"
				class="btn palette-Light-Green bg btn-float waves-effect waves-circle waves-float">
				<i class="zmdi zmdi-arrow-right zmdi-hc-fw"></i>
			</button>
	</div>
	<div class="card-body card-padding">
			<div class="col-sm-3">Step1</div>
			<div class="col-sm-3">Step2</div>
			<div class="col-sm-3">Step3</div>
			<div class="col-sm-3">Step4</div>
			<div class="col-sm-12">
				<div class="progress progress-striped active">
					<div class="progress-bar" role="progressbar" aria-valuenow="0"
						aria-valuemin="0" aria-valuemax="100" style="width: 0%"
						id="progress-status"></div>
				</div>
			</div>
		</div>
</div>

<!-- Step 2 -->
<div class="card" id="card-step1">
    <div class="card-header">
        <strong>Choose Option To Train Your Bot</strong>
    </div>
    <div class="card-body card-padding">
        <label class="radio radio-inline m-r-20"> <input id="rd-bag"
                                                         onchange="handleShowBags()" type="radio"
                                                         name="inlineRadioOptions"
                                                         value="option1"> <i class="input-helper"></i> Get Example In
            Examples Bag
        </label> <label class="radio radio-inline m-r-20"> <input type="radio"
                                                                  id="rd-own" onchange="handleShowType()"
                                                                  name="inlineRadioOptions"
                                                                  value="option2"> <i class="input-helper"></i> Type
        Your Own
        Example
    </label>
        <div class="row" id="train-bag" style="display: none">
            <div class="col-sm-7 m-b-15">
                <select class="chosen" data-placeholder="Some suggestion example..."
                        onchange="pitchExample(this)" class="form-control" id="exampleList">
                    <option value="empty"></option>
                    <c:if test="${not empty logs}">
                        <c:forEach var="log" items="${logs}">
                            <c:if test="${not log.delete}">
                                <option value="${log.train}">${log.train}</option>
                            </c:if>

                        </c:forEach>
                    </c:if>
                </select>
            </div>

        </div>

        <div class="row" id="train-own-example" style="display: none">
            <div class="col-sm-7 m-b-15">
                <div class="form-group">
                    <div class="fg-line">
                        <input id="own-example" type="text" class="form-control"
                               placeholder="Type your own example">
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

<!-- Step 2 -->
<div class="card" id="card-step2" style="display: none">
    <div class="card-header">
        <strong>Training with pattern</strong>
    </div>
    <div class="card-body card-padding">
        <div id="user-say-container">
            <blockquote class="m-b-25" id="chosenExample"></blockquote>
        </div>

        <div class="card">
            <div class="card-body card-padding" id="containerDiv"></div>
        </div>
        <div class="card">
            <div class="card-body card-padding">
                <div id="box-dragable" ondrop="drop(event)" ondragover="allowDrop(event)">
                    <c:forEach var="dragItem" items="${lexicals}" varStatus="counter">
                        <div id="${dragItem.name}0"
                             class="draggable" draggable="true" ondragstart="drag(event)">
                                ${dragItem.name}
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Step 3 -->
<div class="card" id="card-step3" style="display: none">
    <div class="card-header">
        <strong>Test your pattern</strong>
    </div>
    <div class="card-body card-padding">
        <div class="card clearfix" id="messages">
            <div class="ms-menu">
                <div class="list-group lg-alt m-t-10">
                    <a class="list-group-item media" href="#">
                        <div class="pull-left">
                            <img
                                    src="${pageContext.request.contextPath}/resources/assets/img/profile-pics/4.jpg"
                                    alt="" class="avatar-img">
                        </div>
                        <div class="media-body">
                            <div class="lgi-heading">PSIB Bot</div>
                            <small class="lgi-text">Help your bot more intelligent</small>
                        </div>
                    </a>
                </div>
            </div>


            <div class="ms-body">
                <div class="action-header clearfix palette-Teal-400 bg">
                    <div class="ah-label hidden-xs palette-White text">PSIB Bot</div>

                    <div class="menu-collapse visible-xs"
                         data-ma-action="message-toggle">
                        <div class="mc-wrap">
                            <div class="mcw-line top palette-White bg"></div>
                            <div class="mcw-line center palette-White bg"></div>
                            <div class="mcw-line bottom palette-White bg"></div>
                        </div>
                    </div>
                </div>

                <div id="chat-flow" class="list-group lg-alt"
                     style="overflow: scroll; height: 300px;">
                    <div class="list-group-item media">
                        <div class="pull-left">
                            <img class="avatar-img"
                                 src="${pageContext.request.contextPath}/resources/assets/img/profile-pics/4.jpg"
                                 alt="">
                        </div>

                        <div class="media-body">
                            <div class="msb-item">Hello! Type your example to test your
                                pattern
                            </div>
                        </div>
                    </div>


                </div>


                <div class="ms-reply">
                    <textarea id="user-query" placeholder="What's on your mind..."></textarea>

                    <button onclick="sendTestQuery()">
                        <i class="zmdi zmdi-mail-send"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Step 4 -->
<div class="card" id="card-step4" style="display: none">
    <div class="card-header">
        <strong>Choose suitable intent for pattern</strong>
    </div>
    <div class="card-body card-padding">
        <div class="row">
            <select class="chosen" data-placeholder="Choose a Intent..."
                    class="form-control" id="selectIntent">
                <option value="empty"></option>

                <c:if test="${not empty intents}">
                    <c:forEach var="intent" items="${intents}">
                        <option value="${intent.id}">${intent.name}</option>
                    </c:forEach>
                </c:if>

            </select>
        </div>
    </div>


</div>


<style>
    #box-dragable {
        width: 100%;
        background: #eee;
        margin: 10px auto;
        height: auto;
    }

    #buttonadd {
        margin: 10px auto;
        padding: 10px;
    }

    #droptarget {
        padding: 2px;
        margin-right: 6px;
        cursor: default;
        border: 1px solid #999;
        display: inline-block;
    }

    .droptarget {
        padding: 2px;
        margin-right: 6px;
        margin-top: 6px;
        cursor: default;
        border: 1px solid #999;
        display: inline-block;
    }

    .draggable {
        cursor: move;
        width: auto;
        height: 30px;
        background-color: #03a9f4;
        text-align: center;
        border-radius: 0%;
        color: white;
        padding: 5px;
        display: inline-block;
        margin: 5px;
        box-shadow: 0 3px 10px rgba(0, 0, 0, 0.23), 0 3px 10px rgba(0, 0, 0, 0.16);
    }

    .painted {
        background-color: #03a9f4;
        color: #fff;
    }

    .draggable.hollow {
        cursor: default;
        background: #ececec;
    }
</style>
<!-- Modals-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete Product</h4>
            </div>
            <div class="modal-body" style="border-bottom: 0px">
                <div>
                    <h4>
                        Are you sure to delete this pattern <strong id="deletePatternName"></strong>
                        ?
                    </h4>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">Cancel
                </button>

                <button type="submit" class="btn btn-danger"
                        onclick="deletePattern()">Delete
                </button>
            </div>
        </div>
    </div>
</div>
<!-- End modal -->