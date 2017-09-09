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
					<div class="panel-heading"><strong>${criteriaByRank.rank.description}</strong> score
						: ${criteriaByRank.actualScore}/${criteriaByRank.score}  <c:if test="${criteriaByRank.available == 'false'}">Complete other stages to activate</c:if></div>
					<div class="panel-body">
						<table class="table table-striped">
						<c:forEach items="${criteriaByRank.criteria}"
							var="certificationCriterion">
							<tr><td>
							<div class="container-fluid">

									<div class="row vertical-align">
										<div class="col-md-8">
											${fn:escapeXml(certificationCriterion.criterion.description)} 
											<c:if test="${not empty certificationCriterion.criterion.comment}"><small>(${fn:escapeXml(certificationCriterion.criterion.comment)}) </small></c:if>
										</div>
										<div class="col-md-3">
											<label class="label label-${empty certificationCriterion.status.style?'Default':certificationCriterion.status.style}">${empty certificationCriterion.status.description?'Not provided':certificationCriterion.status.description}</label><c:if test="${not empty highlight && highlight == certificationCriterion.id}"><span class="badge">new</span></c:if>
										</div>
										<div class="col-md-1">
											<button onclick="switchForm('${fn:escapeXml(certificationCriterion.criterion.id)}')" type="button" class="btn btn-basic .btn-xs"><span class="glyphicon glyphicon-search" ></span></button>
										</div>
									</div>
										<div class="panel panel-default" id="${fn:escapeXml(certificationCriterion.criterion.id)}" style="display:none">
										<c:if test="${not empty certificationCriterion.criterion.action}"><div class="panel-heading">
												<h4>${fn:escapeXml(certificationCriterion.criterion.action)} :</h4>
										</div></c:if>
										<div class="panel-body">
										<div class="media" >
											<c:if test="${not empty certificationCriterion.picture}">
												<div class="media-left">
													<img alt="Yep" class="media-object" style="width:60px"  onclick="resizeImg(this)" 
														src="${fn:escapeXml(certificationCriterion.picture)}" >
												</div>
											</c:if>
											<div class="media-body">

												<c:if test="${criteriaByRank.available == 'true'}">	
												
													<form method="POST" action="/schoolCriteria"
														${mode == 'admin'?'':'enctype="multipart/form-data"'}>
														<div class="form-group">
														  <label for="comment">Send a comment :</label>
														  <textarea class="form-control" rows="5" name="comment"  id="comment" >${fn:escapeXml(certificationCriterion.comment)}</textarea>
														</div>
														
														<input type="hidden" name="id"
														value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
														value="${schoolId}"/>
													

														<c:if test="${mode != 'admin'}">
														<div class="form-group">
															<label for="image">School picture : </label>
															<div class="input-group">
																<label class="input-group-btn"> 
																	<span class="btn btn-primary">Browse...<input type="file" name="picture" style="display: none;"/>
																	</span>
																</label> 
																<input type="text" class="form-control">
																<label class="input-group-btn"> 
																	<span class="btn btn-danger"> Send<button type="submit" style="display: none;"></button>
																	</span>
																</label> 
															</div>
														</div></c:if>
														<c:if test="${mode == 'admin'}">
															<button type="submit" class="btn btn-success" formAction="/admin/schoolCriteriaAdmin">Validate</button>
															<button type="submit" class="btn btn-danger"
																formaction="/admin/schoolCriteriaAdminRefuse">Refuse</button>
														</c:if>
													</form>
												
												</c:if>
											</div>
										</div>
									</div></div>
							</div>
							</td></tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</c:forEach>

		</c:otherwise>
	</c:choose>
</div>
<!-- [END list] -->
