$(document).ready(function () {

});

function showDetail(id) {
    var body_id = "#body-" + id;
    var isDisplay = $(body_id).css("display");

    if (isDisplay == 'block') {
        $(body_id).css("display", "none");
    } else {
        $(".board-body").css("display", "none");
        $(body_id).css("display", "block");
    }
}

function movePage(page) {
    location.href="/menu/myList?page=" + page + "&pageSize=15";
}

function moveBoard(boardNum, category) {
    location.href="/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0";
}

