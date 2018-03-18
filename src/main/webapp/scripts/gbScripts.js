function beforeCriteria(formData, $form, options) { 
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
	$('#uploadModalClose').addClass('btn-success');
	$('#uploadModalClose').removeClass('btn-danger');
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-danger');
	$('#ammoprogress').find('.progress-bar').addClass('progress-bar-success');

	$('#uploadModal').modal({backdrop: 'static', keyboard: 'false'});
} 
 
function checkEmpty(field, message) { 
	if($('#'+field).val()=='') {
		alert(message);
		return false;
	}
	return true;
} 
 
function uploadingCriteria(event, position, total, percentComplete)  { 
	$('#ammoprogress').find('.progress-bar').attr('style','min-width: 2em;width: '+percentComplete+'%');
	$('#ammoprogress').find('.progress-bar').attr('aria-valuenow',percentComplete);
	if(percentComplete < 100) {
		$('#ammoprogress').find('.progress-bar').text(percentComplete+'%');
	} else {
		$('#ammoprogress').find('.progress-bar').addClass('progress-bar-success');
		$('#ammoprogress').find('.progress-bar').addClass('progress-bar-striped');
		$('#ammoprogress').find('.progress-bar').text('Treating information');
	}
} 

function afterCriteria(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-striped');
	$('#ammoprogress').find('.progress-bar').text('Upload successful');
	$('#pic'+critId).attr('src',responseText.picture);
	$('#pic'+critId).attr('style','width: 60px;');
	if($form.find('textarea[name=comment]').val().length > 0) {
		$('#ammocomment'+critId).append('<div class="alert alert-info" role="alert">'+responseText.comment[responseText.comment.length-1].comment+'</div>');
		$form.find('textarea[name=comment]').val('');
	}
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
} 

function errorCriteria(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-success');
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-striped');
	$('#ammoprogress').find('.progress-bar').addClass('progress-bar-danger');
	$('#uploadModalClose').removeClass('btn-success');
	$('#uploadModalClose').addClass('btn-danger');
	$form.find('textarea[name=comment]').val('');
	$('#ammoprogress').find('.progress-bar').text(responseText.responseJSON.error);
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
} 

function switchForm(id)
{
	switchBlock(id);
    
    $('#'+id+'-btn').find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');

}

function switchEvents()
{
	var eye = $('#eyeOfTheTiger');
	if(eye.hasClass("glyphicon-eye-open")) {
		eye.addClass("glyphicon-eye-close");
		eye.removeClass("glyphicon-eye-open");
		$('.clicked').show();
	} else {
		eye.addClass("glyphicon-eye-open");
		eye.removeClass("glyphicon-eye-close");
		$('.clicked').hide();
	}
}

function switchBlock(id)
{
    if(document.getElementById(id).style.display == "none") {
    	document.getElementById(id).style.display="block";
    } else {
    	document.getElementById(id).style.display="none";
    }
}

function showBlock(id)
{
    if(document.getElementById(id).style.display != "block") {
    	document.getElementById(id).style.display="block";
    }
}

function validateEmail(id)
{
	  var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	  if(!email_regex.test($("#"+id).val())) {
		  alert('Wrong mail format');
		  return false;
	  }
}

function connectedToFB(response)
{
	alert(JSON.stringify(response))
}

function fbConnect()
{
	FB.login(function(response) {
		window.location.reload(false);
	}, {scope: 'public_profile,email'});

}

function resizeImg (img)
{
    var origH  = img.clientHeight;  // original image height
    var origW  = img.clientWidth; // original image width
    
    if(origW > 60) {
    	resize = 60 / origW;
    } else {
       	resize = img.parentElement.parentElement.clientWidth/120
    }

    var newH   = origH * resize;
    var newW   = origW * resize;

    // Set the new width and height
    img.style.height = newH;
    img.style.width  = newW;
}

$(function() {

	// We can attach the `fileselect` event to all file inputs on the page
	$(document).on('change', ':file', function() {
		var input = $(this),
		numFiles = input.get(0).files ? input.get(0).files.length : 1,
				label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
		input.trigger('fileselect', [numFiles, label]);
	});

	// We can watch for our custom `fileselect` event like this
	$(document).ready( function() {
		$(':file').on('fileselect', function(event, numFiles, label) {

			var input = $(this).parents('.input-group').find(':text'),
			log = numFiles > 1 ? numFiles + ' files selected' : label;

			if( input.length ) {
				input.val(log);
			} else {
				if( log ) alert(log);
			}

		});
	});

});
	  