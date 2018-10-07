<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<div class="modal fade" id='pictureModal' role="dialog" tabindex="-1"
		aria-labelledby="pictureModalLabel" style="display: none;">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<img id="modalPicture" alt="full size pic" class="media-object" style="width:100%" 
															src="/pics/gb-logo.png" onclick="$('#pictureModal').modal('toggle')" >
				</div>
			</div>
		</div>
	</div>
