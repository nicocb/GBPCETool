
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Academies</h3>
	<c:choose>
		<c:when test="${empty schools}">
			<p>No schools found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${schools}" var="schoolsByRank">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong>Rank : ${schoolsByRank.rank}</strong>  
					</div>
					<div class="panel-body">
						<c:forEach items="${schoolsByRank.schools}" var="sschool">
							<div class="container-fluid bgcolored" >
								<div class="row vertical-align">
									<div class="col-md-8 col-xs-6">
										<h4><strong><a href="/admin/schoolCriteriaAdmin/${fn:escapeXml(sschool.school.id)}">${fn:escapeXml(sschool.school.schoolName)}</a></strong>
										<c:if test="${highlight == sschool.school.id}">
											<span class="badge">new</span>
										</c:if>	</h4>
									</div>
									<div class="col-md-3 col-xs-4">
										<label class="label label-${empty sschool.school.status?'Default':sschool.school.status.style}">${empty sschool.school.status?'Not provided':sschool.school.status.description}</label><c:if test="${not empty highlight && highlight == certificationCriterion.id}"><span class="badge">new</span></c:if>
									</div>
									<div class="col-md-1 col-xs-1">
										
										<button onclick="switchForm('${fn:escapeXml(sschool.school.id)}')" type="button" class="btn btn-default btn-sm btn-primary-spacing pull-right"><span class="glyphicon glyphicon-triangle-bottom" ></span></button>
									</div>
								</div>
							</div>
							<div class="panel panel-primary panel-criteria" id="${sschool.school.id}" style="display:none">
								<div class="panel-heading panel-heading-criteria">
									<p>Rank : ${fn:escapeXml(sschool.rank.description)}</p>
									<p>Score : ${fn:escapeXml(sschool.score)}</p>
									<p>Pending : ${fn:escapeXml(sschool.nbPending)}</p>
									<p>Agreement status : ${fn:escapeXml(sschool.school.agreementStatus.description)}</p>

								</div>
								<div class="panel-body">

									
									<div class="panel panel-default">
									<div class="panel-heading">Contact :</div>
									<div class="panel-body">
										<p>Contact Name :
											${fn:escapeXml(sschool.school.contactName)}</p>
										<p>Contact Mail :
											${fn:escapeXml(sschool.school.contactMail)}</p>
										<p>Contact Phone :
											${fn:escapeXml(sschool.school.contactPhone)}</p>
									</div></div>
									<div class="panel panel-default">
									<div class="panel-heading">School :</div>
									<div class="panel-body">
										<p>School Name :
											${fn:escapeXml(sschool.school.schoolName)}</p>
										<p>School Address :
											${fn:escapeXml(sschool.school.schoolAddress)} ${fn:escapeXml(sschool.school.schoolZip)} ${fn:escapeXml(sschool.school.schoolCity)} ${fn:escapeXml(sschool.school.schoolCountry)} </p>
										<p>School Mail :
											${fn:escapeXml(sschool.school.schoolMail)}</p>
										<p>School Phone Number :
											${fn:escapeXml(sschool.school.schoolPhone)}</p>
										<p>School Website :
											${fn:escapeXml(sschool.school.schoolWeb)}</p>
									</div></div>									
									<div class="panel panel-default">
									<div class="panel-heading">Instructor :</div>
									<div class="panel-body">
										<p>Instructor Name :
											${fn:escapeXml(sschool.school.instructorName)}</p>
										<p>Instructor Belt :
											${fn:escapeXml(sschool.school.instructorBelt.description)}</p>
										<p>Instructor Professor :
											${fn:escapeXml(sschool.school.instructorProfessor)}</p>
									</div></div>									
									<div class="panel panel-default">
									<div class="panel-heading">Creation :</div>
									<div class="panel-body">
										<p>Creation Date :
											${fn:escapeXml(sschool.school.dateCreated)}</p>
										<p>Validation Date :
											${fn:escapeXml(sschool.school.dateValidated)}</p>
									</div></div>									

									<div class="btn-group" role="group" aria-label="..." align="center">
												<form class="btn-group" method="POST"
													action="/admin/schools">
													<input type="hidden" name="action" value="PUT" /><input
														type="hidden" name="id" value="${sschool.school.id}" /><input
														type="hidden" name="pending" value="${sschool.school.status == 'VALIDATED'}" />
													<button type="submit" class="btn btn-${sschool.school.status == 'VALIDATED'?'warning':'success'}">${sschool.school.status == 'VALIDATED'?'Revoke':'Validate'} school</button>
												</form>
												<form class="btn-group" method="POST"
													action="/admin/schools">
													<input type="hidden" name="action" value="init" /><input
														type="hidden" name="id" value="${sschool.school.id}" /><input
														type="hidden" name="validated" value="${sschool.school.initialFeeStatus == 'VALIDATED'}" />
													<button type="submit" class="btn btn-${sschool.school.initialFeeStatus == 'VALIDATED'?'warning':'success'}">${sschool.school.initialFeeStatus == 'VALIDATED'?'Revoke':'Validate'} initial fee</button>
												</form>
												<form class="btn-group" method="POST"
													action="/admin/schools">
													<input type="hidden" name="action" value="monthly" /><input
														type="hidden" name="id" value="${sschool.school.id}" /><input
														type="hidden" name="validated" value="${sschool.school.monthlyFeeStatus == 'VALIDATED'}" />
													<button type="submit" class="btn btn-${sschool.school.monthlyFeeStatus == 'VALIDATED'?'warning':'success'}">${sschool.school.monthlyFeeStatus == 'VALIDATED'?'Revoke':'Validate'} monthly fee</button>
												</form>
												<form class="btn-group" method="POST" action="/admin/schools">
													<input type="hidden" name="action" value="DELETE" /><input
														type="hidden" name="id" value="${sschool.school.id}" />
													<button type="submit" onclick="return confirm('Sure you want to delete ${sschool.school.schoolName} school?');" class="btn btn-danger">Delete</button>
												</form>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<strong>Existing documents :</strong>  
										</div>
										<div class="panel-body">
											<c:forEach items="${sschool.school.documents}" var="doc">
												<div class="container-fluid bgcolored">
													<div class="row vertical-align">
														<div class="col-md-11 col-xs-6">
															<h5>
																${fn:escapeXml(doc.key)}
															</h5>
														</div>
														<div class="col-md-1 col-xs-1">
															<a href="${fn:escapeXml(doc.value.URL)}">  <span style="font-size:1.2em;" class="glyphicon glyphicon-download"></span>
															</a>
														</div>														
														<div class="col-md-1 col-xs-1">

															<a href="#" onclick="document.getElementById('doc${sschool.school.id}${fn:escapeXml(doc.key)}').submit();">  <span style="font-size:1.2em;" class="glyphicon glyphicon-remove"></span>
															</a>
														</div>
													</div>
												</div>
												<form method="POST" action="/documents" id="doc${sschool.school.id}${fn:escapeXml(doc.key)}">
													<input type="hidden" name="action" value="DELETE" />
													<input type="hidden" name="schoolId" value="${sschool.school.id}" />
													<input type="hidden" name="documentName" value="${fn:escapeXml(doc.key)}" />
													<input type="hidden" name="documentFileName" value="${fn:escapeXml(doc.value.fileName)}" />
												</form>
											</c:forEach>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<strong>Add a document :</strong>  
										</div>
										<div class="panel-body">
											<div class="form-group">
												<form method="POST" action="/documents" enctype="multipart/form-data">
													<label for="certificate">File display name : </label>
													<div class="input-group">
														<input type="text" name="documentName" id="documentName" class="form-control">
													</div>
													<label for="certificate">File upload : </label>
													<div class="input-group">
														<label class="input-group-btn"> 
															<span class="btn btn-primary">Browse...<input type="file" name="certificate" style="display: none;"/>
															</span>
														</label> 
														<input type="text" class="form-control">
														<input
																	type="hidden" name="schoolId" value="${sschool.school.id}" />
														<label class="input-group-btn"> 
															<span class="btn btn-danger"> Send<button onclick="checkEmpty('documentName','Make sure you provided a document name');" type="submit" style="display: none;"></button>
															</span>
														</label> 
													</div>
												</form>
											</div>
										</div>
									</div>
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
