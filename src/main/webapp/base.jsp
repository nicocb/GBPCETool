<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
<title>SCP - DASHBOARD</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet"
		href="/css/gb.css">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon" href="/apple-touch-icon.png"/>
<link rel="icon"  href="/apple-touch-icon.png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="/scripts/gbScripts.js?version=4"></script>
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '123435078353209',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.10'
    });
      
//     FB.AppEvents.logPageView();   
//     FB.getLoginStatus(function(response) {
//         connectedToFB(response);
//     });
      
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#gb-navbar-collapse"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="pull-left">
					<a href="/"><img src="/pics/gb-logo.png" height="50" width="50" /></a>
				</div>
			</div>
			<div class="navbar-collapse collapse"
				id="gb-navbar-collapse" aria-expanded="false"
				style="height: 1px;">
				<h4 class="navbar-text">School certification program</h4>
				<ul class="nav navbar-nav">
					<li><a href="/school">School</a></li>
					<li ${schoolStatus == 'Validated'?'':'class="disabled"'}><a ${schoolStatus == 'Validated'?'href="/admission"':''}>Admission</a></li>
					<li ${agreementStatus == 'Validated'?'':'class="disabled"'}><a ${agreementStatus == 'Validated'?'href="/schoolCriteria"':''}>Certification</a></li>
					<li ${agreementStatus == 'Validated'?'':'class="disabled"'}><a ${agreementStatus == 'Validated'?'href="/documents"':''}>My downloads</a></li>
					<li><a href="/events">Events</a></li>

				</ul>
				<p class="navbar-text navbar-right">
					<c:choose>
						<c:when test="${not empty userEmail}">
							<a href="/logout" ${loginFrom=='Facebook'?'onclick="FB.logout();"':''} }> <c:if test="${not empty userImageUrl}">
									<img class="img-circle" src="${fn:escapeXml(userImageUrl)}"
										width="24">
								</c:if> ${fn:escapeXml(userEmail)} <span style="font-size:1.2em;" class="glyphicon glyphicon-log-out"></span>
							</a>
						</c:when>
						<c:otherwise>
							Login <a href="/login"><img alt="login with Google" width="24" src="pics/icons8-google-squared.png"></a><a href="#" onclick='fbConnect();'><img width="24" alt="login with FB" src="pics/icons8-facebook.png"></a>
						</c:otherwise>
					</c:choose>
				</div>
		</div>
	</nav>

	<c:import url="/${page}.jsp" />
</body>
</html>
<!-- [END base]-->
