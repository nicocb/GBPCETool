<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Events :</h3>
	<c:choose>
		<c:when test="${empty schoolEvents}">
			<p>No events found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${schoolEvents}" var="schoolEvent">

				<form method="POST" action="/admin/events" class="form-inline">
					<c:choose>
						<c:when test="${schoolEvent.status == 'CLICKED'}">
							<label class="label label-default">${fn:escapeXml(schoolEvent.description)}</label>
						</c:when>
						<c:otherwise>
							<label class="label label-info">${fn:escapeXml(schoolEvent.description)}</label>
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="id"
						value="${fn:escapeXml(schoolEvent.id)}" />
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-play" aria-hidden="true"></span>
					</button>
					<button type="submit" class="btn btn-danger"
						formaction="/admin/eventsDelete">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</button>
				</form>

			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>
<!-- [END list] -->
