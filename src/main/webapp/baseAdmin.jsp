<!--
Copyright 2016 Google Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
<title>PCE - DASHBOARD</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet"
		href="/css/gb.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="/scripts/gbScripts.js"></script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#gb-navbar-collapse"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="pull-left">
					<img src="/pics/gb-logo.png" height="50" width="50" />
				</div>
			</div>
			<div class="navbar-collapse collapse"
				id="gb-navbar-collapse" aria-expanded="false"
				style="height: 1px;">
				<h4 class="navbar-text">School certification <strong>ADMIN</strong></h4>
				<ul class="nav navbar-nav">
					<li><a href="/admin/events">Events</a></li>
				</ul>
				<ul class="nav navbar-nav">
					<li><a href="/admin/criteria">Criteria</a></li>
				</ul>
				<ul class="nav navbar-nav">
					<li><a href="/admin/schools">Schools</a></li>
				</ul>
				<p class="navbar-text navbar-right">
					<c:choose>
						<c:when test="${not empty userEmail}">
							<a href="/logout"> <c:if test="${not empty userImageUrl}">
									<img class="img-circle" src="${fn:escapeXml(userImageUrl)}"
										width="24">
								</c:if> ${fn:escapeXml(userEmail)}
							</a>
						</c:when>
						<c:otherwise>
							<a href="/login">Login</a>
						</c:otherwise>
					</c:choose>
				</div>
		</div>
	</nav>

	<c:import url="/${page}.jsp" />
</body>
</html>
<!-- [END base]-->
