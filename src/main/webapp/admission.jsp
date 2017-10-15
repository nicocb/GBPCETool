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
		Agreement form <label class="label label-${school.agreementStatus.style}">${school.agreementStatus.description}</label>
	</h3>

	<form method="POST" action="/admission">
		<div class="panel panel-default">
			<div class="panel-heading">Please read carefully the Gracie Barra Europe Agreement Form and check to agree</div>
			<div class="panel-body">
				<div class="form-group">
					<div class="well" style=" height:200;overflow-y:auto;">
					<section>
						<h3 id="title41">
							Gracie Barra Licensing Terms of Service 
						</h3>
						<div id="instruct41">This terms states the rights and obligations of all Gracie Barra schools in Europe therefore defining the relationship between the schools on the territory and Gracie Barra Europe.<br>
						<br>
						1. Gracie Barra Trademark Use:<br>
						<br>
						The license: Gracie Barra Association grants to Gracie Barra Schools a non-exclusive and non-transferable license to use the Gracie Barra trademark. The trademark shall only be used in connection with martial arts instruction and sales of tuition at Gracie Barra licensed schools. <br>
						<br>
						Area of exclusivity: Your school has the exclusive right to use the Gracie Barra trademark in the area where your service is offered. The exclusivity radius is typically 5 km but may be adjusted depending on the size of your town/city, population density and the level of jiu-jitsu development in the area.    <br>
						<br>
						Reserving an area: In order to determine the area reserved for a new GB school, a proper approval must be given by Gracie Barra Association and the hereby memorandum signed at the time of the proposal. The new school must be opened within 45 days.<br>
						<br>
						After 45 days have passed, the reservation expires and can be offered to another person interested in opening a school. <br>
						<br>
						The applicant whose area application has been approved by Gracie Barra Association shall pay a Joining Fee and the first Monthly Fee.<br>
						<br>
						Monthly Tuition in the same town/city: Gracie Barra Association monthly Tuition policy states that the price should be the same for all the schools in the same town/city.<br>
						<br>
						Exceptions may only apply to big cities with significant discrepancies in per capita income in the area. The population density shall always be taken into consideration.   <br>
						For such an exception to be considered the proposer shall contact Gracie Barra Europe and the decision shall be taken together with the regional leader. <br>
						<br>
						2. Gracie Barra System and Methods:<br>
						<br>
						Gracie Barra Library: All GB Schools shall be provided with access the Gracie Barra Policies as well as Teaching Systems and Methods. The Gracie Barra Library and Initial Training Program are the tools with such information. <br>
						<br>
						Diligence: Gracie Barra schools must implement the whole Gracie Barra Systems and Methods in accordance with the training programs provided by Gracie Barra Association.<br>
						<br>
						Ongoing Support: Gracie Barra Europe shall provide service supporting the implementation of the system via email, online training programs, and the GB Library.<br>
						<br>
						Certification: Gracie Barra School’s instructors must complete periodic instructors certification program coordinated by Gracie Barra Association.<br>
						   <br>
						Uniform (Kimono): Official Gracie Barra uniforms must be used by all students in all Gracie Barra Schools. The uniforms must be purchased from the Gracie Barra Wear.<br>
						<br>
						Seminar: It is highly recommended that all Gracie Barra Schools organize minimum one seminar per year with an instructor designated by Gracie Barra Association. This seminar ensures essential flow of information between instructors and students. It is prohibited to organize seminars with instructors from other Jiu-Jitsu organizations, unless previously approved by Gracie Barra Association.<br>
						<br>
						Referring to Master Carlos Gracie Jr and the Great Master Carlos Gracie: Gracie Barra Schools must clearly display large size photos of Master Carlos Gracie Jr and his father Grand Master Carlos Gracie. <br>
						<br>
						3. Gracie Barra Products:<br>
						<br>
						Gracie Barra Wear is the only official Gracie Barra supplier to all GB Schools. <br>
						<br>
						Manufacturing Exclusivity: Gracie Barra Wear has the exclusive right to manufacture products for Gracie Barra Schools. Gracie Barra Schools shall not manufacture any products with Gracie Barra name, trademark or logo. <br>
						<br>
						Commercial Exclusivity: Gracie Barra Wear has the exclusive right to supply products sold in Gracie Barra Schools. Gracie Barra Schools shall not sell products purchased from another supplier. <br>
						<br>
						4. The use of Gracie Barra Logo (Red Shield):<br>
						<br>
						Name of the School: The name used by a Gracie Barra School must be approved by Gracie Barra Association. The name shall define the area where the services of the school are offered. The following applies: Gracie Barra, followed by the name of the town/city, followed by the name of the area/county.<br>
						<br>
						• It is not permitted to use the name Gracie Barra together with any other word or expression that has a non-geographical meaning. <br>
						<br>
						• For an exception to be considered the proposer shall contact Gracie Barra Association and the decision shall be taken together with the area Leader. <br>
						<br>
						Logo: Gracie Barra Schools must not use the GB trademark in the format other than as permitted by Gracie Barra Association<br>
						<br>
						• The logo shall not be altered in any way and the color as well as the font shall remain exactly the same as in the original. <br>
						<br>
						• Gracie Barra Europe shall provide Gracie Barra schools with logos adapted for their use.  <br>
						<br>
						• For an exception to be considered the proposer shall contact Gracie Barra Europe and the decision shall be taken together with the area Leader. Annex A1 illustrates how to insert information in the GB logo. <br>
						<br>
						<br>
						5. GB Schools Hierarchy<br>
						<br>
						GB Schools classification is based on the three following levels:<br>
						<br>
						GB Training Centers: These centres are administered by Gracie Barra Blue or Purple Belts.  They are normally located in distant places with limited access to supervision, training and all the best practices determined by the Gracie Barra Systems and Methods. Persons devoted to learning and sharing the art of Jiu-Jitsu have full support of Gracie Barra Association.<br>
						<br>
						Gracie Barra is committed to developing these centers so that they can become Official and Premium Gracie Barra Schools. Training Centres’ instructors share the Gracie Barra philosophy but do not necessarily follow the latest discoveries in the area of tuition and management determined by the Barra Methodology. <br>
						<br>
						Official GB Schools: Administered by Gracie Barra Brown or Black Belts. The instructors responsible for the curriculum are directly or indirectly linked to Master Carlos Gracie Jr. The instructors at these schools share the philosophy as well as teaching methods of Gracie Barra but do not necessarily follow the best management practices determined by the Gracie Barra Systems and Methods. <br>
						<br>
						Premium GB Schools: These schools function entirely in accordance with the philosophy, teaching methods and management rules determined by the Gracie Barra Systems and Methods. <br>
						<br>
						All Premium Schools have a Gracie Barra Black Belt as their Head Instructor.  These schools possess all the tools and infrastructure necessary to function, develop and achieve the highest levels of quality in their service in order to be classified as GB Premium.<br>
						<br>
						<br>
						6. Regional Management:<br>
						<br>
						Supervision: Gracie Barra schools in Europe can count on the support of Gracie Barra Europe. The Gracie Barra Europe Staff role is to supervise the schools in his/her region ensuring they function in accordance with the hereby Memorandum of Understanding.  <br>
						<br>
						Competitions: Gracie Barra Europe is also responsible for communication with federations in regards to creating a national competition team. Therefore, regional leaders are to identify talented students in their area and forward their names to the GB Competition Department in order to build a team for national competitions.<br>
						<br>
						7. Marketing and Communication:<br>
						<br>
						Listing on the official websites: Gracie Barra Schools are listed on the GB official websites, i.e.  www.graciebarra.com in accordance with the three categories: training centre, official school, premium school. <br>
						<br>
						8. Payments:<br>
						<br>
						Joining fee: A new school shall pay Gracie Barra Association a joining fee upon opening. The current amount is One Thousand Euros (€ 1,000.00) and Gracie Barra Association reserves the right to change this amount at any time. If the Gracie Barra School had a prior agreement with Gracie Barra, the joining fee will be waived.<br>
						<br>
						• The joining fee may be paid in up to 4 installments without interest.<br>
						<br>
						Monthly fees: Gracie Barra Schools will be charged the amount corresponded of the tuition paid by 2 students at maximum tuition rate paid by the student at the specific location. Example, if the maximum tuition paid by a student at the specific Gracie Barra school is 100.00, the school must pay 2 x 100.00 as an affiliation fee to Gracie Barra Association. In Case your tuition rate is lower than 40 Euros, the minimum affiliation fee to be paid will be 80 Euros. The affiliation fee is due on the the 5th of every month.<br>
						• If the payment is declined and the school does not pay for affiliation fee or any other fee for over one month, all the benefits will be suspended and the school will no longer be listed as a GB School. <br>
						<br>
						Changes: Gracie Barra Association reserves the right to alter these amounts, however, any change requires a 30 days’ notice.  <br>
						<br>
						9. Cancellation:<br>
						The hereby Memorandum of Understanding is valid for 12 months and is automatically renewed at the end of this period. If any party wishes to terminate this agreement, a written 30 days’ notice must be given. </div>
						</section>
					
					</div>
					<label> <input type="checkbox" id="checkbox"
						id="agree" ${school.agreementStatus=='VALIDATED'?'checked':''} /> I have read and agree to the Terms and Conditions
						and Privacy Policy
					</label>
					<button type="submit" class="btn btn-success" onclick="if(!this.form.checkbox.checked){alert('You must agree to the terms first.');return false;}" >Validate</button>
				</div>
			</div>
		</div>
		<input type="hidden" name="id" value="${school.id}" />
	</form>
	<h3>
		Initial setup fees <label class="label label-${school.initialFeeStatus.style}">${school.initialFeeStatus.description}</label>
	</h3>
	<div class="panel panel-default">
		<div class="panel-heading">In order to finalize the registration of your school, you have to proceed with the payment of initial setup fees. (not necessary for existing schools)</div>
		<div class="panel-body">
			<div class="form-group">
				<img alt="PAyPal" src="/pics/paypal.png" /> <button type="submit" class="btn btn-success" onclick="location.href = 'https://www.paypal.com/fr/home';">Pay Now</button>
			</div>
		</div>
	</div>
	<h3>
		Monthly fees <label class="label label-${school.monthlyFeeStatus.style}">${school.monthlyFeeStatus.description}</label>
	</h3>
	<div class="panel panel-default">
		<div class="panel-heading">Please register and validate your Gracie Bqrra monthly fees automatic payment. (Not required for schools having an automatic payment already)</div>
		<div class="panel-body">
			<div class="form-group">
				<img alt="PAyPal" src="/pics/paypal.png" />  <button type="submit" class="btn btn-success" onclick="location.href = 'https://graciebarra.wufoo.com/forms/gb-europe-agreement-form/';">Pay Now</button>
			</div>
		</div>
	</div>


</div>
<!-- [END form] -->
