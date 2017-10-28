<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">
			<strong>Available downloads :</strong>  
		</div>
		<div class="panel-body">
			<c:forEach items="${school.documents}" var="doc">
				<div class="container-fluid bgcolored">
					<div class="row vertical-align">
						<div class="col-md-11 col-xs-6">
							<h4>
								<strong>${fn:escapeXml(doc.key)}</strong>
							</h4>
						</div>
						<div class="col-md-1 col-xs-1">
							<a href="${fn:escapeXml(doc.value.URL)}">Download <span style="font-size:1.2em;" class="glyphicon glyphicon-download"></span>
							</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>