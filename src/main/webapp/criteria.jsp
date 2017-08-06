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
<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Criteria</h3>
	<a href="/admin/criterion" class="btn btn-success btn-sm"> <i
		class="glyphicon glyphicon-plus"></i> Add certification criterion
	</a>
	<c:choose>
		<c:when test="${empty certificationCriteriaByRank}">
			<p>No criteria found</p>
		</c:when>
		<c:otherwise>

			<c:forEach items="${certificationCriteriaByRank}"
				var="certificationCriteria">
				<div class="panel panel-default">
					<div class="panel-heading">Stage
						${certificationCriteria.rank} max score : ${certificationCriteria.score}</div>
					<div class="panel-body">
						<c:forEach items="${certificationCriteria.criteria}" var="criterion">
							<div class="media">

								<div class="media-body">
									<a href="/admin/criterion?id=${criterion.id}"><h4>${fn:escapeXml(criterion.description)}</h4></a>
									<p>${fn:escapeXml(criterion.comment)}</p>
									<p>${fn:escapeXml(criterion.action)}</p>
									<p>${fn:escapeXml(criterion.rank)}</p>
									<p>${fn:escapeXml(criterion.score)}</p>
								</div>
								<div class="btn-group" role="group" aria-label="..." >
								<form class="btn-group" method="GET" action="/admin/criterion">
									<input type="hidden" name="id" value="${criterion.id}" />
									<button type="submit" class="btn btn-danger">Update</button>
								</form>
								<form class="btn-group" method="POST" action="/admin/criteria">
									<input type="hidden" name="action" value="DELETE" /><input
										type="hidden" name="id" value="${criterion.id}" />
									<button type="submit" class="btn btn-danger">Delete</button>
								</form>
								</div>

							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>
<!-- [END list] -->
