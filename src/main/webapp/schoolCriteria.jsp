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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
  <h3>Criteria</h3>
  <c:choose>
  <c:when test="${empty certificationCriteria}">
  <p>No criteria found</p>
  </c:when>
  <c:otherwise>
  <c:forEach items="${certificationCriteria}" var="criterion">
  <div class="media">
      <div class="media-body">
        <h4>${fn:escapeXml(criterion.criterion.description)}</h4>
        <p>Pending : ${fn:escapeXml(criterion.pendings)}</p>
	      <form method="POST" action="/schoolCriteria" >
	      <input type="text" name="picture" id="picture" value="${fn:escapeXml(criterion.picture)}" class="form-control" />
	      <input type="hidden" name="id" value="${criterion.id}" />
	      <button type="submit" class="btn btn-danger" >Upload</button></form>
      </div>
  </div>
  </c:forEach>
  <c:if test="${not empty cursor}">
  <nav>
    <ul class="pager">
      <li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
    </ul>
  </nav>
  </c:if>
  </c:otherwise>
  </c:choose>
</div>
<!-- [END list] -->
