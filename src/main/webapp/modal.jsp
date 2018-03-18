<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<div class="modal fade" id='uploadModal' role="dialog" tabindex="-1"
		aria-labelledby="uploadModalLabel" style="display: none;">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button id='uploadModalX' type="button" class="close" data-dismiss="modal"
						aria-label="Close" >
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="uploadModalLabel">Uploading...</h4>
				</div>
				<div class="modal-body">
					<div id ="ammoprogress" class="progress">
						<div class="progress-bar" role="progressbar"
							aria-valuenow="2" aria-valuemin="0"
							aria-valuemax="100" style="min-width: 2em;width: 2%">
						</div>
					</div>
					<div style="text-align: center;">
						<button  id='uploadModalClose' type="button" class="btn btn-success" data-dismiss="modal"
							aria-label="Close" >Close
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
