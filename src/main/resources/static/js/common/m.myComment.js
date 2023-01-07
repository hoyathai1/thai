$(document).ready(function () {

});

function movePage(page) {
    location.href = encodeURI("/menu/myComment?page=" + page + "&pageSize=15");
}

function moveBoard(boardNum, category) {
    location.href = encodeURI("/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0");
}

function goComment(commentId, boardId, pCategory) {
    location.href = encodeURI("/board/view?pageNum=0&keyword=all&content=&type=all&category=" + pCategory + "&boardNum=" + boardId + "&commentId=" + commentId);
}