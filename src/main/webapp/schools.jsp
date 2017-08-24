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
  <h3>Academies</h3>
  <p>Highlight : ${highlighted}</p>
  <c:choose>
  <c:when test="${empty schools}">
  <p>No schools found</p>
  </c:when>
  <c:otherwise>
  <c:forEach items="${schools}" var="school">
  <div class="media">
      <div class="media-body">
        <h4><a href="/admin/schoolCriteriaAdmin/${fn:escapeXml(school.id)}">${fn:escapeXml(school.name)}</a><c:if test="${highlight == school.id}"><span class="badge">new</span></c:if>
        </h4>
        <p>${fn:escapeXml(school.description)}</p>
        <p>${fn:escapeXml(school.contactMail)}</p>
        <div class="btn-group" role="group" aria-label="..." align="center">
          <c:choose>
		  <c:when test="${school.pending}">
		  	<form  class="btn-group" method="POST" action="/admin/schools" ><input type="hidden" name="action" value="PUT" /><input type="hidden" name="id" value="${school.id}" /><input type="hidden" name="pending" value="false" /><button type="submit" class="btn btn-success" >Validate</button></form>
		  </c:when>
		  <c:otherwise>
		  	<form  class="btn-group" method="POST" action="/admin/schools" ><input type="hidden" name="action" value="PUT" /><input type="hidden" name="id" value="${school.id}" /><input type="hidden" name="pending" value="true" /><button type="submit" class="btn btn-warning" >Revoke</button></form>
		  </c:otherwise>
		  </c:choose>
		  	<form  class="btn-group" method="POST" action="/admin/schools" ><input type="hidden" name="action" value="DELETE" /><input type="hidden" name="id" value="${school.id}" /><button type="submit" class="btn btn-danger" >Delete</button></form>
      </div>
      </div>
  </div>
  </c:forEach>
  </c:otherwise>
  </c:choose>
</div>
<!-- [END list] -->
