function switchForm(id)
{
    if(document.getElementById(id).style.display == "block") {
    	document.getElementById(id).style.display="none";
    } else {
    	document.getElementById(id).style.display="block";
    }
    
    $('#'+id+'-btn').find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');

}

function resizeImg (img)
{
    var resize = 1000; // resize amount in percentage
    var origH  = img.clientHeight;  // original image height
    var origW  = img.clientWidth; // original image width
    
    if(origW > 60) {
    	resize = 10;
    }

    var newH   = origH * (resize / 100);
    var newW   = origW * (resize / 100);

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
	  