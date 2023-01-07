$(document).ready(function () {

});

function goBoard(boardNum, commenId, category) {
    location.href = encodeURI("/board/view?pageNum=0&type=all&category=" +category + "&boardNum=" + boardNum + "&commentId=" + commenId);
}

