<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h3>
    <c:out value="${action}" /> SCP
  </h3>

  <form method="POST" action="${destination}" enctype="multipart/form-data">

    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" name="description" id="description" value="${fn:escapeXml(criterion.description)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="action">Action</label>
      <input type="text" name="action" id="action" value="${fn:escapeXml(criterion.action)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="comment">Comment</label>
      <input type="text" name="comment" id="comment" value="${fn:escapeXml(criterion.comment)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="rank">Rank</label>
	  <select class="form-control" name="rank" id="rank">
	  	<c:forEach items="${rankList}" var="currentRank">
	    	<option value="${currentRank}" ${currentRank.id == criterion.rank.id? 'selected':''}>${currentRank.description}</option>
	    </c:forEach>
	  </select>
    </div>
 
    <div class="form-group">
      <label for="score">Score</label>
      <input type="text" name="score" id="score" value="${fn:escapeXml(criterion.score)}" class="form-control" />
    </div>
	<div class="form-group">
		<label for="image">Example picture : </label>
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
		<c:if test="${not empty criterion.picture}">
			<div class="panel panel-default">
			  <div class="panel-body">
			   <img alt="Picture" width="100%" class="media-object" 
					src="${fn:escapeXml(criterion.picture)}" >
			  </div>
			</div>
		</c:if>
	</div>

        
    <div class="form-group hidden">
      <input type="hidden" name="id" value="${fn:escapeXml(criterion.id)}" />
    </div>

  </form>
</div>
