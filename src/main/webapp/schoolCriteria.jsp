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
	<h3>
		Criteria <span class="badge">${schoolCertificationDashboard.nbPending}
			pending</span><span class="badge">${schoolCertificationDashboard.nbMissing}
			not provided</span>
	</h3>
	<p>Highlight : ${highlighted}</p>
	<c:choose>
		<c:when test="${empty schoolCertificationDashboard}">
			<p>No criteria found</p>
		</c:when>
		<c:otherwise>
			<div class="progress">
				<div class="progress-bar progress-bar-success" style="width: ${schoolCertificationDashboard.criteria[0].actualScore}%">
				</div>
				<c:if test="${fn:length(schoolCertificationDashboard.criteria) > 1}">
				<div class="progress-bar progress-bar-warning" style="width: ${schoolCertificationDashboard.criteria[1].actualScore}%">
				</div></c:if>
				<c:if test="${fn:length(schoolCertificationDashboard.criteria) > 2}">
				<div class="progress-bar progress-bar-danger" style="width: ${schoolCertificationDashboard.criteria[2].actualScore}%">
				</div></c:if>
			</div>
			<c:forEach items="${schoolCertificationDashboard.criteria}"
				var="criteriaByRank">
				<div class="panel panel-default">
					<div class="panel-heading">Stage ${criteriaByRank.rank} score
						: ${criteriaByRank.actualScore}/${criteriaByRank.score}  <c:if test="${criteriaByRank.available == 'false'}">Complete stage ${criteriaByRank.rank -1} to activate</c:if></div>
					<div class="panel-body">

						<c:forEach items="${criteriaByRank.criteria}"
							var="certificationCriterion">
							<div class="media">
								<div class="media-body">
									<h4>${fn:escapeXml(certificationCriterion.criterion.description)} <c:if test="${highlight == certificationCriterion.id}"><span class="badge">new</span></c:if></h4>      
									
									<c:choose>
										<c:when test="${empty certificationCriterion.pending}">
											<p>Not provided</p>
										</c:when>
										<c:when test="${certificationCriterion.pending == 'true'}">
											<p>Pending</p>
										</c:when>
										<c:when test="${certificationCriterion.pending == 'false'}">
											<p>Validated</p>
										</c:when>
									</c:choose>

									<c:choose>
										<c:when test="${mode == 'admin'}">
											<form method="POST" action="/admin/schoolCriteriaAdmin">
												<input type="text" name="comment" id="comment"
													value="${fn:escapeXml(certificationCriterion.comment)}"
													class="form-control" /> <input type="text" name="picture"
													id="picture"
													value="${fn:escapeXml(certificationCriterion.picture)}"
													class="form-control" disabled/> <input type="hidden" name="id"
													value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
													value="${schoolId}"/>
												<button type="submit" class="btn btn-success">Validate</button>
												<button type="submit" class="btn btn-danger"
													formaction="/admin/schoolCriteriaAdminRefuse">Refuse</button>
											</form>
										</c:when>
										<c:when test="${empty mode}">
											<form method="POST" action="/schoolCriteria">
												<input type="text" name="comment" id="comment"
													value="${fn:escapeXml(certificationCriterion.comment)}"
													class="form-control" disabled/>
												<input type="text" name="picture" id="picture"
													value="${fn:escapeXml(certificationCriterion.picture)}"
													class="form-control" /> <input type="hidden" name="id"
													value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
													value="${schoolId}"/>
												<c:if test="${criteriaByRank.available == 'true'}"><button type="submit" class="btn btn-danger">Upload</button></c:if>
											</form>
										</c:when>
									</c:choose>
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
