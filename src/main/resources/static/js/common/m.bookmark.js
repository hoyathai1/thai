$(document).ready(function () {

});

function movePage(page) {
    location.href="/menu/bookmark?page=" + page + "&pageSize=15";
}

function moveBoard(boardNum, category) {
    location.href="/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0";
}

