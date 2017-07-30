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
    Academy details (${fn:escapeXml(schoolStatus)})
  </h3>
 

  Make sure you describe your academy in the best possible way and you enter a valid e-mail address for communication.
  Once this information provided we will validate the school existence and give you access to certification form.

  <form method="POST" action="${destination}">

    <div class="form-group">
      <label for="action">Name</label>
      <input type="text" name="name" id="name" value="${fn:escapeXml(school.name)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" name="description" id="description" value="${fn:escapeXml(school.description)}" class="form-control" />
    </div>


    <div class="form-group">
      <label for="contactMail">Contact Mail</label>
      <input type="text" name="contactMail" id="contactMail" value="${fn:escapeXml(school.contactMail)}" class="form-control" />
    </div>
    
    <div class="form-group hidden">
      <input type="hidden" name="id" value="${school.id}" />
    </div>

    <button type="submit" class="btn btn-success">${action}</button>
  </form>
</div>
<!-- [END form] -->
