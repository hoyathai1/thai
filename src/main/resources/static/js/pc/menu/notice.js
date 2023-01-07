const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

$(document).ready(function () {

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return encodeURI("type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content);
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
}

function goCategory(pCategory) {
    location.href = encodeURI("/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=");
}

function goSignUp() {
    location.href = "/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href = "/pc/login";
}

function goLogout() {
    location.href = "/logout";
}

function goMyAccount() {
    location.href = "/pc/menu/account";
}

function goMyList() {
    location.href = "/pc/menu/myList?pageNum=0";
}

function goBookmark() {
    location.href = "/pc/menu/myBookmark?pageNum=0";
}

function goMyComment() {
    location.href = "/pc/menu/myComment?pageNum=0";
}

function initSearch() {
    $(".likes").removeClass("on");
    $("input[name=likes]").val(0);

    $(".comment").removeClass("on");
    $("input[name=comment]").val(0);
}

function movePage(page) {
    location.href = encodeURI("/pc/menu/notice?pageNum=" + page);
}

function goView(boardId) {
    var pageNum = $("input[name=pageNum]").val();

    location.href = encodeURI("/pc/menu/noticeView?boardNotiId=" + boardId + "&pageNum=" + pageNum);
}