
function saveForm(formId){
	$("#" + formId).submit();
}
function normalModal(modelId, contentId, url, title) {

    if (title) {
        $("#" + modelId + " .modal-title").html(title);
    }
    $("#" + modelId +" #"+contentId).load(url, function() {
    });

    $("#" + modelId).modal({
        backdrop : "static"
    });
    $("#" + modelId).on('hidden.bs.modal', function() {
        $("#" + modelId +" #"+contentId).empty();
    });
}