$(document).ready(function () {

});

function movePage(page) {
    location.href = encodeURI("/menu/bookmark?page=" + page + "&pageSize=15");
}

function moveBoard(boardNum, category) {
    location.href = encodeURI("/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0");
}

