<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('#searchBox').on('input',function () {
            var inputText = $('#searchBox').val();
        	$("div.search").filter(function (){return $(this).text().toLowerCase().indexOf( inputText.toLowerCase() ) < 0;}).parent().parent().hide()
        	$("div.search").filter(function (){return $(this).text().toLowerCase().indexOf( inputText.toLowerCase() ) >= 0;}).parent().parent().show()
    	});
    });
</script>
<div class="container-fluid">
	<c:choose>
		<c:when test="${empty schoolEvents}">
			<p>No events found</p>
		</c:when>
		<c:otherwise>
				<div class="input-group input-group-sm">
					<span class="input-group-addon" id="sizing-addon">Search : </span>
					<input id="searchBox" type="text" class="form-control" placeholder="School/Date/Description" aria-describedby="sizing-addon">
					<span id="eyeOfTheTiger" class="input-group-addon glyphicon glyphicon-eye-open" onclick="switchEvents()"></span>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
					
						<c:forEach items="${schoolEvents.events}" var="schoolEvent">  
							<div class="container-fluid bgcolored${schoolEvent.status == 'CLICKED'?' clicked':'Highlight'}" ${schoolEvent.status == 'CLICKED'?'style="display: none;"':''}>
								<div class="row vertical-align">
									<div class="col-sm-2 hidden-xs search">
										<fmt:formatDate value="${schoolEvent.date}"  pattern="yyyy-MM-dd" />
									</div>
									<div class="col-sm-1 col-xs-3 search">
										${schoolEvent.readableDate}
									</div>
									<div class="col-sm-2 hidden-xs search">
										${fn:escapeXml(mode == 'admin'?schoolEvents.schools.get(schoolEvent.schoolId):schoolEvent.object.description)}
									</div>
									<div class="col-sm-6 col-xs-8 search">
										<a href="javascript:;" onclick="document.getElementById('form${fn:escapeXml(schoolEvent.id)}').submit();">${fn:escapeXml(schoolEvent.description)}</a>
									</div>
									<div class="col-sm-1 col-xs-1 search">
										<form id="form${fn:escapeXml(schoolEvent.id)}" method="POST" action="${mode == 'admin'?'/admin':''}/events" hidden=true>
											<input type="hidden" name="id" value="${fn:escapeXml(schoolEvent.id)}" />
										</form>
										<div class="input-group pull-right">
											<button type="submit" onclick="return confirm('Sure you want to delete this event?');" class="icon-button" form="form${fn:escapeXml(schoolEvent.id)}"
												formaction="${mode == 'admin'?'/admin':''}/eventsDelete">
												<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
											</button>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
</div>
<!-- [END list] -->
