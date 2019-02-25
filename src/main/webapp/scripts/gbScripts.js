function beforeCriteria(formData, $form, options) { 
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
	$('#uploadModalClose').addClass('btn-success');
	$('#uploadModalClose').removeClass('btn-danger');
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-danger');
	$('#ammoprogress').find('.progress-bar').addClass('progress-bar-success');

	$('#uploadModal').modal({backdrop: 'static', keyboard: 'false'});
	critId = $form.attr('id').substr(4);
} 
function beforeComm(formData, $form, options) { 
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
function uploadingComm(event, position, total, percentComplete)  {}

function afterCriteria(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-striped');
	$('#ammoprogress').find('.progress-bar').text('Upload successful');
	//$('#pic'+critId).attr('src',responseText.picture);
	//$('#pic'+critId).attr('style','width: 60px;');
	//id="pic${pic.id}"
	$('.example').hide();
	rebuildPics(critId, responseText.picture, responseText.schoolId);
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
	$form[0].reset();
} 

function afterComm(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	if($form.find('textarea[name=comment]').val().length > 0) {
		$('#ammocomment'+critId).append('<div class="alert alert-'+(responseText.comment[responseText.comment.length-1].author == 'GB'?'danger':'info')+'" role="alert">'+responseText.comment[responseText.comment.length-1].comment+'</div>');
		$form.find('textarea[name=comment]').val('');
	}
	//Update status 
	$('#status'+critId).removeClass();
	$('#status'+critId).addClass("label");
	$('#status'+critId).addClass("label-"+responseText.status.style);
	$('#status'+critId).text(responseText.status.description);
} 

function afterPicDelete(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	rebuildPics(critId, responseText.picture, responseText.schoolId);
} 

function rebuildPics(critId, picture, schoolId) {
	$('#media'+critId).empty();
	var component = '';
	for ( var pic in picture) {		
		component += '<img alt="Yep" class="media-object" style="width:60px;margin:3px"  onclick="resizeImg(this)"  src="'+picture[pic].url+'" >';
		component += '<form method="POST" id="picd'+critId+'" class="picForm"><input type="hidden" name="action" value="DELETE" />';
		component += '<input type="hidden" name="picId" value="'+picture[pic].id+'" />';
		component += '<input type="hidden" name="schoolId" value="'+schoolId+'"/>';
		component += '<input type="hidden" name="critId" value="'+critId+'" />';
		component += '<button type="submit" class="btn btn-danger" formAction="/api/schoolCriteria">Delete</button></form>';
	}
	$('#media'+critId).append(component);
	
    var picDeleteOptions = {
    		beforeSubmit:  beforeComm, 
 	        success:       afterPicDelete,
 	        error:       errorComm,
 	        uploadProgress: uploadingComm,
 	        url:'/api/schoolCriteria',         
 	        dataType:  'json'  
	};
    $('.picForm').ajaxForm(picDeleteOptions); 

}

function errorCriteria(responseText, statusText, xhr, $form)  { 
	critId = $form.attr('id').substr(4);
	$('#ammoprogress').find('.progress-bar').attr('style','min-width: 2em;width: 100%');
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-success');
	$('#ammoprogress').find('.progress-bar').removeClass('progress-bar-striped');
	$('#ammoprogress').find('.progress-bar').addClass('progress-bar-danger');
	$('#uploadModalClose').removeClass('btn-success');
	$('#uploadModalClose').addClass('btn-danger');
	$form.find('textarea[name=comment]').val('');
	$('#ammoprogress').find('.progress-bar').text(responseText.responseJSON.error);
	switchBlock('uploadModalClose');
	switchBlock('uploadModalX');
	$form[0].reset();
} 

function errorComm(responseText, statusText, xhr, $form)  { 
	$form.find('textarea[name=comment]').val('');
	alert(responseText.responseJSON.error);
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
	$('#pictureModal').modal();
	$('#modalPicture').attr('src',img.src);
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
	  