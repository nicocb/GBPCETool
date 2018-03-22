<html lang="en">
<head>
<title>SCP - DASHBOARD</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon" href="/apple-touch-icon.png"/>
<link rel="icon"  href="/apple-touch-icon.png">
<script src="/scripts/gbScripts.js"></script>
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '123435078353209',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.10'
    });
      
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
	<div class="container">
		<h3 align="center">GB SCP Tool</h3>


		<a href='/school'><img alt="Gracie Barra Certification Program" src="/pics/scp.png" class="img-responsive center-block"/></a>

		<div class="text-center">
			<div class="btn-group" role="group" aria-label="..." align="center">
				<button type="button" class="btn btn-success"
					onclick="location.href='/school';">Go</button>
			</div>
		</div>
	</div>
</body>