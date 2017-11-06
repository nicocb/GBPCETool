<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
	<c:choose>
		<c:when test="${empty schoolEvents}">
			<p>No events found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${schoolEvents}" var="schoolEventsByObject">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong>Object : ${schoolEventsByObject.object}</strong>  
					</div>
					<div class="panel-body">
						<c:forEach items="${schoolEventsByObject.events}" var="schoolEvent">
							<div class="container-fluid bgcolored${schoolEvent.status == 'CLICKED'?'':'Highlight'}" >
								<div class="row vertical-align">
									<div class="col-md-4 col-xs-4">
										<fmt:formatDate value="${schoolEvent.date}" pattern="yyyy-MM-dd HH:mm:ss" />
									</div>
									<div class="col-md-6 col-xs-6">
										${fn:escapeXml(schoolEvent.description)}
									</div>
									<div class="col-md-2 col-xs-2">
										<form id="form${fn:escapeXml(schoolEvent.id)}" method="POST" action="/admin/events" hidden=true>
											<input type="hidden" name="id" value="${fn:escapeXml(schoolEvent.id)}" />
										</form>
										<div class="input-group pull-right">
											<button type="submit" class="btn btn-success" form="form${fn:escapeXml(schoolEvent.id)}">
												<span class="glyphicon glyphicon-play" aria-hidden="true"></span>
											</button>
											<button type="submit" onclick="return confirm('Sure you want to delete this event?');" class="btn btn-danger" form="form${fn:escapeXml(schoolEvent.id)}"
												formaction="/admin/eventsDelete">
												<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
											</button>
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
