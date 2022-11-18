$(document).ready(function () {

});

function showDetail(id) {
    var body_id = "#body-" + id;
    var isDisplay = $(body_id).css("display");

    if (isDisplay == 'block') {
        $(body_id).css("display", "none");
    } else {
        $(".notice-body").css("display", "none");
        $(body_id).css("display", "block");
    }
}