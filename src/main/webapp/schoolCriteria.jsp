<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	 	    var commOptions = {
	 	    		beforeSubmit:  beforeComm, 
		 	        success:       afterComm,
		 	        error:       errorComm,
		 	        uploadProgress: uploadingComm,
		 	        url:"${mode == 'admin'?'/admin/schoolCriteriaAdmin':'/api/schoolCriteria'}",
		 	        dataType:  'json'  
	 		};
	 	    $('.commForm').ajaxForm(commOptions); 
	 	    var picDeleteOptions = {
	 	    		beforeSubmit:  beforeComm, 
		 	        success:       afterPicDelete,
		 	        error:       errorComm,
		 	        uploadProgress: uploadingComm,
		 	        url:'/api/schoolCriteria',         
		 	        dataType:  'json'  
	 		};
	 	    $('.picForm').ajaxForm(picDeleteOptions); 
	 	    
	 	   	$(".validate").on("click", function(e){				 	   
		 		commOptions.url = "/admin/schoolCriteriaAdminValidate";
		 	});
	 	   	$(".refuse").on("click", function(e){
		 		commOptions.url = "/admin/schoolCriteriaAdminRefuse";
		 	});

	 	}); 
	</script>
<c:import url="/modal.jsp" />
<c:import url="/modalPic.jsp" />
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
											<label id="status${fn:escapeXml(certificationCriterion.criterion.id)}" class="label label-${empty certificationCriterion.status.style?'Default':certificationCriterion.status.style}">${empty certificationCriterion.status.description?'Not provided':certificationCriterion.status.description}</label><c:if test="${not empty highlight && highlight == certificationCriterion.id}"><span class="badge error">new</span></c:if>
										</div>
										<div class="col-md-1 col-xs-1">
											<button id='${fn:escapeXml(certificationCriterion.criterion.id)}-btn' onclick="switchForm('${fn:escapeXml(certificationCriterion.criterion.id)}')" type="button" class="btn btn-default btn-sm btn-primary-spacing"><span class="glyphicon ${not empty highlight && highlight == certificationCriterion.id?'glyphicon-triangle-top':'glyphicon-triangle-bottom'}" ></span></button>
										</div>
									</div>
										<div class="panel panel-primary panel-criteria" id="${fn:escapeXml(certificationCriterion.criterion.id)}" ${not empty highlight && highlight == certificationCriterion.id?'':'style="display:none"'}>
										<c:if test="${not empty certificationCriterion.criterion.action}"><div class="panel-heading panel-heading-criteria">
												<h5>${fn:escapeXml(certificationCriterion.criterion.action)} :</h5>
										</div></c:if>
										<div class="panel-body">
										<div class="media" >
											<div id="media${certificationCriterion.criterion.id}" class="media-left">
												<c:if test="${empty certificationCriterion.picture}"><label id="example${certificationCriterion.criterion.id}" for="example">Example</label>
													<img id="pic${certificationCriterion.id}" alt="Yep" class="media-object example" style="width:60px"  onclick="resizeImg(this)" 
														src="${fn:escapeXml(not empty certificationCriterion.picture?certificationCriterion.picture:not empty certificationCriterion.criterion.picture?certificationCriterion.criterion.picture:'/pics/default.jpg')}" >
												</c:if>
												<c:forEach items="${certificationCriterion.picture}" var="pic">
												<img id="pic${pic.id}" alt="Yep" class="media-object" style="width:60px;margin:3px"  onclick="resizeImg(this)" 
													src="${fn:escapeXml(pic.url)}" >
												<form method="POST" id="comm${certificationCriterion.criterion.id}" class="picForm">
													<input type="hidden" name="action" value="DELETE" />
													<input type="hidden" name="picId" value="${pic.id}" />
													<input type="hidden" name="schoolId" value="${schoolId}"/>
													<input type="hidden" name="critId" value="${certificationCriterion.criterion.id}" />
													<button type="submit" class="btn btn-danger" formAction="/api/schoolCriteria">Delete</button>
												</form>
												</c:forEach>
											</div>
											<div class="media-body">
												<c:choose>
												<c:when test="${criteriaByRank.available == 'true' || mode == 'admin'}">	
												
													<form method="POST" id="comm${certificationCriterion.criterion.id}" class="commForm">
														<div class="form-group">
														  <label for="comment">Send a comment :</label>
														  <div id="ammocomment${certificationCriterion.criterion.id}">
															  <c:forEach items="${certificationCriterion.comment}" var="currentComment">
															  	<div class="alert alert-${currentComment.author == 'GB'?'danger':'info'}" role="alert">${currentComment.comment}</div>
															  </c:forEach>
														  </div>
														  <textarea class="form-control" rows="5" name="comment"  id="comment" ></textarea>
														</div>
														<input type="hidden" name="action" value="COMMENT" />
														<input type="hidden" name="id"
														value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
														value="${schoolId}"/>
													
														
														<button type="submit" class="btn btn-warning comment" formAction="${mode == 'admin'?'/admin/schoolCriteriaAdmin':'/api/schoolCriteria'}">Comment</button>
														<c:if test="${mode == 'admin'}">
															<button type="submit" class="btn btn-danger refuse" formaction="/admin/schoolCriteriaAdminRefuse" style="float: right;margin-left:4">Refuse</button>
															<button type="submit" class="btn btn-success validate" formAction="/admin/schoolCriteriaAdminValidate" style="float: right;margin-left:4"">Validate</button>
														</c:if>
													</form>
													<c:if test="${mode != 'admin'}">
														<form method="POST" id="pict${certificationCriterion.criterion.id}" class="ammo" 
															enctype="multipart/form-data">
															<input type="hidden" name="id"
																value="${certificationCriterion.criterion.id}" /><input type="hidden" name="schoolId"
																value="${schoolId}"/>
															<div id="ammovalidate${certificationCriterion.criterion.id}" class="form-group" style="display:block">
																<label for="image">School picture  : </label>
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
																<label for="image">Use your camera or choose from existing pictures. Handled formats are jpg and png</label>
															</div>
														</form>
													</c:if>
												
												</c:when>
												<c:otherwise>
													<div class="form-group">
														  <label for="comment">Comment :</label>
														  <div>
															  <c:forEach items="${certificationCriterion.comment}" var="currentComment">
															  	<div class="alert alert-${currentComment.author == 'GB'?'danger':'info'}" role="alert">${currentComment.comment}</div>
															  </c:forEach>
														  </div>													</div>
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
