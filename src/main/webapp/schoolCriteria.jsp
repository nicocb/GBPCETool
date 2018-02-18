<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${mode != 'admin'}">
   	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
 	<script>
	 	$(document).ready(function() { 
	 	    var options = { 
	 	        beforeSubmit:  beforeCriteria, 
	 	        success:       afterCriteria,
	 	        error:       errorCriteria,
	 	        uploadProgress: uploadingCriteria,
	 	        url:       '/api/schoolCriteria',         
	 	        dataType:  'json'       
	 	    }; 
	 	    $('.ammo').ajaxForm(options); 
	 	}); 
	</script>
</c:if>
<div class="container">
	<div class="container-fluid" >
		<div class="row vertical-align">
			<div class="col-md-4 col-xs-8">
				<h3>${mode == 'admin'?school.schoolName:'My'} Status :  </h3><span class="badge">${schoolCertificationDashboard.nbPending}
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
											<label class="label label-${empty certificationCriterion.status.style?'Default':certificationCriterion.status.style}">${empty certificationCriterion.status.description?'Not provided':certificationCriterion.status.description}</label><c:if test="${not empty highlight && highlight == certificationCriterion.id}"><span class="badge error">new</span></c:if>
										</div>
										<div class="col-md-1 col-xs-1">
											<button id='${fn:escapeXml(certificationCriterion.criterion.id)}-btn' onclick="switchForm('${fn:escapeXml(certificationCriterion.criterion.id)}')" type="button" class="btn btn-default btn-sm btn-primary-spacing"><span class="glyphicon glyphicon-triangle-bottom" ></span></button>
										</div>
									</div>
										<div class="panel panel-primary panel-criteria" id="${fn:escapeXml(certificationCriterion.criterion.id)}" ${not empty highlight && highlight == certificationCriterion.id?'':'style="display:none"'}>
										<c:if test="${not empty certificationCriterion.criterion.action}"><div class="panel-heading panel-heading-criteria">
												<h5>${fn:escapeXml(certificationCriterion.criterion.action)} :</h5>
										</div></c:if>
										<div class="panel-body">
										<div class="media" >
											<c:choose>
  												<c:when test="${not empty certificationCriterion.picture}">
													<div class="media-left">
														<img id="pic${certificationCriterion.criterion.id}" alt="Yep" class="media-object" style="width:60px"  onclick="resizeImg(this)" 
															src="${fn:escapeXml(certificationCriterion.picture)}" >
													</div>
												</c:when>
												<c:when test="${not empty certificationCriterion.criterion.picture}">
													<div class="media-left">
														<label for="example">Example</label>
														<img id="pic${certificationCriterion.criterion.id}" alt="Yep" class="media-object" style="width:60px"  onclick="resizeImg(this)" 
															src="${fn:escapeXml(certificationCriterion.criterion.picture)}" >
													</div>
												</c:when>
												<c:otherwise>
													<div class="media-left">
															<label for="example">Example</label>
															<img id="pic${certificationCriterion.criterion.id}" alt="Yep" class="media-object" style="width:60px"  onclick="resizeImg(this)" 
																src="/pics/default.jpg" >
													</div>
												</c:otherwise>
											 </c:choose>
											<div class="media-body">
												<c:choose>
												<c:when test="${criteriaByRank.available == 'true' || mode == 'admin'}">	
												
													<form method="POST" id="ammo${certificationCriterion.criterion.id}" class="ammo" 
														${mode == 'admin'?'':'enctype="multipart/form-data"'}>
														<div class="form-group">
														  <label for="comment">Send a comment :</label>
														  <div id="ammocomment${certificationCriterion.criterion.id}">
															  <c:forEach items="${certificationCriterion.comment}" var="currentComment">
															  	<div class="alert alert-${currentComment.author == 'GB'?'danger':'info'}" role="alert">${fn:escapeXml(currentComment.comment)}</div>
															  </c:forEach>
														  </div>
														  <textarea class="form-control" rows="5" name="comment"  id="comment" ></textarea>
														</div>
														
														<input type="hidden" name="id"
														value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
														value="${schoolId}"/>
													

														<c:if test="${mode != 'admin'}">
														<div id="ammovalidate${certificationCriterion.criterion.id}" class="form-group" style="display:block">
															<label for="image">School picture : </label>
															<div class="input-group">
																<label class="input-group-btn"> 
																	<span class="btn btn-primary">Browse...<input type="file" name="picture" style="display: none;"/>
																	</span>
																</label> 
																<input type="text" id="ammofile${certificationCriterion.criterion.id}" class="form-control">
																<label class="input-group-btn"> 
																	<span class="btn btn-danger"> Send<button type="submit" style="display: none;"></button>
																	</span>
																</label> 
															</div>
														</div>
														<div id ="ammoprogress${certificationCriterion.criterion.id}" class="progress" ${not empty highlight && highlight == certificationCriterion.id?'':'style="display:none"'}>
															<div class="progress-bar" role="progressbar"
																aria-valuenow="2" aria-valuemin="0"
																aria-valuemax="100" style="min-width: 2em;width: 2%">
															</div>
														</div>
														</c:if>
														<c:if test="${mode == 'admin'}">
															<button type="submit" class="btn btn-warning" formAction="/admin/schoolCriteriaAdmin">Comment</button>
															<button type="submit" class="btn btn-success" formAction="/admin/schoolCriteriaAdminValidate">Validate</button>
															<button type="submit" class="btn btn-danger" formaction="/admin/schoolCriteriaAdminRefuse">Refuse</button>
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
