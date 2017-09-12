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
<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h3>
    <c:out value="${action}" /> PCE
  </h3>

  <form method="POST" action="${destination}">

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
	  <select class="form-control" name="rank" id="rank" value="${criterion.rank.id}">
	  	<c:forEach items="${rankList}" var="currentRank">
	    	<option value="${currentRank}" ${currentRank.id == criterion.rank.id? 'selected':''}>${currentRank.description}</option>
	    </c:forEach>
	  </select>
    </div>
 
    <div class="form-group">
      <label for="score">Score</label>
      <input type="text" name="score" id="score" value="${fn:escapeXml(criterion.score)}" class="form-control" />
    </div>


        
    <div class="form-group hidden">
      <input type="hidden" name="id" value="${fn:escapeXml(criterion.id)}" />
    </div>

    <button type="submit" class="btn btn-success">Save</button>
  </form>
</div>
<!-- [END form] -->
