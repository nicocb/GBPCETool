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
    Academy details <label class="label label-${empty school.status?'Default':school.status.style}">${empty school.status?'Not provided':school.status.description}</label>
  </h3>
 
  <form method="POST" action="${destination}">
	<div class="panel panel-default">
		<div class="panel-heading">Contact :</div>
		<div class="panel-body">
		    <div class="form-group">
		      <label for="contactName">Your Name</label>
		      <input type="text" name="contactName" id="contactName" value="${fn:escapeXml(school.contactName)}" class="form-control" />
		    </div>
		    <div class="form-group">
		      <label for="contactMail">Your email</label>
		      <input type="text" name="contactMail" id="contactMail" value="${fn:escapeXml(school.contactMail)}" class="form-control" />
		    </div>
		    <div class="form-group">
		      <label for="contactPhone">Your phone number</label>
		      <input type="text" name="contactPhone" id="contactPhone" value="${fn:escapeXml(school.contactPhone)}" class="form-control" />
		    </div>
		 </div>
	</div>
 	<div class="panel panel-default">
		<div class="panel-heading">School :</div>
		<div class="panel-body">
		    <div class="form-group">
		      <label for="schoolName">School Name</label>
		      <input type="text" name="schoolName" id="schoolName" value="${fn:escapeXml(school.schoolName)}" class="form-control" />
		    </div>
 		    <div class="form-group">
		      <label for="schoolAddress">School Address</label>
		      <input type="text" name="schoolAddress" id="schoolAddress" value="${fn:escapeXml(school.schoolAddress)}" class="form-control" />
		    </div>
 		    <div class="form-group">
		      <label for="schoolMail">School Mail</label>
		      <input type="text" name="schoolMail" id="schoolMail" value="${fn:escapeXml(school.schoolMail)}" class="form-control" />
		    </div>
 		    <div class="form-group">
		      <label for="schoolPhone">School Phone Number</label>
		      <input type="text" name="schoolPhone" id="schoolPhone" value="${fn:escapeXml(school.schoolPhone)}" class="form-control" />
		    </div>
 		    <div class="form-group">
		      <label for="schoolWeb">School Website</label>
		      <input type="text" name="schoolWeb" id="schoolWeb" value="${fn:escapeXml(school.schoolWeb)}" class="form-control" />
		    </div>
 		 </div>
	</div>   
	<div class="panel panel-default">
		<div class="panel-heading">Instructor :</div>
		<div class="panel-body">
		    <div class="form-group">
		      <label for="instructorName">Instructor Name</label>
		      <input type="text" name="instructorName" id="instructorName" value="${fn:escapeXml(school.instructorName)}" class="form-control" />
		    </div>
		    <div class="form-group">
		      <label for="instructorBelt">Instructor Belt</label>
			  <select class="form-control" name="instructorBelt" id="instructorBelt">
			  	<c:forEach items="${beltList}" var="currentBelt">
			    	<option value="${currentBelt}" ${currentBelt == school.instructorBelt? 'selected':''}>${currentBelt.description}</option>
			    </c:forEach>
			  </select>
		    </div>
		    <div class="form-group">
		      <label for="instructorProfessor">Name of the person who promoted the instructor</label>
		      <input type="text" name="instructorProfessor" id="instructorProfessor" value="${fn:escapeXml(school.instructorProfessor)}" class="form-control" />
		    </div>
		 </div>
	</div>
    <div class="form-group hidden">
      <input type="hidden" name="id" value="${school.id}" />
    </div>

    <button type="submit" class="btn btn-success">${action}</button>
  </form>
</div>
<!-- [END form] -->
