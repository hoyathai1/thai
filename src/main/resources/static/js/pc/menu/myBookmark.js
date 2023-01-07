const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

$(document).ready(function () {

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return encodeURI("type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content);
}

function goCategory(pCategory) {
    var pageSize = $("input[name=pageSize]").val();

    location.href = encodeURI("/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=");
}

function goSignUp() {
    location.href="/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href="/pc/login";
}

function goLogout() {
    location.href="/logout";
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

function onLikes(val) {

    if ($("input[name=likes]").val() == val) {
        $("input[name=likes]").val(0);
        $(".likes").removeClass("on");
    } else {
        $("input[name=likes]").val(val);
        $(".likes").removeClass("on");
        $("#likes" + val).addClass("on");
    }
}

function onComment(val) {
    if ($("input[name=comment]").val() == val) {
        $("input[name=comment]").val(0);
        $(".comment").removeClass("on");
    } else {
        $("input[name=comment]").val(val);
        $(".comment").removeClass("on");
        $("#comment" + val).addClass("on");
    }

}

function initSearch() {
    $(".likes").removeClass("on");
    $("input[name=likes]").val(0);

    $(".comment").removeClass("on");
    $("input[name=comment]").val(0);
}

function search() {
    var category = $("#category-filter-select option:selected").val();
    var keyword = $("#keyword option:selected").val();
    var content = $(".search-input").val();

    location.href = encodeURI("/pc/menu/myBookmark?category=" + category + "&keyword=" + keyword + "&content=" + content);
}

function movePage(page) {
    var category = $("#category-filter-select option:selected").val();
    var keyword = $("#keyword option:selected").val();
    var content = $(".search-input").val();

    location.href = encodeURI("/pc/menu/myBookmark?pageNum=" + page + "&category=" + category + "&keyword=" + keyword + "&content=" + content);
}

function goView(boardId, pCategory) {
    location.href = encodeURI("/pc/board/view?boardNum=" + boardId + "&pageNum=0&type=all&category=" + pCategory + "&keyword=all&content=");
}

function goDelete(boardNum) {

    openModal('삭제를 하시겠습니까?', 'type2', function () {
        var pageNum = $("input[name=pageNum]").val();
        var category = $("#category-filter-select option:selected").val();
        var keyword = $("#keyword option:selected").val();
        var content = $(".search-input").val();

        location.href = encodeURI("/pc/menu/deleteBookmark?boardNum=" + boardNum + "&pageNum=" + pageNum + "&category=" + category + "&keyword=" + keyword + "&content=" + content);
    });
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
}