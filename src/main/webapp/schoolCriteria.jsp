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
	<div class="container-fluid" >
		<div class="row vertical-align">
			<div class="col-md-4 col-xs-8">
				<h3>My Status :  </h3><span class="badge">${schoolCertificationDashboard.nbPending}
						pending</span><span class="badge">${schoolCertificationDashboard.nbMissing}
						not provided</span>
			</div>
			<div class="col-md-4 col-xs-4">
				<c:choose>
					<c:when test="${schoolCertificationDashboard.rank.id > '0'}">
						<div class="container-fluid centerstars">
							<h4>Actual score : ${schoolCertificationDashboard.score}%</h4>
						</div>
						<div class="container-fluid centerstars">
							<c:if test="${schoolCertificationDashboard.rank.id >= '1'}"><img alt="Star" src="/pics/star.png" height="30" /></c:if>
							<c:if test="${schoolCertificationDashboard.rank.id >= '2'}"><img alt="Star" src="/pics/star.png" height="30" /><img alt="Star" src="/pics/star.png" height="30" /></c:if>
							<c:if test="${schoolCertificationDashboard.rank.id >= '3'}"><img alt="Star" src="/pics/star.png" height="30" /><img alt="Star" src="/pics/star.png" height="30" /></c:if>			
						</div>
						<div class="container-fluid centerstars">
							<img class="center-block" alt="${schoolCertificationDashboard.rank.description}" height="50" src="/pics/stage${schoolCertificationDashboard.rank.id}.png"/>
						</div>
					</c:when>
					<c:otherwise>
						<h4>In Progress. Actual score : ${schoolCertificationDashboard.score}%</h4> 
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${empty schoolCertificationDashboard}">
			<p>No criteria found</p>
		</c:when>
		<c:otherwise>
			<div class="progress panel-criteria">
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
					<div class="panel-heading panel-heading-custom" >
						<div class="container-fluid" >
							<div class="row vertical-align">
								<div class="col-md-8 col-xs-8">
									<img alt="${criteriaByRank.rank.description}" src="/pics/stage${criteriaByRank.rank.id}.png" height="40"/>
									<img alt="${criteriaByRank.validated?'Validated':'In Progress'}" src="/pics/${criteriaByRank.validated?'completed':'incomplete'}.png" height="50"/>
								</div>
								<div class="col-md-8 col-xs-8">
									<h5><strong>Score
										: ${criteriaByRank.actualScore}%</strong> (max : ${criteriaByRank.score}%)  <c:if test="${criteriaByRank.available == 'false'}"> Complete other stages to activate</c:if>
									</h5>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<c:forEach items="${criteriaByRank.criteria}"
							var="certificationCriterion">
							<div class="container-fluid bgcolored" >

									<div class="row vertical-align">
										<div class="col-md-7 col-xs-6">
											${fn:escapeXml(certificationCriterion.criterion.description)} 
											<c:if test="${not empty certificationCriterion.criterion.comment}"><small>(${fn:escapeXml(certificationCriterion.criterion.comment)}) </small></c:if>
										</div>
										<div class="col-md-4 col-xs-4">
											<label class="label label-${empty certificationCriterion.status.style?'Default':certificationCriterion.status.style}">${empty certificationCriterion.status.description?'Not provided':certificationCriterion.status.description}</label><c:if test="${not empty highlight && highlight == certificationCriterion.id}"><span class="badge">new</span></c:if>
										</div>
										<div class="col-md-1 col-xs-1">
											<button id='${fn:escapeXml(certificationCriterion.criterion.id)}-btn' onclick="switchForm('${fn:escapeXml(certificationCriterion.criterion.id)}')" type="button" class="btn btn-default btn-sm btn-primary-spacing pull-right"><span class="glyphicon glyphicon-triangle-bottom" ></span></button>
										</div>
									</div>
										<div class="panel panel-primary panel-criteria" id="${fn:escapeXml(certificationCriterion.criterion.id)}" style="display:none">
										<c:if test="${not empty certificationCriterion.criterion.action}"><div class="panel-heading panel-heading-criteria">
												<h5>${fn:escapeXml(certificationCriterion.criterion.action)} :</h5>
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
												<c:choose>
												<c:when test="${criteriaByRank.available == 'true' || mode == 'admin'}">	
												
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
												
												</c:when>
												<c:otherwise>
													<div class="form-group">
														  <label for="comment">Comment :</label>
														  <textarea class="form-control"  rows="5" name="comment"  id="comment" >${fn:escapeXml(certificationCriterion.comment)}</textarea>
													</div>
												</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>

		</c:otherwise>
	</c:choose>
</div>
