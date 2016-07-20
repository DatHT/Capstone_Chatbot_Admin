<script src="resources/assets/scripts/dataConfig.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="api" value="${api_sync}" />
<c:set var="log" value="${log_sync}" />
<c:set var="synonym" value="${synonym_sync}" />
<div class="c-header">
	<input type="hidden" value="${_csrf.parameterName}" id="paramName" />
	<input type="hidden" value="${_csrf.token}" id="token" />
	<h2>Typography</h2>

	<ul class="actions a-alt">
		<li><a href="#"> <i class="zmdi zmdi-trending-up"></i>
		</a></li>
		<li><a href="#"> <i class="zmdi zmdi-check-all"></i>
		</a></li>
		<li class="dropdown"><a href="#" data-toggle="dropdown"> <i
				class="zmdi zmdi-more-vert"></i>
		</a>

			<ul class="dropdown-menu dropdown-menu-right">
				<li><a href="#">Refresh</a></li>
				<li><a href="#">Manage Widgets</a></li>
				<li><a href="#">Widgets Settings</a></li>
			</ul></li>
	</ul>
</div>

<div class="row">
	<div class="card">
		<div class="card-header">
			<h2>Config Data</h2>
		</div>

		<div class="card-body card-padding">
			<div class="row">
				<button class="btn btn-primary btn-lg waves-effect"
					onclick="applySynchronize()" style="float: right">Apply</button>
			</div>
			<div class="row">
				<div class="toggle-switch col-lg-5"
					style="min-width: 800px !important;">
					<div class="col-md-5">
						<label for="ts1" class="ts-label">Synchronize From API To
							Database </label>
					</div>
					<div class="col-md-3">
						<input id="ts1" type="checkbox" hidden="hidden"
							<c:if test="${api.status}">checked</c:if>> <label
							for="ts1" class="ts-helper"></label>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				<div class="toggle-switch col-lg-5"
					style="min-width: 800px !important;">
					<div class="col-md-5">
						<label for="ts2" class="ts-label">Update Log</label>

					</div>
					<div class="col-md-3">
						<input id="ts2" type="checkbox" hidden="hidden"
							<c:if test="${log.status}">checked</c:if>> <label
							for="ts2" class="ts-helper"></label>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				<div class="toggle-switch col-lg-5"
					style="min-width: 800px !important;">
					<div class="col-md-5">
						<label for="ts3" class="ts-label">Synchronize Synonym</label>

					</div>
					<div class="col-md-3">
						<input id="ts3" type="checkbox" hidden="hidden"
							<c:if test="${synonym.status}">checked</c:if>> <label
							for="ts3" class="ts-helper"></label>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 18px">
				<div class="col-lg-8">
					<div class="col-md-5">
						<label class="ts-label">Set Time Scheduler</label>
					</div>
					<div class="col-md-3">
						<div class="fg-line">
							<div class="select">
								<select class="form-control" id="select-day">
									<option value="everyday" selected="selected">Everyday</option>
									<option value="everyweek">EveryWeek</option>
									<option value="everymonth">EveryMonth</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<div class="fg-line">
							<div class="select" >
								<select id="select-hour" class="form-control">
									<option value="0">00</option>
									<option value="1">01</option>
									<option value="2">02</option>
									<option value="3">03</option>
									<option value="4">04</option>
									<option value="5">05</option>
									<option value="6">06</option>
									<option value="7">07</option>
									<option value="8">08</option>
									<option value="9">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23" selected="selected">23</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<div class="fg-line">
							<div class="select">
								<select class="form-control"  id="select-minute">
									<option selected="selected" value="0">00</option>
									<option value="1">01</option>
									<option value="2">02</option>
									<option value="3">03</option>
									<option value="4">04</option>
									<option value="5">05</option>
									<option value="6">06</option>
									<option value="7">07</option>
									<option value="8">08</option>
									<option value="9">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>
								</select>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>


</div>